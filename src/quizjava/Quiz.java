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
		return "Pergunta: " + randomEntry.getValue();
	}

	public String getPalavra() {
		return palavra;
	}

	private void initializeWordsAndHints() {
		wordsAndHints = new HashMap<>();
		wordsAndHints.put("artorias", "(Série souls) Exímio espadachim e que foi um dos poucos que ousaram enfrentar a imponente ameaça do Abismo!");
	    wordsAndHints.put("200", "Qual é o dano da spas?");
	    wordsAndHints.put("gandalf", "(Senhor dos Anéis) Poderoso mago que liderou a Sociedade do Anel na jornada para destruir o Um Anel.");
	    wordsAndHints.put("mario", "(Jogo clássico) Encanador da Nintendo conhecido por resgatar a Princesa Peach do vilão Bowser.");
	    wordsAndHints.put("python", "(Linguagem de programação) Conhecida por sua sintaxe limpa e legibilidade, é muito utilizada em diversos campos.");
	    wordsAndHints.put("pikachu", "(Franquia de jogos) Adorável personagem amarelo que é um Pokémon do tipo elétrico.");
	    wordsAndHints.put("monalisa", "(Arte famosa) Pintura renascentista que retrata uma mulher com um sorriso enigmático.");
	    wordsAndHints.put("einstein", "(Física) Famoso cientista conhecido pela Teoria da Relatividade e pela equação E=mc².");
	    wordsAndHints.put("leonardo", "(Renascimento) Gênio italiano conhecido por suas contribuições à arte, ciência e invenções.");
	    wordsAndHints.put("javascript", "(Linguagem de programação) Utilizada principalmente para desenvolvimento web, permite interações dinâmicas em páginas da internet.");
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
