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
	private JButton bn1, bn2;
	private JPanel p0, p1, p2, p4;
	private JLabel lb;

	public StartScene() {
		this(Client.client.getJFrame());
	}

	public StartScene(JFrame fr) {
		this.fr = fr;
		this.fr.setLayout(new BorderLayout());
		this.fr.getContentPane().removeAll();

		p0 = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p4 = new JPanel();
		lb = new JLabel();

		this.setLayout(new GridLayout(3, 1));
		p0.setLayout(new BorderLayout());
		p1.setLayout(new FlowLayout());
		p2.setLayout(new FlowLayout());
		p4.setLayout(new GridLayout(3, 1));

		p0.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p4.setOpaque(false);

		bn1 = new JButton("Join Game");
		bn2 = new JButton("How To Play");

		bn1.setFont(new Font("TimesRoman", Font.BOLD, 20));
		bn2.setFont(new Font("TimesRoman", Font.BOLD, 20));
		bn1.setBackground(Color.white);
		bn1.setForeground(Colors.blue);
		bn2.setBackground(Color.white);
		bn2.setForeground(Colors.blue);


		bn1.setPreferredSize(new Dimension(160, 40));
		bn2.setPreferredSize(new Dimension(160, 40));

		bn1.addActionListener(this);
		bn2.addActionListener(this);

		bn1.setVisible(true);
		bn2.setVisible(true);

		p1.add(bn1);
		p2.add(bn2);
		p4.add(p1);
		p4.add(p2);
		p0.add(p4, BorderLayout.SOUTH);

		this.add(lb);
		this.add(p0);
		this.fr.add(this);
		this.fr.pack();
		this.fr.setSize(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		this.fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.fr.setResizable(false);
		this.fr.setVisible(true);
		this.fr.revalidate();
		this.fr.repaint();
		System.out.println(Client.client.getAccount().toString());
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bn1)) {
			new WaitingRoom();
		}
		if (e.getSource().equals(bn2)) {
			new Howtoplay();
		}

	}
}
