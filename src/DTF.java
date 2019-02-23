import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class DTF {
    private List<Complex[][]> data;
    private WritableRaster input;
    private Complex[][] bandComplex;
    private Complex[][] processedBand;
    private List<Complex[][]> complexImg;
    private int imageHeight;
    private int imageWidth;


    private List<Complex[][]> imageToComplex(BufferedImage img) {
        if (img == null) {
            return null;
        }
        imageHeight = img.getHeight();
        imageWidth = img.getWidth();
        data = new ArrayList<Complex[][]>();
        input = img.getRaster();
        System.out.println("img has bands " + input.getNumBands());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int b = 0; b < input.getNumBands(); b++) {
            bandComplex = new Complex[input.getHeight()][input.getWidth()];
            for (int i = 0; i < input.getHeight(); i++) {
                for (int j = 0; j < input.getWidth(); j++) {
                    //try {
                        int sample = input.getSample(j, i, b);
                        bandComplex[i][j] = new Complex(sample,0);
                    //} catch (ArrayIndexOutOfBoundsException e) {

                    //}
                    //System.out.println("input[" + i + "][" + j + "] at band " + b + " has value " + sample);
                    bandComplex[i][j] = new Complex(sample,0);
                }
            }
            data.add(bandComplex);
            //bandComplex = null;
        }
        System.out.println("Image to complex done");
        return data;
    }

    private Complex[][] discretedFourierTransform(Complex[][] source, boolean forward) {
        int factor = forward? 1 : -1;
        int width = source[0].length;
        int height = source.length;

        Complex[][] result = new Complex[height][width];

        for (int u = 0; u < height; u++) {
            for (int v = 0; v < width; v++) {
                Complex coefficient = new Complex();
                for (int i = 0; i < height; i ++) {
                    for (int j = 0; j < width; j++) {
                        Complex basis = Complex.euler(factor, (float) (2 * Math.PI * (u * i / height + v * j / width)));
                        try {
                            //System.out.println("Real part of basis is " + basis.real);
                            //System.out.println("Imaginary part of basis is " + basis.img);
                            coefficient.plus(source[i][j].times(basis));
                            //System.out.println("Real part of coefficient is " + coefficient.real);
                            //System.out.println("Imaginary part of coefficient is " + coefficient.img);
                            result[u][v] = coefficient;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            //System.out.println("u is " + u);
                            //System.out.println("v is " + v);
                            //System.out.println("result height is " + result[0].length);
                            //System.out.println("result width is " + result.length);
                            System.exit(-1);
                        }
                    }
                }

                //System.out.println("coefficient[" + u + "][" + v + "] is " + coefficient.getMagnitude());
            }
            //System.out.println("u = " + u);
        }
        return result;
    }

    /**
    private void combineBands(List<Complex[][]> src) {
        int width = processedRealForPrint[0].length;
        int height = processedRealForPrint.length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Complex temp = new Complex();
                for (int b = 0; b < src.size(); b++) {
                    Complex[][] tmpC = src.get(b);
                    System.out.println("tmpC[" + i + "][" + j + "] real value is " + tmpC[i][j].real);
                    temp.plus(tmpC[i][j]);
                    if (b == src.size() - 1) {

                        System.out.println("processedRealForPrint[" + i + "][" + j + "] is " + temp.real);
                    }
                    try {
                        temp.plus(tmpC[i][j]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("i is " + i);
                        System.out.println("j is " + j);
                        System.out.println("tempC heigth is " + tmpC[0].length);
                        System.out.println("tempC width is " + tmpC.length);
                    }
                }
                //System.out.println("processedRealForPrint[" + i + "][" + j + "] is " + temp.getMagnitude());
                processedRealForPrint[i][j] = temp.getMagnitude();
                processedImgForPrint[i][j] = temp.getPhase();
            }
        }
    }*/


    public void imgDFT(BufferedImage img, boolean forward) {
        if (img == null) {
            System.exit(-1);
        }
        complexImg = imageToComplex(img);

        /**
        BufferedImage returnRealImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //BufferedImage returnImgImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //for (int b = 0; b < returnRealImg.getRaster().getNumBands(); b++) {
        for (int b = 0; b < 1; b++) {
            processedBand = discretedFourierTransform(complexImg.get(b), forward);
            for (int i = 0; i < processedBand.length; i++) {
                for (int j = 0; j < processedBand[0].length; j++) {
                    //System.out.println("the color value of real part is going to be set is processedBand[" + i +
                            //"][" + j + "] is " + processedBand[i][j].real);
                    //float logCompressed = (float) Math.log(processedBand[i][j].real);
                    //System.out.println("The log compress value is " + processedBand[i][j].real);
                    returnRealImg.getRaster().setSample(j, i, b, processedBand[i][j].real / imageHeight * imageWidth);

                    //System.out.println("the color value is going to be set is processedReal[" + i +
                           // "][" + j + "] is " + processedBand[i][j].real + ", and the RGB value is " +
                            //returnRealImg.getRGB(j, i));
                    //System.out.println("Image[" + i + "][" + j + " value is " + (int)(processedImgForPrint[i][j]));
                }
            }
            System.out.println("Image is done");
            System.out.println("Is this image null?? " + returnRealImg == null);
         } */
        //combineBands(processedImg);
        //System.out.println("Combination is done");

        processedBand = discretedFourierTransform(complexImg.get(0), forward);
        System.out.println(processedBand[0].length);
        System.out.println(processedBand.length);
        int[] xPosition = new int[(processedBand[0].length) * (processedBand.length)];
        int[] yPosition = new int[(processedBand[0].length) * (processedBand.length)];
        System.out.println("ploting size is " + xPosition.length);
        int index = 0;
        for (int i = 0; i < processedBand.length; i++) {
            for (int j = 0; j < processedBand[0].length; j++) {
                Complex tempSample = processedBand[i][j];
                int onX = (int)(tempSample.getMagnitude() * Math.cos(tempSample.getPhase()));
                int onY = (int)(tempSample.getMagnitude() * Math.sin(tempSample.getPhase()));
                xPosition[index] = onX;
                yPosition[index] = onY;
                index++;
            }
        }
        JFrame frame = new JFrame("DFT");
        TestPanel panel = new TestPanel();
        panel.setCoordinate(xPosition, yPosition);
        frame.setSize(1000, 1000);
        panel.setSize(new Dimension(900, 900));
        panel.setBackground(Color.BLACK);

        frame.add(panel);
        //panel.paintComponent(frame.getGraphics());
        panel.setVisible(true);
        frame.setVisible(true);
        panel.repaint();
        frame.repaint();

    }

    public class TestPanel extends JPanel implements ActionListener {
        private int plotX[];
        private int plotY[];
        private Timer timer;
        public JPanel panel;

        public TestPanel() {
            panel = new JPanel();
            panel.setPreferredSize(new Dimension(800, 700));
            panel.setBackground(Color.BLACK);
            timer = new Timer(100, this);
            timer.start();

        }
        public void setCoordinate(int x[], int y[]) {
            this.plotX = x;
            this.plotY = y;
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            int index = 0;
            while (index < plotX.length) {
                System.out.println(plotX[index]);
                System.out.println(plotY[index]);
                g.fillOval((int)Math.log(plotX[index]), plotY[index] + 400, 2, 2);
                //repaint();
                index++;
            }
        }


        public void actionPerformed(ActionEvent e) {
            repaint();
        }


    }

    public static void main(String[] args) {
        DTF test = new DTF();
        BufferedImage img = null;
        File file = new File("/Users/mali/Desktop/Image_Processing/Project_2/img/hat.jpg");
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.imgDFT(img, true);

    }


}
