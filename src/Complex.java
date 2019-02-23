import java.awt.event.ComponentListener;

/**
 * @author mali
 *
 * Because the DFT requires imaginary numbers. So, this class is build for storing complex numbers
 * */


public class Complex {

    protected float real, img;

    public Complex() {
        real = 0f;
        img = 0f;
    }

    public Complex(float real, float img) {
        this.real = real;
        this.img = img;
    }

    /**
     * This function returns a new Complex object which contains new values being obtained by doing addition
     * @param complex is an instanced Complex which needs being updated
     */

    public void plus(Complex complex) {

        this.real += complex.real;
        this.img += complex.img;

    }

    public float getMagnitude() {

        return (float)Math.sqrt(real * real + img * img);
    }

    public float getPhase() {
        return (float)Math.atan2(img, real);
    }

    public Complex times(Complex complex) {
       float newReal = complex.real * this.real;
       float newImg = complex.img + this.img;
       return new Complex(newReal, newImg);
    }

    public static Complex euler(float factor, float input) {
        float inputReal = (float) Math.cos(factor * input);
        float inputImg = (float)(factor * Math.sin(factor * input));
        //System.out.println("input real is " + inputReal);
        //System.out.println("input imaginary is " + inputImg);
        return new Complex(inputReal, inputImg);
    }
}
