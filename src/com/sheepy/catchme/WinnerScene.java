package com.sheepy.catchme;

import com.sheepy.catchme.events.GameEndEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class WinnerScene extends JPanel implements ActionListener {

    private JFrame fr;
    private JButton btn1, btn2;
    private JPanel p0, p1, p2, p3, p4;
    private JTextField pass;
    private JLabel lb1, lb2;
    private String winnerSide;
    private BufferedImage winnerImage;
    
    public WinnerScene(String winnerSide) {
        this.winnerSide = winnerSide;
        try {
            this.winnerImage = this.winnerSide.equals("Werewolf") ?
                    ImageIO.read(getClass().getResource("image/Wolf-win.png")) :
                    ImageIO.read(getClass().getResource("image/Sheep-win.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.fr = new JFrame();
        this.init();
    }
    
    public WinnerScene(String winnerSide, BufferedImage winnerImage) {
        this(new JFrame(), winnerSide, winnerImage);
    }
    
    public WinnerScene(JFrame frame, String winnerSide, BufferedImage winnerImage) {
        this.fr = frame;
        this.winnerSide = winnerSide;
        this.winnerImage = winnerImage;
        this.init();
    }

    public void init() {
        this.fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        p0 = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        this.setLayout(new GridLayout(4, 1));
        p1.setLayout(new BorderLayout());
        p2.setLayout(new FlowLayout());
        p3.setLayout(new FlowLayout());
        p4.setLayout(new GridLayout(2, 1));

        p0.setOpaque(false);
        p1.setOpaque(false);
        p2.setOpaque(false);
        p3.setOpaque(false);
        p4.setOpaque(false);

        btn1 = new JButton("Main Menu");
        btn1.setFont(new Font("TimesRoman", Font.BOLD, 20));
        btn1.setBackground(Color.white);
        btn1.setForeground(new Color(110, 15, 109));
        btn1.addActionListener(this);

        p2.add(btn1);
        p4.add(p2);

        this.add(p1);
        this.add(p4);

        fr.add(this);
        fr.pack();
        fr.setSize(640, 640);
        fr.setResizable(false);
        fr.setVisible(true);
        fr.revalidate();
        fr.repaint();
    }

    public void paintComponent(Graphics g) {
        // paint ...
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(this.winnerImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public static void main(String[] args) {
        new WinnerScene("Sheep");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(btn1)) {
            new StartScene(this.fr);
        }

    }
}
