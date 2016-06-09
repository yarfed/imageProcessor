package cortica;

import db.ImagesDB;
import org.apache.commons.io.FilenameUtils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * Created by User on 08.06.2016.
 */
public class ImageProcess {

    private long ID;
    private Timestamp date;
    private String fileName;
    private String ext;
    private ImageProcess self;
    private String url;
    private BufferedImage image;
    private String urlMD5;

    public ImageProcess(String url) {
        this.url = url;
        this.urlMD5 = MD5.getMD5(url);
        this.fileName = FilenameUtils.getName(url);
        this.ext = FilenameUtils.getExtension(url);
        self = this;
    }

    public ImageProcess download() throws IOException {
        image = ImageIO.read(new URL(url));
        date = new Timestamp(System.currentTimeMillis());
        return self;
    }

    public ImageProcess save() throws IOException {
        ImageIO.write(image, ext, new File("images/" + urlMD5 + "." + ext));
        return self;
    }

    public ImageProcess resize(int width,int height) throws IOException {
        Image img = image.getScaledInstance(width,
                height, Image.SCALE_SMOOTH);
        image = new BufferedImage(width, height,image.getType());
        image.getGraphics().drawImage(img, 0, 0 , null);
        return self;
    }

    public ImageProcess grayScale() throws IOException {
        int width=image.getWidth();
        int height=image.getHeight();
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_GRAY);
        img.getGraphics().drawImage(image, 0, 0, null);
        image=img;
        return self;
    }

    public ImageProcess register() throws SQLException {
        ImagesDB db = new ImagesDB(
                "jdbc:hsqldb:file:database/database", "test", "test",
                "org.hsqldb.jdbcDriver");
        db.createTable();
        db.insertIntoTable( date,url,"images/"+fileName,urlMD5);
        return self;
    }

}