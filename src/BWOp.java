import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class BWOp {
    private BufferedImage dest;

    public BufferedImage bwOp(BufferedImage src) {
        if (dest == null) {
            dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        }
        WritableRaster srcRaster = src.getRaster();
        for (int i = 0; i < srcRaster.getHeight(); i++) {
            for (int j = 0; j < srcRaster.getWidth(); j++) {
                for (int b = 0; b < srcRaster.getNumBands(); b++) {
                    if (b == 0) {
                        dest.getRaster().setSample(j, i, 0, src.getRaster().getSample(j, i, 0) * 0.299);
                    }
                    if (b == 1) {
                        dest.getRaster().setSample(j, i, 1, src.getRaster().getSample(j, i, 1) * 0.587);
                    }
                    if (b == 2) {
                        dest.getRaster().setSample(j, i, 2, src.getRaster().getSample(j, i, 2) * 0.114);
                    }
                }
            }
        }
        return dest;
    }
}
