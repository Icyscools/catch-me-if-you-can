package com.sheepy.catchme;
import java.awt.*;
import javax.swing.*;
public class StartScene extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame fr;
	private JPanel buttons;
	
	public StartScene() {
		fr = new JFrame(Game.TITLE);
		buttons = new JPanel(new GridBagLayout());
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttons.add(new JButton("Start"));
		fr.add(buttons);
		fr.setSize(640, 640);
		fr.setVisible(true);
	}
	
	public static void main(String[] args) {
		new StartScene();
	}
	
}
