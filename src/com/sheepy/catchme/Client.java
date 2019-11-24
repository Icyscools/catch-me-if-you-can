package com.sheepy.catchme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import org.bson.Document;

public class Client implements ActionListener {
	private JFrame fr;
	private JPanel p0, p1, p2, p3, p4;
	private JLabel instructionLb, ipLb, titleLb, userLb, passLb, status;
	private JTextField ipTf, userTf;
	private JPasswordField passTf;
	private JButton connectBtn, loginBtn, regisBtn;
	private Socket clientSocket;
	private ObjectOutputStream toServer;	// Request to server
	private ObjectInputStream fromServer;	// Response from server

	public Client() {
		fr = new JFrame("Catch me if you can - Client");
		p0 = new JPanel();	// Title Label
		p1 = new JPanel();	// Username / Instruction
		p2 = new JPanel();	// Password / Server's IP Address
		p3 = new JPanel();	// Button
		p4 = new JPanel();	// Status
		titleLb = new JLabel("Catch me if you can");
		instructionLb = new JLabel("Enter Server's IP Address to continue");
		ipLb = new JLabel("Server IP: ");
		userLb = new JLabel("Username: ");
		passLb = new JLabel("Password: ");
		status = new JLabel("");
		ipTf = new JTextField("", 15);
		userTf = new JTextField("", 15);
		passTf = new JPasswordField("", 15);
		connectBtn = new JButton("Connect");
		loginBtn = new JButton("Login");
		regisBtn = new JButton("Register");
		
		titleLb.setFont(new Font("TimesRoman", Font.BOLD, 24));
		passTf.setEchoChar('*');
		
		fr.setSize(800, 600);
		fr.setLayout(new GridLayout(8, 1));
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p0.setLayout(new FlowLayout());
		p1.setLayout(new FlowLayout());
		p2.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		p4.setLayout(new FlowLayout());
		
		loginBtn.addActionListener(this);
		regisBtn.addActionListener(this);
		connectBtn.addActionListener(this);
		
		this.showConnectionPanel();
		
		fr.add(new JPanel());
		fr.add(p0);
		fr.add(p1);
		fr.add(p2);
		fr.add(p3);
		fr.add(p4);
	}
	
	public void updatePanel() {
		p0.updateUI();
		p1.updateUI();
		p2.updateUI();
		p3.updateUI();
		p4.updateUI();
	}
	
	public void clearAllPanel() {
		p0.removeAll();
		p1.removeAll();
		p2.removeAll();
		p3.removeAll();
		p4.removeAll();
		this.updatePanel();
	}
	
	public void showConnectionPanel() {
		this.clearAllPanel();
		p0.add(titleLb);
		p1.add(instructionLb);
		p2.add(ipLb);
		p2.add(ipTf);
		p3.add(connectBtn);
		p4.add(status);
		this.updatePanel();
	}
	
	public void showLogin() {
		this.clearAllPanel();
		p0.add(titleLb);
		p1.add(userLb);
		p1.add(userTf);
		p2.add(passLb);
		p2.add(passTf);
		p3.add(loginBtn);
		p3.add(regisBtn);
		p4.add(status);
		this.updatePanel();
	}
	
	public void showLobby() {
		this.clearAllPanel();
		p0.add(titleLb);
		p1.add(new JLabel("Lobby"));
		this.updatePanel();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(connectBtn)) {
			try {
				this.tryConnection(ipTf.getText(), 5555);
				this.showLogin();
				this.stopConnection();
			}
			catch (Exception ex) {
				status.setText("Connection failed.");
			}
		}
		else if (e.getSource().equals(loginBtn)) {
			try {
				status.setText("Connecting to Server....");
				this.startConnection("127.0.0.1", 5555, "login");
				new StartScene().init();
			}
			catch (Exception ex) {
				status.setText(ex.toString());
			}
		}
		else if (e.getSource().equals(regisBtn)) {
			try {
				if (userTf.getText().length() < 3) {
					status.setText("Username must be at least 3 characters long.");
				}
				
				else if (String.valueOf(passTf.getPassword()).length() < 8 ) {
					status.setText("Password must be at least 8 characters long.");
				}
				
				else {
					status.setText("Connecting to Server....");
					this.startConnection("127.0.0.1", 5555, "regis");
					this.stopConnection();
				}
			}
			catch (Exception ex) {
				status.setText(ex.toString());
			}
		}
	}
	
	public void tryConnection(String ip, int port) throws UnknownHostException, IOException {
		/* Try to connect to Server */
		clientSocket = new Socket(ip, port);
	}
	
	public void startConnection(String ip, int port, String option) throws UnknownHostException, IOException, ClassNotFoundException {
		/* Connecting to Server Socket */
		clientSocket = new Socket(ip, port);
		status.setText("Connected.");
		
		/* Get user's input from Text Field */
		String username = userTf.getText();
		String password = String.copyValueOf(passTf.getPassword());
		
		/* Create Document from Text Field & Password Field */
		Document userInfo = new Document("username", username)
				.append("password", password);
		
		/* Index 0 contains option ("regis", "login") */
		Object[] documentArr = new Object[2];
		documentArr[0] = option;
		documentArr[1] = userInfo;

		/* Create I/O Stream */
		toServer = new ObjectOutputStream(clientSocket.getOutputStream());
		fromServer = new ObjectInputStream(clientSocket.getInputStream());
		
		/* Send Document to Server */
		toServer.writeObject(documentArr);
		status.setText((String) fromServer.readObject());
	}
	
	public void stopConnection() throws IOException {
		clientSocket.close();
		toServer.close();
		fromServer.close();
	}
	
	public static void main(String[] args) {
		new Client();
	}
}
