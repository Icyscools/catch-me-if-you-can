/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepy.catchme;

import com.sheepy.catchme.util.Colors;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Howtoplay extends JPanel implements ActionListener {

    private JFrame fr;
    private JButton btn;
    private JPanel p0, p1, p2, p3, p4;

    public Howtoplay() {
        this(Client.client.getJFrame());
    }

    public Howtoplay(JFrame frame) {
        this.fr = frame;
        this.fr.getContentPane().removeAll();
        this.fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        p0 = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        this.setLayout(new BorderLayout());
        p1.setLayout(new GridLayout(3, 1));
        p2.setLayout(new FlowLayout());

        p0.setOpaque(false);
        p1.setOpaque(false);
        p2.setOpaque(false);
        p3.setOpaque(false);
        p4.setOpaque(false);

        btn = new JButton("Back");
        btn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        btn.setBackground(Color.white);
        btn.setForeground(Colors.blue);

        btn.addActionListener(this);

        p2.add(btn);
        p1.add(p2);
        this.add(p1, BorderLayout.SOUTH);
        this.add(p0);

        fr.add(this);
        fr.pack();
		fr.setSize(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn)) {
            new StartScene();
        }
    }
}
