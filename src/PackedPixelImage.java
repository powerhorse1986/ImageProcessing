import java.awt.image.BufferedImage;

/**
 * @author mali As we discussed in the DigitalImage class, computers store image
 *         as a grid of piexls, which contains spatitial information as well as
 *         the color information. In java, the concept Raster refers to an pixel
 *         and the color information stored in that pixel. Thus, the navie way
 *         for storing a image information is creating a three dimension array
 *         as the following: int[][][] raster = new int[number of column][number
 *         of row][number of bands]; If we want to store a color information, we
 *         have to assign the value of the color to the target raster like this:
 *         raster[col][number][band] = color; This is straight forward, but,
 *         it's a waste of space. A three dimensional array in fact is an
 *         one-dimensioal array which contains references to a two dimensional
 *         array. So, the raster can be treated as an one dimensional array with
 *         length of numberOfHeight, references to a two dimensional array which
 *         has a size of numberOfHeight * numberOfRow. And each element in the
 *         2D array refers to a color value And, every java array contains an
 *         integer which refers to the length of the array. So, there are 1 +
 *         numberOfHeight + numberOfwidth * numberOfHeigth integers being used
 *         to indicate the lengths. Thus, to store the references to colors, 4 *
 *         (numberOfHeight + numberOfHeight * numberOfWidth) + 4 (1 +
 *         numberOfHeight + numberOfwidth * numberOfHeigth) bytes of space is
 *         used.
 *
 *         If we take a look at the TCP/IP, we can notice that the IP addresses
 *         are represented using 256-digital numbers. In an IP address, every 8
 *         bits of binary numbers contains a specific information
 *
 *         Since one integer has a length of 32 bits, we can combine the piexl
 *         information into one ingeter number. And we can get information we
 *         need by using binary operations like >>, << and logic operation &, |
 *
 *         E.g.:
 *
 *         Byte3 Byte2 Byte1 Byte0 (band) (red) (green) (blue) 11111111 01101111
 *         10101000 11001001
 *
 *         Now, we can install a pixel information into an array like this:
 *         int[] raster = new int[numberOfHeight * numberOfWidth];
 *
 *         If we want to get the sample located in col x and row y, and it has z
 *         bands, operation is return (raster[col + row * col] >> 8 * z) & 0xFF;
 *         The 0xFF is the mask which returns the last 8 digital 0xFF:
 *         00000000000000000000000011111111
 *
 *         If we want to reset the sample in col x, row y with z bands int
 *         pixelIndex = row ∗ width + col; int pixel = raster pixelIndex ]; int
 *         mask = 0xFF << ( band ∗ 8 ); s = s << ( band ∗ 8 ); int
 *         raster[pixelIndex] = (pixel ˆ ((pixel ˆ s) & mask)); Here, band means
 *         the index of each color sample
 */
public class PackedPixelImage implements DigitalImage {
    private int[] raster;
    private int col;
    private int row;
    private int bands;

    public PackedPixelImage(int height, int width, int bands) {
        this.col = width;
        this.row = height;
        this.bands = bands;
        raster = new int[col * row];
    }

    public PackedPixelImage(BufferedImage srcImg) {
        if (srcImg == null) {
            System.exit(-1);
        }
        this.col = srcImg.getWidth();
        this.row = srcImg.getHeight();
        this.bands = srcImg.getRaster().getNumBands();
        raster = new int[col * row];
        for (int i = 0; i < srcImg.getHeight(); i ++) {
            for (int j = 0; j < srcImg.getWidth(); j++) {
                for (int b = 0; b < srcImg.getRaster().getNumBands(); b++) {
                    int sample = srcImg.getRaster().getSample(i, j, b);
                    this.setSample(i, j, b, sample);
                }
            }
        }
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return col;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return row;
    }

    @Override
    public int getNumberOfBand() {
        // TODO Auto-generated method stub
        return bands;
    }

    @Override
    public int[] getPixel() {
        // TODO Auto-generated method stub
        return raster;
    }

    public int getSample(int row, int col, int band) {

        return (raster[col + col * row] >> band * 8) & 0xFF;

    }

    public static int RANDOM_COLOR(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | (blue);
    }

    public void setSample(int row, int col, int band, int s) {
        int pixelIndex = col + col * row;
        int pixel = raster[pixelIndex];
        int mask = (8 * band) << 0xFF;
        s = s << (8 * band);

        raster[pixelIndex] = pixel ^ ((pixel ^ s) & mask);
    }

    // We have to pay attention to that pixel != sample
    // Again, one pixel may compose several samples
    public int[] getPixel(int row, int col) {
        int[] temp = new int[bands];
        for (int i = 0; i < bands; i++) {
            temp[i] = getSample(row, col, i);
        }
        return temp;
    }

    public void setPixel(int row, int col, int[] pixel) {
        for (int i = 0; i < bands; i++) {
            setSample(row, col, i, pixel[i]);
        }
    }

}
