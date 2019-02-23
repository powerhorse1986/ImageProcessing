/* Digital image is a concept, which defines a digital image as a
 * grid of pixels.
 * Each pixel contains spatitial information, band information as well
 * as sample information.
 * Each pixel may contain one or more samples. For example, a RGB piexl
 * should contain one red sample, one green sample as well as a blut sample
 *
 * Since different color models define different digital images
 * So, here we only create an interface.
 *
 *
 * */

public interface DigitalImage {
    public int getWidth();

    public int getHeight();

    public int getNumberOfBand();

    // According to description above, pixels should be stroed in an array
    public int[] getPixel();
}
