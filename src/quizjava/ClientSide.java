package quizjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSide {
    private static final String DELIMITER = ":";

    public static void start(String host, int port) {
        try (Socket socket = new Socket(host, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String response;
            String userChoice;
            boolean exit = false;

            do {
                userChoice = userInput.readLine();
                sendUserChoice(output, userChoice);

                response = input.readLine();
                handleResponse(response);

            } while (!exit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendUserChoice(PrintWriter output, String userChoice) {
        output.println(userChoice);
    }

    private static void handleResponse(String response) {
        String responseType = findBeforeFirstColon(response);
        String responseData = findAfterFirstSemicolon(response).trim();

        switch (responseType) {
            case "Dica":
                System.out.println(response);
                System.out.println("Digite a palavra abaixo:");
                break;
            case "Resultado":
                if (responseData.equals("correta") || responseData.equals("Tentativas Esgotadas")) {
                    System.out.println(responseData + "!");
                    System.out.println("Jogar novamente? (s/n)");
                } else {
                    System.out.println("Contem: " + responseData);
                }
                break;
            default:
        }
    }

    private static String findBeforeFirstColon(String text) {
        int indexOfColon = text.indexOf(DELIMITER);
        return (indexOfColon != -1) ? text.substring(0, indexOfColon) : text;
    }

    private static String findAfterFirstSemicolon(String text) {
        int index = text.indexOf(DELIMITER);
        return (index != -1) ? text.substring(index + 1).trim() : "";
    }
}
