import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DemoPanel extends JPanel implements ActionListener {
    private BufferedImage img;
    private boolean flag;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private Timer timer;
    private int centerX;
    private int centerY;
    private int r = 100;
    private int x_one;
    private int y_one;
    private int x_two;
    private int y_two;
    private int x_three;
    private int y_three;
    private int degree = 0;


    public DemoPanel() {

        centerY = 400;
        centerX = 500;
        flag = true;
        this.setPreferredSize(new Dimension(HEIGHT, WIDTH));
        img = new BufferedImage(HEIGHT, WIDTH, BufferedImage.TYPE_INT_RGB);
        this.timer = new Timer(1000 / 24, this);
        timer.start();

    }

    public Timer getTimer() {
        return timer;
    }

    public void initiating() {
        /**
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                int a = (int)(Math.random()*256); //alpha
                int r = (int)(Math.random()*256); //red
                int g = (int)(Math.random()*256); //green
                int b = (int)(Math.random()*256); //blue
                int p = (a<<24) | (r<<16) | (g<<8) | b;
                img.setRGB(i, j, p);

            }
        }*/
        degree = (degree + 360 / 24) % 360;
        x_one = centerX + (int)(r * Math.sin(degree));
        y_one = centerY + (int)(r * Math.cos(degree));
        x_two = centerX + (int)(r * Math.sin(degree));
        y_two = centerY + (int)(r * Math.cos(degree));
        x_three = centerX + (int)(r * Math.sin(degree));
        y_three = centerY + (int)(r * Math.cos(degree));
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        //g.drawImage(img,0, 0, this);
        g.fillOval(x_one, y_one, 10, 10 );

        g.setColor(Color.GREEN);
        g.fillOval(x_two, y_two, 10, 10);

        g.setColor(Color.BLUE);
        g.fillOval(x_three, y_three, 10, 10);
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