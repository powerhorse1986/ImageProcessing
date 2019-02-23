
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * @author mali
 *
 */
public class InvertOp {
    private BufferedImage dest;

    public BufferedImage filter(BufferedImage src) {
        if (dest == null) {
            dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        }
        WritableRaster srcRaster = src.getRaster();
        for (int i = 0; i < srcRaster.getHeight(); i++) {
            for (int j = 0; j < srcRaster.getWidth(); j++) {
                for (int b = 0; b < srcRaster.getNumBands(); b++) {
                    int sample = srcRaster.getSample(j, i, b);
                    dest.getRaster().setSample(j, i, b, sample - 255);
                }
            }
        }
        return dest;
    }

}
