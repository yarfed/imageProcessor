package cortica;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) {

        UrlSource urls = new FileUrlSource("input.images.txt");

        ExecutorService service = Executors.newCachedThreadPool();

        while (true) {
           final String url = urls.nextUrl();
            if (url == null) {
                break;
            }
            service.submit(() -> {
                try {
                    new ImageProcess(url)
                            .download()
                            .resize(200, 200)
                            .grayScale()
                            .save()
                            .register();
                    System.out.println(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
