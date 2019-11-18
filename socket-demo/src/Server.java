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
        clientSocket = serverSocket.accept();
        System.out.println(clientSocket + " has connected.");

        while (true) {
            try {
                /* Create I/O Stream */
                toClient = new ObjectOutputStream(clientSocket.getOutputStream());
                fromClient = new ObjectInputStream(clientSocket.getInputStream());

                /* Read message from Client */
                Message message = (Message) fromClient.readObject();
                System.out.println(clientSocket + " sent you " + message);

                /* Reply to Client */
                toClient.writeObject("This is server. I have recieved your message: \"" + message + "\"");
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