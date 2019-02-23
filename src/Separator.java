import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.*;

/*
 * All these works have been done based on the assumption that all images being loaded
 * into the program are in RGB model
 *
 * */

public class Separator {

    /*
     * The index of the color matrices in the list follows the natrue order of RGB
     * color[0] = red
     * color[1] = green
     * color[2] = blue
     *
     * */
    private ArrayList<int[][]> colors;
    private int[][] red;
    private int[][] green;
    private int[][] blue;

    // width and height store the sizes from the input image file
    private int width;
    private int height;

    private void prepareColors(BufferedImage image) {
        if (image == null) {
            // In fact an exception should be thrown out
            return;
        }
        colors = new ArrayList();
        width = image.getWidth();
        height = image.getHeight();

        // Initiating all color matrices
        red = new int[this.width][this.height];
        green = new int[this.width][this.height];
        blue = new int[this.width][this.height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Initiating a new color based on the image RGB information
                Color c = new Color(image.getRGB(i, j));

                // Separating red, blue, green from the new color c
                // and putting them into corresponding matrices
                red[i][j] = (int)c.getRed();
                green[i][j] = (int)c.getGreen();
                blue[i][j] = (int)c.getBlue();
            }
        }
        colors.add(0, red);
        colors.add(1, green);
        colors.add(2, blue);
    }

    public List<int[][]> getColorMatrices(BufferedImage image) {
        prepareColors(image);
        return colors;
    }
}
