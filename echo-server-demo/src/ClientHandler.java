import java.net.*;
import java.io.*;

public class ClientHandler extends Thread {
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private Socket clientSocket;
    private static int connectingClient = 0;
    private Message message = new Message("", 0);

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        connectingClient++;
    }

    public int getConnectedClient() {
        return connectingClient;
    }

    @Override
    public void run() {
        while (true) {
            try {
                /* Create I/O Stream */
                toClient = new ObjectOutputStream(clientSocket.getOutputStream());
                fromClient = new ObjectInputStream(clientSocket.getInputStream());

                /* Read message from Client */
                message = (Message) fromClient.readObject();
                System.out.println(clientSocket + " sent you " + message);

                /* Reply to Client */
                toClient.writeObject("This is server. I have recieved your message: \"" + message + "\"");
            }
            catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Closing connection.");
                break;
            }
        }
    }

}