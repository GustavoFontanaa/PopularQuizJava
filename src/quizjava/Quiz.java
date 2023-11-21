package quizjava;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Quiz {

	private Map<String, String> wordsAndHints;
	private String palavra;

	public Quiz() {
		initializeWordsAndHints();
		chooseRandomWord();
	}

	public String getRandomHint() {
		Map.Entry<String, String> randomEntry = pickRandomEntry(wordsAndHints);
		palavra = randomEntry.getKey();
		return "Dica: " + randomEntry.getValue();
	}

	public String getPalavra() {
		return palavra;
	}

	private void initializeWordsAndHints() {
		wordsAndHints = new HashMap<>();
		wordsAndHints.put("artorias", "(Série souls) Exímio espadachim e que foi um dos poucos que ousaram enfrentar a imponente ameaça do Abismo!");
		wordsAndHints.put("200", "Qual é o dano da spas?");

		// colocar mais perguntas e respostas
	}

	private static <K, V> Map.Entry<K, V> pickRandomEntry(Map<K, V> map) {
	    Random random = new Random();
	    int randomIndex = random.nextInt(map.size());

	    return map.entrySet().stream().skip(randomIndex).findFirst().orElseThrow();
	}


	void chooseRandomWord() {
		Map.Entry<String, String> randomEntry = pickRandomEntry(wordsAndHints);
		palavra = randomEntry.getKey();
	}
}
