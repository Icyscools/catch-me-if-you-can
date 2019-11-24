/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepy.catchme;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author ckrittima
 */
public class Howtoplay extends JPanel implements ActionListener{

    private JFrame fr;
    private JButton btn;
    private JPanel p0;
    
    public Howtoplay() {
        this(new JFrame(Game.TITLE));
    }
    public Howtoplay(JFrame frame) {
        this.fr = frame;
//        fr = new JFrame(Game.TITLE);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setLayout(new GridBagLayout());
        p0 = new JPanel();
        p0.setLayout(new BorderLayout());
        btn = new JButton("Back to Menu");
        
        btn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        
        btn.addActionListener(this);
        btn.setVisible(true);
        
        p0.add(btn, BorderLayout.SOUTH);
        this.add(p0);
        fr.add(this);
        fr.pack();
        fr.setSize(640, 640);
        fr.setResizable(false);
        fr.setVisible(true);
        fr.revalidate();
        fr.repaint();

    }


    @Override
    public void paintComponent(Graphics g) {
        // paint ...
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            BufferedImage img = ImageIO.read(getClass().getResource("image/Howto.png"));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
            System.out.println(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Howtoplay();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn)) {
            new WaitingRoom(this.fr);
        }
    }
}
