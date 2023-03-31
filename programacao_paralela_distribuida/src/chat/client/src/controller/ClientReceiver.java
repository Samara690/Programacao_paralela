package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class ClientReceiver extends Thread {

    private Socket connection;
    private ObjectInputStream input;

    public ClientReceiver(Socket connection) throws UnknownHostException, IOException {
        this.connection = connection;
    }

    public void run() {
        try {
            input = new ObjectInputStream(connection.getInputStream());
            String message;
            while (true) {
                message = input.readObject().toString();

                if (Objects.isNull(message)) {
                    System.out.println("Conexão Encerrada!");
                    break;
                }

                System.out.println();
                System.out.println(message);
                System.out.print("...> ");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
