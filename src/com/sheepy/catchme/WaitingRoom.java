/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sheepy.catchme;

import com.sheepy.catchme.util.Colors;

import java.awt.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.awt.Color;

public class WaitingRoom extends JPanel implements ActionListener, Serializable {

	private JFrame frame;
	private JPanel p1, p2, p3;
	private JLabel lb;
	private JTextField tf1, tf2, tf3, tf4, tf5;
	private JButton btn, btn1;
//	private ObjectInputStream fromServer;
//	private ObjectOutputStream toServer;

	public WaitingRoom() {
		this(_Client.client.getJFrame());
	}

	public WaitingRoom(JFrame frame) {
		this.frame = frame;
		this.init();
	}

	public void init() {
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		lb = new JLabel("Waiting For Players...", JLabel.CENTER);
		tf1 = new JTextField();
		tf2 = new JTextField();
		tf3 = new JTextField();
		tf4 = new JTextField();
		tf5 = new JTextField();
		btn = new JButton("Start");
		btn1 = new JButton("Leave");

		btn.addActionListener(this);
		btn1.addActionListener(this);

		tf1.setEditable(false);
		tf2.setEditable(false);
		tf3.setEditable(false);
		tf4.setEditable(false);
		tf5.setEditable(false);

		lb.setFont(new Font("TimesRoman", Font.BOLD, 45));
		tf1.setFont(new Font("TimesRoman", Font.BOLD, 30));
		tf2.setFont(new Font("TimesRoman", Font.BOLD, 30));
		tf3.setFont(new Font("TimesRoman", Font.BOLD, 30));
		tf4.setFont(new Font("TimesRoman", Font.BOLD, 30));
		tf5.setFont(new Font("TimesRoman", Font.BOLD, 30));
		btn.setFont(new Font("TimesRoman", Font.BOLD, 23));
		btn1.setFont(new Font("TimesRoman", Font.BOLD, 23));

		btn.setBackground(Color.white);
		btn1.setBackground(Color.white);

		tf1.setBackground(Colors.lgrey);
		tf2.setBackground(Colors.lgrey);
		tf3.setBackground(Colors.lgrey);
		tf4.setBackground(Colors.lgrey);
		tf5.setBackground(Colors.lgrey);

		// tf1.setBackground(Colors.lred);
		// tf2.setBackground(Colors.lyellow);
		// tf3.setBackground(Colors.lgreen);
		// tf4.setBackground(Colors.lblue);
		// tf5.setBackground(Colors.lviolet);
		p2.setBackground(Colors.lgrey);
		p3.setBackground(Colors.lgrey);

		btn.setForeground(Colors.blue);
		btn1.setForeground(Colors.blue);
		lb.setForeground(Colors.blue);
		tf5.setForeground(Color.white);
		tf4.setForeground(Color.white);
		tf3.setForeground(Color.white);
		tf2.setForeground(Color.white);
		tf1.setForeground(Color.white);

		tf1.setBorder(null);
		tf2.setBorder(null);
		tf3.setBorder(null);
		tf4.setBorder(null);
		tf5.setBorder(null);

		tf1.setHorizontalAlignment(JTextField.CENTER);
		tf2.setHorizontalAlignment(JTextField.CENTER);
		tf3.setHorizontalAlignment(JTextField.CENTER);
		tf4.setHorizontalAlignment(JTextField.CENTER);
		tf5.setHorizontalAlignment(JTextField.CENTER);

		// tf1.setText("Sheep 1");
		// tf2.setText("Sheep 2");
		// tf3.setText("Sheep 3");
		// tf4.setText("Sheep 4");
		// tf5.setText("Wolf");

		this.frame.setLayout(new BorderLayout());
		p1.setLayout(new GridLayout(1, 5));
		p2.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		p1.add(tf1);
		p1.add(tf2);
		p1.add(tf3);
		p1.add(tf4);
		p1.add(tf5);
		p2.add(btn);
		p2.add(btn1);
		p3.add(lb);

		this.setLayout(new BorderLayout());
		this.add(p3, BorderLayout.NORTH);
		this.add(p1, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);
		this.setSize(640, 640);
		this.setVisible(true);

		this.frame.getContentPane().removeAll();
		this.frame.add(this);
		this.frame.pack();
		this.frame.setSize(640, 640);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		this.frame.revalidate();
		this.frame.repaint();
	}

	public void updatePlayer() {// List<Account> connectionPlayer) {
//		System.out.println(connectionPlayer);
//		switch (connectionPlayer.size()) {
//		case 5:
//			tf5.setBackground(Colors.lviolet);
//			tf5.setText(connectionPlayer.get(4).getUsername());
//		case 4:
//			tf4.setBackground(Colors.lblue);
//			tf4.setText(connectionPlayer.get(3).getUsername());
//		case 3:
//			tf3.setBackground(Colors.lgreen);
//			tf3.setText(connectionPlayer.get(2).getUsername());
//		case 2:
//			tf2.setBackground(Colors.lyellow);
//			tf2.setText(connectionPlayer.get(1).getUsername());
//		case 1:
//			tf1.setBackground(Colors.lred);
//			tf1.setText(connectionPlayer.get(0).getUsername());
//		default:
//			break;
//		}
		this.frame.revalidate();
		this.frame.repaint();
	}

	public Game startGame() {
		Game game = null;
		try {
			game = new Game(this.frame);
			game.startGame();
			return game;
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return game;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(btn)) {
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(false);
			this.startGame();
		} else if (ae.getSource().equals(btn1)){
			new StartScene();
		}
	}
}
