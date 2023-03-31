package sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class UDPReceiver {

    DatagramSocket receiver = new DatagramSocket();
    Scanner scan = new Scanner(System.in);

    public UDPReceiver(int port) throws UnknownHostException, IOException{
        this.receiver = new DatagramSocket(port);
    }

    public void run() throws IOException, ClassNotFoundException {
        byte[] msg = new byte[256];
        
        DatagramPacket pkg = new DatagramPacket(msg, msg.length);

        this.receiver.receive(pkg);

        System.out.println("Received message: " + new String(pkg.getData()));
    
        System.out.println("Connection terminated.");
        this.receiver.close();
      }
    

    public static void main (String[] args) throws ClassNotFoundException{
        int port = 1100;
        try {
            UDPReceiver receiver = new UDPReceiver(port);
            receiver.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }