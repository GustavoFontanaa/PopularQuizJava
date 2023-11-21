package quizjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Server {

	static private AtomicInteger attempts = new AtomicInteger(5);

	BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

	public static void start(Integer port, Quiz quiz) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Aguardando conexões...");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Conectado ao servidor.");
			System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress() + "\n");
			System.out.println("Para iniciar digite: 'start'");

			new Thread(() -> handleClient(clientSocket, quiz)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket clientSocket, Quiz quiz) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

			while (true) {
				String clientChoice = input.readLine();

				if (clientChoice == null) {
					break;
				}

				if (clientChoice.equals("n")) {
					System.out.println("Cliente escolheu sair. Encerrando o jogo.");
					System.exit(1);
					break;
				}

				if ("start".equals(clientChoice) || "s".equals(clientChoice)) {
					System.out.println();
					System.out.println();
					attempts.set(5);
					output.println(getRandomChoice(quiz));
					continue;
				}

				processClientChoice(clientChoice, quiz, output);
			}

			input.close();
			output.close();
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void processClientChoice(String clientChoice, Quiz quiz, PrintWriter output) {
		if (removeSpecialCaracters(quiz.getPalavra()).equals(removeSpecialCaracters(clientChoice))) {
			attempts.set(5);
			output.println("Resultado: correta");
		} else {
			handleIncorrectChoice(clientChoice, quiz, output);
		}
	}

	private static void handleIncorrectChoice(String clientChoice, Quiz quiz, PrintWriter output) {
		if (attempts.intValue() == 1) {
			output.println("Resultado: Tentativas Esgotadas");
			// arrumar bug de responder s ou n e ter que apertar enter 2x para poder funcionar
		}

		String equalsChar = findCommonCharacters(quiz.getPalavra().trim(), clientChoice.trim());
		output.println("Resultado: " + formatString(equalsChar, " - ") + ", Tentativas: " + attempts.decrementAndGet());
	}

	private static String findCommonCharacters(String str1, String str2) {
		Set<Character> commonChars = new HashSet<>();

		if (str1.isEmpty() || str2.isEmpty()) {
			return "";
		}

		for (char c : str1.toCharArray()) {
			if (str2.indexOf(c) != -1) {
				commonChars.add(c);
			}
		}

		return commonChars.stream().map(Object::toString).collect(Collectors.joining(""));
	}

	private static String formatString(String input, String delimiter) {
		StringBuilder formatted = new StringBuilder();

		if (input.isEmpty()) {
			return formatted.toString();
		}

		formatted.append(input.charAt(0));

		for (int i = 1; i < input.length(); i++) {
			formatted.append(delimiter).append(input.charAt(i));
		}

		return formatted.toString();
	}

	private static String getRandomChoice(Quiz quiz) {
		return quiz.getRandomHint();
	}

	private static String removeSpecialCaracters(String text) {
		if (text == null) {
			return "";
		}

		text = text.replaceAll("\\s", "");

		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
	}

}