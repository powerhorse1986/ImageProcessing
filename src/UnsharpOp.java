import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UnsharpOp {
    private int[][] original;
    private int[][] processed;
    private int[][] unsharped;
    private int[][] removeFilter;

    // this list will be returned to user
    private List<int[][]> unsharpedColor;
    private List<int[][]> substractFilter;

    // these two integers define the sizes of the three matrices
    private int width;
    private int height;

    private Random random;
    private Separator separator;

    // coefficient is for the unsharp filter
    private double coefficient;

    private void process(List<int[][]> originalPic, List<int[][]> processedPic) {
        width = originalPic.get(0)[0].length;
        height = originalPic.get(0).length;
        unsharpedColor = new ArrayList();
        substractFilter = new ArrayList();
        while (this.coefficient < 0.2 || this.coefficient > 0.7) {
            // coefficient will be generated randomly
            // 0.5 <= coefficient <= 1.5
            random = new Random();

            // set seed avoiding duplication
            random.setSeed(width * height);
            coefficient = random.nextDouble();
        }

        // taking out the matrices for each colors
        for (int index = 0; index < originalPic.size(); index++) {
            // initiating the three matrices
            original = originalPic.get(index);
            processed = processedPic.get(index);
            unsharped = new int[height][width];
            removeFilter = new int[height][width];
            // unsharping
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    removeFilter[i][j] = (original[i][j] - processed[i][j]) <= 0 ? 0 : (original[i][j] - processed[i][j]);
                    unsharped[i][j] = original[i][j] +
                            (int)coefficient * (original[i][j] - processed[i][j]);
                    unsharped[i][j] = unsharped[i][j] > 255 ? 255 : unsharped[i][j];
                }
            }
            this.substractFilter.add(this.removeFilter);
            this.unsharpedColor.add(this.unsharped);

        }
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

    public BufferedImage unsharp(BufferedImage original, BufferedImage processed) {
        separator = new Separator();
        System.out.println("Unsharper is working");
        process(separator.getColorMatrices(original), separator.getColorMatrices(processed));
        return this.reformPic(this.unsharpedColor);
    }

}
