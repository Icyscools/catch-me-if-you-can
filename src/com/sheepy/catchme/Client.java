package com.sheepy.catchme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import org.bson.Document;

import com.sheepy.catchme.util.Colors;

public class Client implements ActionListener, Serializable {
	private JFrame frame;
	private JPanel p, p0, p1, p2, p3, p4;
	private JLabel instructionLb, ipLb, titleLb, userLb, passLb, status;
	private JTextField ipTf, userTf;
	private JPasswordField passTf;
	private JButton connectBtn, loginBtn, regisBtn;
	private Socket clientSocket;
	private ObjectOutputStream toServer;	// Request to server
	private ObjectInputStream fromServer;	// Response from server
	private Account account;
	public static Client client;
	public static String serverIp;
	public static int serverPort = 5555;

	public Client() {
		this.frame = new JFrame(Game.TITLE + " - Client");
		p = new JPanel() {
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
		};
		p0 = new JPanel();	// Title Label
		p1 = new JPanel();	// Username / Instruction
		p2 = new JPanel();	// Password / Server's IP Address
		p3 = new JPanel();	// Button
		p4 = new JPanel();	// Status
		titleLb = new JLabel(Game.TITLE);
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

		p0.setLayout(new FlowLayout());
		p1.setLayout(new FlowLayout());
		p2.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		p4.setLayout(new FlowLayout());
		p.setLayout(new GridLayout(8, 1));
		
		JPanel pt = new JPanel();
		pt.setOpaque(false);
		p0.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		
		p.add(pt);
		p.add(pt);
		p.add(p0);
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);
		p.revalidate();
		p.repaint();

		loginBtn.addActionListener(this);
		regisBtn.addActionListener(this);
		connectBtn.addActionListener(this);

		this.showConnectionPanel();

		this.frame.add(new JPanel());
		this.frame.add(p);

		this.frame.setSize(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
		client = this;
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
				status.setText("Connected.");
			}
			catch (IOException ex) {
				status.setText("Connection failed.");
			}
		}
		else if (e.getSource().equals(loginBtn)) {
			try {
				status.setText("Connecting to Server....");
				Object response = this.startConnection(ipTf.getText(), 5555, "login");
				if (response instanceof Account) {
					System.out.println("login success");
					status.setText("Login successful!");
					this.account = (Account) response;
					this.account.setClient(this);
					System.out.println(this.account.getUsername());
					System.out.println(clientSocket);
					Server.connectingClient.put(this.account.getUsername(), this.clientSocket); // Add this client (username, socket) to Hashmap
					serverIp = ipTf.getText();
					serverPort = 5555;
					new StartScene();
				} else {
					System.out.println("login fail");
					status.setText((String) response);
				}
				Server.showAllConnectingClients();
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
					Object response = this.startConnection(ipTf.getText(), 5555, "regis");
					System.out.println(response.toString());
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
		status.setText("Connected.");
	}

	public Object startConnection(String option, Object object) throws UnknownHostException, IOException, ClassNotFoundException {
		/* Connecting to Server Socket */
		clientSocket = new Socket(serverIp, serverPort);
		Object[] document;
		Object response = null;
		switch (option) {
		case "join":
			document = new Object[2];
			document[0] = option;
			document[1] = object;
			break;
		default:
			System.out.println("Option not found : " + option);
			return null;
		}

		if (document != null) {
			/* Create I/O Stream */
			toServer = new ObjectOutputStream(clientSocket.getOutputStream());
			fromServer = new ObjectInputStream(clientSocket.getInputStream());

			/* Send Document to Server */
			System.out.println(document[1].toString());
			toServer.writeObject(document);
			response = fromServer.readObject();
			System.out.println(response.toString());
		}
		
		return response != null ? response : null;
	}

	public Object startConnection(String ip, int port, String option) throws UnknownHostException, IOException, ClassNotFoundException {
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
		System.out.println(documentArr[0].toString());
		System.out.println(documentArr[1].toString());

		/* Create I/O Stream */
		toServer = new ObjectOutputStream(clientSocket.getOutputStream());
		fromServer = new ObjectInputStream(clientSocket.getInputStream());

		/* Send Document to Server */
		toServer.writeObject(documentArr);
		Object response = fromServer.readObject();
		System.out.println(response.toString());
		return response;
	}

	public void stopConnection() throws IOException {
		clientSocket.close();
		toServer.close();
		fromServer.close();
	}
	
	public JFrame getJFrame() {
		return this.frame;
	}
	
	public void setJFrame(JFrame frame) {
		this.frame = frame;
	}

	public Account getAccount() {
		return this.account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Socket getSocket() {
		return this.clientSocket;
	}

	public static void main(String[] args) {
		new Client();
	}

}
