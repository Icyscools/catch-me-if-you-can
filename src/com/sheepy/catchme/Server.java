package com.sheepy.catchme;

import java.net.*;
import java.util.HashMap;
import org.bson.Document;
import java.io.*;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private MongoClient mongo;
    public static int connectingClient = 0;
    public static HashMap<String, WaitingRoom> lobbyList;
    public static HashMap<String, Game> gameList;

    /* Start Server */
    public void start(int port) throws Exception {
        /* Create server */
        serverSocket = new ServerSocket(port);
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
    
    public void createRoom(String roomID) {
    	this.lobbyList.put(roomID, new WaitingRoom());
    }
    
    public void createGame(String roomID) {
    	WaitingRoom room = this.lobbyList.get(roomID);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start(5555);
    }
}