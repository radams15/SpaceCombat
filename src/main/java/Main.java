import java.io.File;

public class Main {
    public static void main(String[] args){
        System.setProperty("org.lwjgl.librarypath", new File("target/natives").getAbsolutePath());
        new Main().init(args);
    }

    public void init(String[] args){
        MainWindow window = new MainWindow();
        window.run();
    }
}
