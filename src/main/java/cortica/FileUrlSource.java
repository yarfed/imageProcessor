package cortica;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by User on 09.06.2016.
 */
public class FileUrlSource implements UrlSource{

    private Scanner scanner;

    public FileUrlSource(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try  {
            scanner = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String nextUrl() {
        if (scanner.hasNext()) {
            return scanner.nextLine();
        }
        else {
            scanner.close();
            return null;
        }
    }
}
