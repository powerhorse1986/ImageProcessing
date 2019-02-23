import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

/**
 * @author mali This interface describes single-input/single-output operations
 *         performed on BufferedImage objects. In other words, this is an
 *         abstract filter
 */
public abstract class FilterOp implements BufferedImageOp {

    /**
     * Since we just want to see the output of a processed picture, and we do not
     * want to modify the picture itself, so, we have to generate another image
     * instead of processing the original file.
     *
     * This createCompatibleSampleModel() method is created for this purpose The
     * return value of this method is a BufferedImage
     *
     * Input parameters:
     * @param srcImg: We are going to create an image has
     * the same size as the original file. So, the best approach is passing the
     * source image into this method and using the sizes of the original file to
     * create a new image
     *
     * @param outCm(@cite java.awt.image.ColorModel): The ColorModel abstract
     * class encapsulates the methods for translating a pixel value to color
     * components (for example, red, green, and blue) and an alpha component. In
     * order to render an image to the screen, a printer, or another image, pixel
     * values must be converted to color and alpha components. As arguments to or
     * return values from methods of this class, pixels are represented as 32-bit
     * ints or as arrays of primitive types. The number, order, and interpretation
     * of color components for a ColorModel is specified by its ColorSpace. A
     * ColorModel used with pixel data that does not include alpha information
     * treats all pixels as opaque, which is an alpha value of 1.0.
     */

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCm) {
        /**
         * Since the return value of this method is an instanced BufferedImage, So, we
         * just have to generate a new BufferedImage by calling its one of its
         * Constructor:
         * BufferedImage(ColorModel destCm, WritableRaster raster,
         * boolean isRasterPremultiplied, HashTable properties)
         * Paramters:
         * @param raster(@cite: java.awt.image.WritableRaster) This class
         * extends Raster to provide pixel writing capabilities. Refer to the class
         * comment for Raster for descriptions of how a Raster stores pixels. Raster:
         * java use the instanced Raster for storing pixel information. Briefly
         * speaking, raster is a conceptual layer, which is an one dimensional array
         * stores references to color table. It implements 3D array using an 1D array
         *
         */
        return new BufferedImage(destCm, destCm.createCompatibleWritableRaster(src.getWidth(), src.getHeight()),
                destCm.isAlphaPremultiplied(), null);
    }

    /**
     * (non-Javadoc)
     *
     * @see java.awt.image.BufferedImageOp#filter(java.awt.image.BufferedImage,
     * java.awt.image.BufferedImage) This is the core method of this class. Any
     * filter inherits this class, must override this method.
     */
    @Override
    public abstract BufferedImage filter(BufferedImage src, BufferedImage dest);

    /**
     * (non-Javadoc)
     *
     * @see java.awt.image.BufferedImageOp#getBounds2D(java.awt.image.BufferedImage)
     * Parameters:
     * @param src - The BufferedImage to be filtered Returns: The Rectangle2D
     * representing the destination image's bounding box. An
     * IllegalArgumentException may be thrown if the source image is incompatible
     * with the types of images allowed by the class implementing this filter.
     */
    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        // TODO Auto-generated method stub
        // Check if the src is null
        if (src == null) {
            System.out.println("Please input an image");
            return null;
        } else {
            return src.getRaster().getBounds();
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see java.awt.image.BufferedImageOp#getPoint2D(java.awt.geom.Point2D,
     * java.awt.geom.Point2D) Returns the location of the corresponding destination
     * point given a point in the source image. If dstPt is specified, it is used to
     * hold the return value. Parameters: srcPt - the Point2D that represents the
     * point in the source image dstPt - The Point2D in which to store the result
     */
    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        // TODO Auto-generated method stub
        // First we have to check if any one of these points is null
        if (srcPt == null) {
            System.out.println("Please make sure inputting an valid source file");
            return null;
        } else if (dstPt == null) {
            // The constructor of Point2D is protected, so it cannot be
            // instanced directly. clone() is a method can copy the original
            // information and return an Object
            dstPt = (Point2D) srcPt.clone();
        } else {
            dstPt.setLocation(srcPt);
        }
        return dstPt;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.awt.image.BufferedImageOp#getRenderingHints()
     */
    @Override
    public RenderingHints getRenderingHints() {
        // TODO Auto-generated method stub
        return null;
    }

}
