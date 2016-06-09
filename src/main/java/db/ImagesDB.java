package db;

/**
 * Created by User on 09.06.2016.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class ImagesDB extends ConfigDB {

    public ImagesDB(String DBAddress, String userName, String password, String driver) {
        super(DBAddress, userName, password, driver);

    }

    public void insertIntoTable(Date date, String url, String path, String md5)
            throws SQLException {
      // using date from sql.Date we loose time! TIMESTAMP better
        String query = "INSERT INTO images (downloadDate, filepath, url, md5) "
                + " VALUES(now(), '" + path + "', '" + url + "', '" + md5 + "')";
        Statement s = createStatement();
        s.executeUpdate(query);
       closeStatement(s,null);
    }

    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS images "
                + "(downloadDate DATE, filepath VARCHAR(2000),"
                + "url VARCHAR(2000), md5 VARCHAR(32))";
        try (Statement s = createStatement()) {
            s.executeUpdate(query);
        }
    }
}