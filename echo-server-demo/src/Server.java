import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectInputStream fromClient; // Message from Client
    private ObjectOutputStream toClient; // Reply to Client

    /* Start Server */
    public void start(int port) throws Exception {        
        /* Create server */
        System.out.println("Server is running.");
        serverSocket = new ServerSocket(port);

        System.out.println("Waiting for client...");
        
        while (true) {
            try {
                /* Accept client's request */
                clientSocket = serverSocket.accept();
                System.out.println(clientSocket + " has connected.");

                /* Create a new Thread to handling client */
                System.out.println("Assigning a new Thread for " + clientSocket);
                Thread t = new ClientHandler(clientSocket);
                t.start();
            }
            catch (Exception e) {
                System.out.println(e);
                System.out.println("Closing connection.");
                this.stop();
                break;
            }
        }
    }

    public void stop() throws Exception {
        fromClient.close();
        toClient.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start(5555);
    }
}