import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GaussFilterOp {

    // originalColors stores the color information from the original pic
    // processedColors stores the image being processed by filters
    private ArrayList<int[][]> originalColors;
    private ArrayList<int[][]> processedColors;
    private GaussKernel kernel;
    private Separator separator;

    private JFrame inputFrame;

    private int radius;
    private int sigma;
    private boolean flag;
    private String rVal;
    private String sigVal;


    public GaussFilterOp() {
        this.kernel = new GaussKernel();
        this.separator = new Separator();
        inputFrame = new JFrame("Parameters");
        rVal = JOptionPane.showInputDialog(inputFrame, "Enter integers greater than 0 here",
                "Setting radius", JOptionPane.WARNING_MESSAGE);
        if (rVal == null || hasLetter(rVal)) {
            System.out.println("Illegal input");
            new GaussFilterOp();
        } else {
            radius = Integer.parseInt(rVal);
            System.out.println("Radius is set as " + radius);
        }
        sigVal = JOptionPane.showInputDialog(inputFrame, "Enter integers greater than 0 here",
                "Setting sigma", JOptionPane.WARNING_MESSAGE);
        if (sigVal.equals(null) || hasLetter(sigVal)) {
            System.out.println("Illegal input");
            new GaussFilterOp();
        } else {
            sigma = Integer.parseInt(sigVal);
            System.out.println("Sigma is set as " + sigma);
        }

    }

    public BufferedImage filter(BufferedImage src) {
        Thread.currentThread().setName("Main");
        System.out.println("Any one called this filter");
        if (src == null) {
            System.out.println("Invalid source picture");
            System.exit(-1);
        }

        originalColors = (ArrayList<int[][]>)separator.getColorMatrices(src);
        processedColors = new ArrayList<>();
        for (int i = 0; i < originalColors.size(); i++) {
            processedColors.add(kernel.processPic(originalColors.get(i), radius, sigma));
        }

       return reformPic(processedColors);

    }


    private boolean hasLetter(String s) {
        char[] target = s.toCharArray();

        for (char i : target) {
            if (i < '0' || i > '9') {
                return true;
            }
        }
        return false;
    }



    private BufferedImage reformPic(List<int[][]> finalColor) {
        int height = finalColor.get(0).length;
        int width = finalColor.get(0)[0].length;
        // This loop takes all gauss transferred color information
        // out to form a new RGB color and update the corresponding pixel
        // information of the original image


        BufferedImage img = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int red = finalColor.get(0)[i][j];
                int green = finalColor.get(1)[i][j];
                int blue = finalColor.get(2)[i][j];


                Color newColor = new Color(red, green, blue);
                img.setRGB(i, j, newColor.getRGB());
            }
        }
        return img;
    }

}
