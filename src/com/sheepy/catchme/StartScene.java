package com.sheepy.catchme;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
public class StartScene extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame fr;
	private JButton bn1, bn2, bn3;
        private JPanel p0;
	public StartScene() {
		fr = new JFrame(Game.TITLE);
		fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setLayout(new GridBagLayout());
                p0 = new JPanel();
                p0.setLayout(new GridLayout(5,1));
		bn1 = new JButton("New Game");
                bn2 = new JButton("Join Game");
                bn3 = new JButton("How To Play");
                
		bn1.setFont(new Font("TimesRoman", Font.BOLD, 20));
                bn2.setFont(new Font("TimesRoman", Font.BOLD, 20));
                bn3.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
                
                p0.add(bn1);
//                System.out.println("add1");
                p0.add(new JLabel());
                p0.add(bn2);
                p0.add(new JLabel());
                p0.add(bn3);
//                System.out.println("add2");
                this.add(p0);
		fr.add(this);
		fr.setSize(640, 640);
		fr.setResizable(false);
		fr.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		// paint ...
		super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
            try {
			BufferedImage img = ImageIO.read(getClass().getResource("image/game_start.png"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
			System.out.println(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
        
	
	public static void main(String[] args) {
		new StartScene();
	}
	
}
