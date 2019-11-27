package com.sheepy.catchme;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bson.Document;
import java.io.*;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private MongoClient mongo;
    public static HashMap<String, Socket> connectingClient = new HashMap<String, Socket>();
    public static int connectingClientAmount = 0;
    public static Lobby lobby;
    private static ObjectOutputStream toClient;
    private static ObjectInputStream fromClient;

    /* Start Server */
    public void start(int port) throws Exception {
    	/* Create Client List */
    	connectingClient.put("test", null);
    	
    	
        /* Create server */
        serverSocket = new ServerSocket(port);
        lobby = new Lobby();
        System.out.println("Server is running.");

        /* Connecting to MongoDB */
        System.out.println("Connecting to MongoDB .....");
    	mongo = new MongoClient("localhost", 27017);
    	MongoDatabase db = mongo.getDatabase("pg");
    	System.out.println(db);
    	MongoCollection<Document> collection = db.getCollection("pg");
    	System.out.println(collection);
        
        System.out.println("Waiting for client...");
        
        while (true) {
            try {
                /* Accept client's request */
                clientSocket = serverSocket.accept();
                System.out.println(clientSocket + " has connected.");
                showAllConnectingClients();

                /* Create a new Thread to handling client */
                System.out.println("Assigning a new Thread for " + clientSocket);
                Thread t = new ClientHandler(clientSocket, collection);
                t.start();
            }
            catch (Exception e) {
                System.out.println(e);
                System.out.println("Closing connection.");
                this.stop();
                System.out.println("Server has closed.");
                break;
            }
        }
    }

    public void stop() throws IOException {
        clientSocket.close();
        serverSocket.close();
        mongo.close();
    }
    
    /* Display HashMap of connecting clients (String, Socket) on Server's Terminal */
    public static void showAllConnectingClients() {
    	for (Map.Entry<String, Socket> entry: connectingClient.entrySet()) {
    		System.out.println(entry.getKey() + ": " + entry.getValue());
    	}
    }
    
    /* Send object to all connecting clients */
    public static void broadcastObject(Object obj) throws IOException, ClassNotFoundException {
    	for (Map.Entry<String, Socket> entry: connectingClient.entrySet()) {
    		toClient = new ObjectOutputStream(entry.getValue().getOutputStream());
    		fromClient = new ObjectInputStream(entry.getValue().getInputStream());
    		
    		// To Do
    		
//    		Object recieveObj = fromClient.readObject();
//    		toClient.writeObject(obj);
    	}
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start(5555);
    }
}