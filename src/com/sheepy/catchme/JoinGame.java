package com.sheepy.catchme;

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
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class JoinGame extends JPanel implements ActionListener {

	private JFrame fr;
	private JButton btn1, btn2;
	private JPanel p0, p1, p2, p3, p4;
	private JTextField pass;
	private JLabel lb1, lb2;

	public JoinGame() {
		this(Client.client.getJFrame());
	}

	public JoinGame(JFrame frame) {
		this.fr = frame;
		this.fr.getContentPane().removeAll();
		this.fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		p0 = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		this.setLayout(new GridLayout(3, 1));
		p1.setLayout(new BorderLayout());
		p2.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		p4.setLayout(new GridLayout(2, 1));

		p0.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);

		lb1 = new JLabel("Join Game", JLabel.CENTER);
		lb1.setFont(new Font("TimesRoman", Font.BOLD, 45));

		lb2 = new JLabel("Room ID:", JLabel.CENTER);
		lb2.setFont(new Font("TimesRoman", Font.BOLD, 18));
		pass = new JTextField(10);
		pass.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 20));
		btn1 = new JButton("Join");
		btn1.setFont(new Font("TimesRoman", Font.BOLD, 20));
		btn2 = new JButton("Back");
		btn2.setFont(new Font("TimesRoman", Font.BOLD, 20));

		btn1.addActionListener(this);
		btn2.addActionListener(this);

		p1.add(lb1, BorderLayout.SOUTH);
		p2.add(lb2);
		p2.add(pass);
		p3.add(btn1);
		p3.add(btn2);
		p4.add(p2);
		p4.add(p3);

		this.add(p1);
		this.add(p4);

		this.fr.add(this);
		this.fr.pack();
		this.fr.setSize(640, 640);
		this.fr.setResizable(false);
		this.fr.setVisible(true);
		this.fr.revalidate();
		this.fr.repaint();
	}

	public static void main(String[] args) {
		new JoinGame();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// paint ...
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		try {
			BufferedImage img = ImageIO.read(getClass().getResource("image/join_player.png"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(btn1)) {
			try {
				Object response = Client.client.startConnection("joingame", pass.getText());
				if (response instanceof WaitingRoom) {
					WaitingRoom waitingRoom = (WaitingRoom) response;
					waitingRoom.addPlayer(Client.client);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (ae.getSource().equals(btn2)) {
			new StartScene(this.fr);
		}
	}
}
