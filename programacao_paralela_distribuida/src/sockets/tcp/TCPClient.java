package sockets.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class TCPClient {

    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    Scanner scan = new Scanner(System.in);

    public TCPClient(int port) throws UnknownHostException, IOException{
        client = new Socket("localhost", port);
    }

    public void run() throws IOException, ClassNotFoundException{
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());

        String msg = (String) input.readObject();
        System.out.println("Server>> "+msg);

        do {
            System.out.print("..: ");
            msg = scan.nextLine();
            output.writeObject(msg);
            output.flush();
            
            msg = (String) input.readObject();
            System.out.println("Servidor>> " + msg);
        } while (!msg.equals("stop"));
    }

    public void close() throws IOException{
        output.close();
        input.close();
        client.close();
        scan.close();
    }

    public static void main (String[] args) throws ClassNotFoundException{
        int port = 1100;
        try {
            TCPClient client = new TCPClient(port);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }