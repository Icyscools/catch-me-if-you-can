package com.sheepy.catchme;

import java.net.*;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

//import javax.xml.bind.DatatypeConverter;
import org.bson.Document;
import java.io.*;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

public class ClientHandler extends Thread {
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private Socket clientSocket;
	private MongoCollection<Document> collection;
	public static int threadID = 0;

	public ClientHandler(Socket clientSocket, MongoCollection<Document> collection) {
		this.clientSocket = clientSocket;
		this.collection = collection;
		Server.connectingClientAmount++;
		threadID++;
		System.out.println(Server.connectingClientAmount + " client(s) is/are connecting");
	}

	public int getConnectingClient() {
		return Server.connectingClientAmount;
	}

	public void updateWaitingRoom(List<WaitingRoom> rooms) {
		try {
			toClient = new ObjectOutputStream(clientSocket.getOutputStream());
			fromClient = new ObjectInputStream(clientSocket.getInputStream());


			Object[] document = new Object[2];
			document[0] = "update-waiting-room";
			document[1] = rooms;
			toClient.writeObject("update-waiting-room");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				/* Create I/O Stream */
				toClient = new ObjectOutputStream(clientSocket.getOutputStream());
				fromClient = new ObjectInputStream(clientSocket.getInputStream());

				/* Read message from Client */
				Object[] document = (Object[]) fromClient.readObject();
				Document userInfo = (Document) document[1];
				String username = userInfo.getString("username");
				String password = userInfo.getString("password");

				/* Hashing user's password using MD5 Algorithm */
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				byte[] digest = md.digest();
				// password = DatatypeConverter.printHexBinary(digest).toLowerCase();
				userInfo.put("password", password); // Update user's password in Document

				if (document[0].equals("regis")) {
					if (collection.find(eq("username", username)).first() == null) {
						/* Write user's information to Database */
						collection.insertOne((Document) document[1]);

						/* Reply to user */
						toClient.writeObject("Registration sucess.");
					}
					else {
						/* Reply to user */
						toClient.writeObject("Registration failed: Username already existed.");
					}
					break;
				}
				else if (document[0].equals("login")) {
					if (collection.find(and(
							eq("username", username),
							eq("password", password))).first() != null) {
						toClient.writeObject(new Account(username));
					}
					else {
						toClient.writeObject("Username or Password are incorrect.");
						break;
					}
				}
				else if (document[0].equals("join")) {
//					Server.lobby.addConnectionPlayer(this, (WaitingRoom) document[1]);
//					for (Map.Entry<ClientHandler, WaitingRoom> map : Server.lobby.getConnectionPlayer().entrySet()) {
//						map.getKey().updateWaitingRoom((List<WaitingRoom>) Server.lobby.getConnectionPlayer().values());
//					}
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		}
		System.out.println("Closing connection. Thread #" + threadID + " has closed.");
		Server.connectingClientAmount--;
		threadID--;
		System.out.println(Server.connectingClientAmount + " client(s) is/are connecting now.");
	}
}