package controller;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Objects;

import messages.Commands;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import model.Client;

public class Server extends Thread {

    private static LinkedList<Client> clients;
    private Socket connection;

    public Server(Socket connection) throws IOException {
        this.connection = connection;
    }

    public void run() {
        MessageControl messageControl = new MessageControl();
        try {
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());

            System.out.println("Aguardando o id do cliente...");
            String clientId = input.readObject().toString();
            Client client = addClient(clientId, output);

            messageControl.sendMessageToAllClients(clients, output, String.format("%s entered.", client.getUserId()));

            if (Objects.isNull(client)) {
                output.writeObject("Esse id já existe!");
            }

            String message = input.readObject().toString();
            while (!Objects.isNull(message) && !message.trim().equals(Commands.EXIT.getCommand())) {
                messageControl.sendMessage(clients, client, output, message);
                message = input.readObject().toString();
            }

            messageControl.sendMessageToAllClients(clients, output, String.format("%s left.", client.getUserId()));
            clients.remove(client);
            connection.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private Client addClient(String clientId, ObjectOutputStream output) {
        
        if (Objects.isNull(clients)){
            clients = new LinkedList<>();
        }

        for (Client client : clients) {
            if (client.getUserId().equals(clientId)) {
                return null;
            }
        }

        Client client = new Client(clientId, output, this);
        clients.add(client);

        return client;
    }

    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(6060, 10);
            System.out.println("Servidor sendo executado: " + server.getLocalSocketAddress());

            while (true) {

                System.out.println("Esperando algum cliente...");
                Socket connection = server.accept();
                System.out.println("Conexão estabelecida com: " + connection.getInetAddress().getHostAddress());

                Thread serverThread = new Server(connection);
                serverThread.start();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
