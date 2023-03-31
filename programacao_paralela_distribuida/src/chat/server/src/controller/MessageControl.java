package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import messages.Commands;
import model.Client;

public class MessageControl {

    public void sendMessage(List<Client> clients, Client sender, ObjectOutputStream output, String text) throws IOException {
        if (text.toLowerCase().startsWith(Commands.HELP.getCommand())) {
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.asList(Commands.values()).forEach(cmd -> stringBuilder.append(cmd.getSample() + " \n"));

            sender.getOutput().writeObject(stringBuilder.toString());

        } else if (text.toLowerCase().startsWith(Commands.WHISPER.getCommand())) {
            String[] texts = text.split(" ", 3);
            String recipient = texts[1];
            text = String.join(",", texts);

            this.sendMessageToSpecificClient(clients, sender, output, texts[2], recipient);

        } else {
            this.sendMessageToAllClients(clients, output, String.format("%s said: %s", sender.getUserId(), text));
        }
    }

    public void sendMessageToAllClients(List<Client> clients, ObjectOutputStream output, String text) throws IOException {
        for (Client client : clients) {
            client.getOutput().writeObject(text);
        }
    }

    public void sendMessageToSpecificClient(List<Client> clients, Client sender, ObjectOutputStream output, String text, String recipient)
            throws IOException {
        for (Client client : clients) {
            if (client.getUserId().equals(recipient)) {
                client.getOutput().writeObject(String.format("%s whispered: %s", sender.getUserId(), text));
                sender.getOutput().writeObject(String.format("%s whispered: %s", sender.getUserId(), text));
            }
        }
    }
}
