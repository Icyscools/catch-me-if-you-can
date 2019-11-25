package com.sheepy.catchme;

import com.sheepy.catchme.util.Colors;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScene extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JFrame fr;
    private JButton bn1, bn2, bn3;
    private JPanel p0;

    public StartScene() {
        this(new JFrame(Game.TITLE));
    }
    
    public StartScene(JFrame fr) {
        this.fr = fr;
        this.fr.getContentPane().removeAll();
        this.fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        this.setLayout(new GridBagLayout());
        p0 = new JPanel();
        GridLayout grid = new GridLayout(3, 1);
        p0.setLayout(grid);
        bn1 = new JButton("New Game");
        bn2 = new JButton("Join Game");
        bn3 = new JButton("How To Play");

        bn1.setFont(new Font("TimesRoman", Font.BOLD, 20));
        bn2.setFont(new Font("TimesRoman", Font.BOLD, 20));
        bn3.setFont(new Font("TimesRoman", Font.BOLD, 20));
        bn1.setBackground(Color.white);
        bn1.setForeground(Colors.blue);
        bn2.setBackground(Color.white);
        bn2.setForeground(Colors.blue);
        bn3.setBackground(Color.white);
        bn3.setForeground(Colors.blue);


        bn1.addActionListener(this);
        bn2.addActionListener(this);
        bn3.addActionListener(this);
        bn1.setVisible(true);
        bn2.setVisible(true);
        bn3.setVisible(true);

        p0.add(bn1);
        p0.add(bn2);
        p0.add(bn3);

        this.add(p0);
        this.fr.add(this);
        this.fr.pack();
        this.fr.setSize(640, 640);
        this.fr.setResizable(false);
        this.fr.setVisible(true);
        this.fr.revalidate();
        this.fr.repaint();
        System.out.println(Client.account.toString());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // paint ...
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            BufferedImage img = ImageIO.read(getClass().getResource("image/game_start.png"));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new StartScene();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(bn1)) {
            new WaitingRoom(this.fr);
        }
        if (e.getSource().equals(bn2)) {
        	new JoinGame(this.fr);
        }
        if (e.getSource().equals(bn3)) {
            new Howtoplay(this.fr);
        }
    }

}
