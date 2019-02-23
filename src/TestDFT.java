import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestDFT {



    public static void main(String[] args) {
        TestDFT test = new TestDFT();
        DTF dtf = new DTF();
        BufferedImage img = null;
        File file = new File("/Users/mali/Desktop/Image_Processing/Project_2/img/hat.jpg");
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dtf.imgDFT(img, true);

    }
}
