import java.io.IOException;
import java.util.Scanner;

import controller.ClientConnection;

public class Main {
    static ClientConnection connection;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {

        System.out.print("Bem vindo ao nosso chat!\n" +
                "Digite seu nome: ");

        String clientName = scanner.nextLine();

        try {
            connection = new ClientConnection("localhost", 6060, clientName);

            while (true) {
                String message = scanner.nextLine();
                connection.speak(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
