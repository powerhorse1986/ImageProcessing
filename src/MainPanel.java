import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author mali
 *
 * The class MainPanel constructs the GUI for the main functions
 * */

public class MainPanel extends JFrame {
    private Container leftPanel, rightPanel, displayPanel;

    private JButton b1, b2, b3, b4, b5, b6, b7, b8;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openItem, exitItem;

    private InitialPanel initialPanel;
    private JPanel imagePanel, offPanel;

    private BufferedImage srcImg, destImg, paintImg;

    private GaussFilterOp op;
    private UnsharpOp unsharpOp;
    private SobelOp sobel;
    private InvertOp inverter;
    private BWOp bwOp;

    private int albumIndex;
    private int finalIndex;
    private BufferedImage[] album;
    private boolean albumIsFull;
    private boolean[] hasPic;

    private boolean filteredByGauss;



    public MainPanel() {
        album = new BufferedImage[9];
        hasPic = new boolean[9];
        this.leftPanel = new JPanel();
        this.displayPanel = new JPanel();
        this.rightPanel = new JPanel();

        this.leftPanel.setLayout(new GridLayout(4, 1));
        this.displayPanel.setLayout(new BorderLayout());
        this.rightPanel.setLayout(new GridLayout(4, 1));
        b1 = new JButton("BW");
        b2 = new JButton("Invert");
        b3 = new JButton("Unsharp");
        b4 = new JButton("Sobel");
        b5 = new JButton("Gaussian");
        b6 = new JButton("Fourier");
        b7 = new JButton("Back");
        b8 = new JButton("Reset");



        b1.setToolTipText("Clicking this button will show you a black and white picture");
        b2.setToolTipText("An inverted pic will be shown");
        b3.setToolTipText("Unsharp");
        b4.setToolTipText("Black and white");
        b5.setToolTipText("Gauss transfer");
        b6.setToolTipText("Fourier");
        b7.setToolTipText("Back to the original");
        b8.setToolTipText("Give up this pic");

        this.imagePanel = new JPanel() {
            public void paintComponent(Graphics graphics) {
                graphics.drawImage(paintImg, 0, 0, this);
            }
        };

        this.leftPanel.add(b1);
        this.leftPanel.add(b2);
        this.leftPanel.add(b3);
        this.leftPanel.add(b4);

        this.initialPanel = new InitialPanel();
        this.initialPanel.setVisible(true);

        this.rightPanel.add(b5);
        this.rightPanel.add(b6);
        this.rightPanel.add(b7);
        this.rightPanel.add(b8);

        this.displayPanel.add(initialPanel);

        this.getContentPane().add(this.leftPanel, BorderLayout.WEST);
        this.getContentPane().add(this.displayPanel, BorderLayout.CENTER);
        this.getContentPane().add(this.rightPanel, BorderLayout.EAST);
        this.setAction();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMenuBar();
        this.setTitle("Image processing demo");
        this.pack();
        this.setVisible(true);

    }

    private void setAction() {
        b1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                bwConvert();

            }
        });
        b2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                invertOp();
            }
        });
        b3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                unsharpOp();
            }
        });
        b4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sobelOp();
            }
        });
        b5.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gaussOp();
            }
        });
        b6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        b7.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                backWard();
            }
        } );
        b8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (offPanel.isVisible()) {
                    offPanel.setVisible(false);
                    offPanel.removeAll();
                    imagePanel.setVisible(true);
                    displayPanel.repaint();
                } else if (imagePanel.isVisible()) {
                    imagePanel.setVisible(false);
                    imagePanel.removeAll();
                    initialPanel.setVisible(true);
                    displayPanel.repaint();
                } else {
                    displayPanel.repaint();
                }
            }
        });
    }

    /**
     * Setting up the menu bar for selecting pictures
     * */
    private void setMenuBar() {
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                openFile();

            }
        });
        fileMenu.add(openItem);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener()	{
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(exitItem);
        setJMenuBar(menuBar);
    }

    private void backWard() {
        albumIndex--;
        if (hasPic[0]) {
            if (albumIndex < 0 && finalIndex >= 0) {
                albumIndex = finalIndex;
            }
            System.out.println("albumIndex is " + albumIndex);
            System.out.println("finalIndex is  " + finalIndex);
            paintImg = album[albumIndex];
            //imagePanel.getGraphics().drawImage(previousPic, 0, 0, imagePanel);
            System.out.println("paintImg is null? " + paintImg.equals(null));
            imagePanel.setSize(paintImg.getWidth(), paintImg.getHeight());
            imagePanel.repaint();
            getContentPane().repaint();
        }
    }


    /**
     * Processing pic with gauss kernel
     * */

    private void gaussOp() {
        op = new GaussFilterOp();
        if (!hasPic[0]) {
            openFile();
        } else {
            srcImg = album[0];
            destImg = op.filter(srcImg);
            System.out.println("Next step???");
            filteredByGauss = true;
            setPicture(destImg);
        }
    }

    /**
     * Unsharp a pic based on gaussOp
     * */

    private void unsharpOp() {
        unsharpOp = new UnsharpOp();
        if (!filteredByGauss) {
            gaussOp();
        } else {
            destImg = unsharpOp.unsharp(srcImg, destImg);
            setPicture(destImg);
        }

    }

    /**
     * Performing the sobel filter
     * */
    private void sobelOp() {
        sobel = new SobelOp();
        if (srcImg == null) {
            openFile();
        } else {
            srcImg = album[0];
            destImg = sobel.sobelFilter(srcImg);
            setPicture(destImg);
        }
    }

    /**
     * Creating an inverted pic
     * */
    private void invertOp() {
        inverter = new InvertOp();
        if (srcImg == null) {
            openFile();
        } else {
            srcImg = album[0];
            destImg = inverter.filter(srcImg);
            setPicture(destImg);
        }
    }

    private void bwConvert() {
        bwOp = new BWOp();
        if (srcImg == null) {
            openFile();
        } else {
            srcImg = album[0];
            destImg = bwOp.bwOp(srcImg);
            setPicture(destImg);
        }
    }


    /**
     * Open a file and load the image.
     */
    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        String[] extensions = ImageIO.getReaderFileSuffixes();
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", extensions));
        int r = chooser.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            srcImg = ImageIO.read(chooser.getSelectedFile());
            setPicture(srcImg);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }

        setResizable(true);
    }

    private void setPicture(BufferedImage processedImg) {

        if (initialPanel.isVisible()) {
            initialPanel.setVisible(false);
        }

        albumIndex %= album.length;
        if (albumIndex == 0 && hasPic[albumIndex]) {
            albumIndex++;
        }
        if (!albumIsFull && albumIndex != 0) {
            albumIndex = finalIndex + 1;
        }

        album[albumIndex] = processedImg;
        hasPic[albumIndex] = true;

        if (!albumIsFull) {
            if (albumIndex == album.length - 1) {
                albumIsFull = true;
            }
            finalIndex = albumIndex;
        }
        albumIndex++;

        paintImg = processedImg;
        imagePanel.setVisible(true);
        imagePanel.setSize(new Dimension(processedImg.getHeight(), processedImg.getWidth()));
        imagePanel.repaint();

        this.displayPanel.add(imagePanel, BorderLayout.CENTER);
        this.setResizable(true);
        this.getContentPane().repaint();


    }



    public static void main(String args[]){
        MainPanel t = new MainPanel();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
