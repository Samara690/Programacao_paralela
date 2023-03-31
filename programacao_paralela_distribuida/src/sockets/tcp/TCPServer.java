package sockets.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

  private ServerSocket server;
  private Socket connection;
  private ObjectOutputStream output;
  private ObjectInputStream input;
  private int port;

  public TCPServer(int port) throws IOException {
    this.port = port;
    this.server = new ServerSocket(port, 10);
  }

  public void run() throws IOException, ClassNotFoundException {
    String msg = "Hello";
    System.out.println(String.format("Server listening to the port %d", this.port));

    while (true) {
      Socket connection = server.accept();
      System.out.println("Conected client: " + connection.getInetAddress().getHostAddress());
      output = new ObjectOutputStream(connection.getOutputStream());
      input = new ObjectInputStream(connection.getInputStream());

      output.writeObject("Connection successfully established...");

      do {
        try {
          msg = (String) input.readObject();
          System.out.println("Client>> " + msg);
          output.writeObject(msg);
        } catch (IOException iOException) {
          System.err.println("error: " + iOException.toString());
        }
      } while (!msg.equals("stop"));

      System.out.println("Connection terminated by the client.");
      this.close();
      break;
    }
  }

  public void close() throws IOException {
    output.close();
    input.close();
    connection.close();
  }

  public static void main (String[] args) throws ClassNotFoundException{
    int port = 1100;
    try {
        TCPServer server = new TCPServer(port);
        server.run();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}