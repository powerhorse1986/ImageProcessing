import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class TestDemo extends JFrame {
    public TestDemo() {

        initUI();
    }

    private void initUI() {

        DemoPanel surface = new DemoPanel();
        add(surface);

        this.addWindowListener(new WindowAdapter() {

            public void windowClosing() {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Points");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                TestDemo ex = new TestDemo();
                ex.setVisible(true);
            }
        });
    }
}
