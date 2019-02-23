import java.awt.image.BufferedImage;
import java.util.*;

public class SobelOp {
    private final int[][] xKernel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private final int[][] yKernel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    private int[][] processedColor;
    private Separator separator;

    private int[][] convolvPic(int[][] color, int[][] selectedKernel) {
        processedColor = new int[color.length][color[0].length];
        int[][] kernel = selectedKernel;

        // window_***_index setting the boarder for the window
        // this nested loop is sliding the window
        // the window starts moving from (0, 0) of the color matrix
        // but for the window itself, it starts from (r, r), the center of
        // this matrix. with the window sliding, the cover area of the windwo increases
        for (int i = 0; i < color.length; i++) {
            //int window_upper_index = Math.min(0, Math.max(0, i - r));
            //int window_lower_index = Math.min(color.length - 1, i + r);
            for (int j = 0; j < color[0].length; j++) {
                //int window_left_index = Math.min(0, Math.max(0,  j - r)) ;
                //int window_right_index = Math.min(color[0].length - 1, j + r);
                //int x_pointer = window_left_index;
                //int y_pointer = window_upper_index;
                int left_board, upper_board = Integer.MIN_VALUE;
                int rignt_board, lower_board = Integer.MAX_VALUE;
                if (j <= 3) {
                    left_board = Math.abs(j - 3);
                    rignt_board = 2 * 3;
                } else if (color[0].length - 1 - j <= 3) {
                    left_board = 0;
                    rignt_board = 3 + (color[0].length - 1 - j);
                } else {
                    left_board = 0;
                    rignt_board = 2 * 3;
                }
                if (i <= 3) {
                    upper_board = Math.abs(i - 3);
                    lower_board = 2 * 3;
                } else if (color.length - 1 - i <= 3) {
                    upper_board = 0;
                    lower_board = 3 + (color.length - 1 - i);
                } else {
                    upper_board = 0;
                    lower_board = 2 * 3;
                }
                int y_pointer = upper_board;
                int x_pointer = left_board;
                while (y_pointer <= lower_board && y_pointer < kernel[0].length) {

                    while (x_pointer <= rignt_board && x_pointer < kernel.length) {
                        //System.out.println("x is " + x_pointer);
                        //System.out.println("y is " + y_pointer);
                        double kernel_cell = kernel[y_pointer][x_pointer];
                        int color_x_pointer = -1;
                        int color_y_pointer = -1;
                        if (left_board == 0) {
                            color_x_pointer = Math.min(x_pointer + j, color[0].length - 1);
                        } else {
                            color_x_pointer = x_pointer;
                        }
                        if (upper_board == 0) {
                            color_y_pointer = Math.min(y_pointer + i, color.length - 1);
                        } else {
                            color_y_pointer = y_pointer;
                        }
                        //System.out.println("color x is " + color_x_pointer);
                        //System.out.println("color y is " + color_y_pointer);
                        int original_color = color[color_y_pointer][color_x_pointer];
                        //System.out.println("kernel cell = " + kernel_cell);
                        //System.out.println("original color " + original_color);
                        double transfered = kernel_cell * original_color;
                        //System.out.println("transfered value is " + transfered);
                        processedColor[i][j] += transfered;
                        //System.out.println("stored value is " + processedColor[i][j]);
                        x_pointer++;

                    }
                    x_pointer = left_board;
                    y_pointer++;
                }


            }
        }
        return processedColor;
    }

    public BufferedImage sobelFilter(BufferedImage img) {
        if (img == null) {
            return img;
        }
        System.out.println("Sobel is calling");
        separator = new Separator();
        List<int[][]> processedImg = separator.getColorMatrices(img);
        BufferedImage distImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int b = 0; b < img.getRaster().getNumBands(); b++) {
            int[][] bandSample = convolvPic(processedImg.get(b), xKernel);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    distImg.getRaster().setSample(i, j, b, bandSample[i][j]);
                }
            }
        }
        return distImg;
    }
}
