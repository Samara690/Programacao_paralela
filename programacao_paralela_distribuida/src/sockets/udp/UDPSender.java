package sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPSender {

  private int port;
  private String destinName;
  DatagramSocket sender;
  Scanner scan = new Scanner(System.in);

  public UDPSender(int port, String destinName) throws IOException {
    this.port = port;
    this.destinName = destinName;
    this.sender = new DatagramSocket();
  }

  public void run() throws IOException, ClassNotFoundException {
    InetAddress addr = InetAddress.getByName(destinName);
    System.out.println(String.format("Servidor na porta %d", this.port));
    String msg;

    System.out.print("..: ");
    msg = scan.nextLine();

    DatagramPacket pkg = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addr, port);
    this.sender.send(pkg);

    System.out.println("Enviando mensagem para: " + addr.getHostAddress() + "\n" +
        "port: " + port + "\n" + "message: " + msg);

    System.out.println("Conexão finalizada.");
    this.sender.close();
  }

  public static void main (String[] args) throws ClassNotFoundException{
    int port = 1100;
    try {
        UDPSender sender = new UDPSender(port, "localhost");
        sender.run();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}