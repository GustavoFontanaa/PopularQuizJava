package quizjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Server {

    private static final AtomicInteger attempts = new AtomicInteger(5);

    public static void start(int port, Quiz quiz) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Aguardando conexões...");
            Socket clientSocket = serverSocket.accept();
            initializeClientConnection(clientSocket, quiz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeClientConnection(Socket clientSocket, Quiz quiz) {
        try {
            System.out.println("Conectado ao servidor.");
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress() + "\n");
            System.out.println("Para iniciar digite: 'start'");

            new Thread(() -> handleClient(clientSocket, quiz)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, Quiz quiz) {
        try (
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            while (true) {
                String clientChoice = input.readLine();

                if (clientChoice == null) {
                    break;
                }

                if (clientChoice.equalsIgnoreCase("n")) {
                    handleClientExit();
                    break;
                }

                if ("start".equalsIgnoreCase(clientChoice) || "s".equalsIgnoreCase(clientChoice)) {
                    handleStartCommand(quiz, output);
                } else {
                    processClientChoice(clientChoice, quiz, output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleStartCommand(Quiz quiz, PrintWriter output) {
        System.out.println();
        System.out.println();
        attempts.set(5);
        output.println(getChoice(quiz));
    }

    private static void processClientChoice(String clientChoice, Quiz quiz, PrintWriter output) {
        if (quiz.checkAnswer(clientChoice)) {
            attempts.set(5);
            output.println("Resultado: correta");
        } else {
            handleIncorrectChoice(clientChoice, quiz, output);
        }
    }


    private static void handleIncorrectChoice(String clientChoice, Quiz quiz, PrintWriter output) {
        if (attempts.intValue() == 1) {
            output.println("Resultado: Tentativas Esgotadas");
        } else {
            String equalsChar = findCommonCharacters(quiz.getCurrentAnswer().trim(), clientChoice.trim());
            output.println("Resultado: " + formatString(equalsChar, " - ") + " Tentativas: " + attempts.decrementAndGet());
        }
    }

    private static String findCommonCharacters(String str1, String str2) {
        Set<Character> commonChars = new HashSet<>();

        if (str1.isEmpty() || str2.isEmpty()) {
            return "";
        }

        str1.chars().mapToObj(c -> (char) c)
                .filter(c -> str2.indexOf(c) != -1)
                .forEach(commonChars::add);

        return commonChars.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    private static String formatString(String input, String delimiter) {
        StringBuilder formatted = new StringBuilder();

        if (input.isEmpty()) {
            return formatted.toString();
        }

        input.chars().mapToObj(c -> (char) c)
                .map(String::valueOf)
                .reduce((s1, s2) -> s1 + delimiter + s2)
                .ifPresent(formatted::append);

        return formatted.toString();
    }

    private static String getChoice(Quiz quiz) {
        return quiz.getRandomHint();
    }

    private static void handleClientExit() {
        System.out.println("Cliente escolheu sair. Encerrando o jogo.");
        System.exit(1);
    }
}
