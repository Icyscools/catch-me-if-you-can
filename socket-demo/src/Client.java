import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private ObjectInputStream fromServer; // Reply from Server
    private ObjectOutputStream toServer; // Message to Server
    private Scanner sc;
    private String data = "";
    private String reply;

    public void startConnection(String ip, int port) throws Exception {
        System.out.println("Connecting to " + ip + " on Port " + port);
        clientSocket = new Socket(ip, port);
        System.out.println("Connected");
        sc = new Scanner(System.in);

        while (!data.equals("terminate")) {
            /* Input from client's keyboard */
            System.out.print("Say something to server (\"terminate\" to stop connection.): ");
            data = sc.nextLine();
            Message message = new Message(data, 555);
            
            /* Create I/O Stream */
            toServer = new ObjectOutputStream(clientSocket.getOutputStream());
            fromServer = new ObjectInputStream(clientSocket.getInputStream());

            /* Message to Server */
            toServer.writeObject(message);

            /* Reply from Server */
            reply = (String) fromServer.readObject();
            System.out.println(reply);

        }
        this.stopConnection();
    }

    public void stopConnection() throws Exception {
        clientSocket.close();
        sc.close();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.startConnection("10.110.194.176", 5555);
    }
}