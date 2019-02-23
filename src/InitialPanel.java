import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


import javax.swing.*;

public class InitialPanel extends JPanel implements ActionListener {
    private BufferedImage img;
    private boolean flag;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private Timer timer;



    public InitialPanel() {
        flag = true;
        this.setPreferredSize(new Dimension(HEIGHT, WIDTH));
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.timer = new Timer(100, this);
        initiating();
        timer.start();

    }


    public void initiating() {

        if (flag) {
            //BufferedImage initImage = img;

            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    int a = (int) (Math.random() * 256); //alpha
                    int r = (int) (Math.random() * 256); //red
                    int g = (int) (Math.random() * 256); //green
                    int b = (int) (Math.random() * 256); //blue
                    int p = (a << 24) | (r << 16) | (g << 8) | b;
                    img.setRGB(j, i, p);

                }
            }

            //this.repaint();
        }


    }
    public void setFlag() {
        this.flag = false;
    }

    public void setImg(BufferedImage image) {

        this.img = image;
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img,0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!this.isVisible()) {
            flag = false;
        } else {
            flag = true;
        }

        if (flag) {
            initiating();
        }

        repaint();

    }

}