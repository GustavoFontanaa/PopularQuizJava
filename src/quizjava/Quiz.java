package quizjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quiz {

    private List<Question> questions;
    private Question currentQuestion;

    public Quiz() {
        initializeQuestions();
        chooseRandomQuestion();
    }

    public String getCurrentQuestionHint() {
        return "Pergunta: " + currentQuestion.getHint();
    }

    public String getCurrentAnswer() {
        return currentQuestion.getAnswer();
    }

    public boolean checkAnswer(String userAnswer) {
        return currentQuestion.checkAnswer(userAnswer);
    }

    public String getRandomHint() {
        chooseRandomQuestion();
        return getCurrentQuestionHint();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Poderoso mago que liderou a Sociedade do Anel na jornada para destruir o Um Anel.", "Gandalf"));
        questions.add(new Question("Encanador da Nintendo conhecido por resgatar a Princesa Peach do vilão Bowser.", "Mario"));
        questions.add(new Question("Conhecida por sua sintaxe limpa e legibilidade, é muito utilizada em diversos campos.", "Python"));
        questions.add(new Question("Adorável personagem amarelo que é um Pokémon do tipo elétrico.", "Pikachu"));
        questions.add(new Question("Pintura renascentista que retrata uma mulher com um sorriso enigmático.", "Monalisa"));
        questions.add(new Question("Famoso cientista conhecido pela Teoria da Relatividade e pela equação E=mc².", "Einstein"));
        questions.add(new Question("Gênio italiano conhecido por suas contribuições à arte, ciência e invenções.", "Leonardo"));
        questions.add(new Question("Utilizada principalmente para desenvolvimento web, permite interações dinâmicas em páginas da internet.", "JavaScript"));
    }

    private void chooseRandomQuestion() {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        currentQuestion = questions.get(randomIndex);
    }

    private static class Question {
        private String hint;
        private String answer;

        public Question(String hint, String answer) {
            this.hint = hint;
            this.answer = answer;
        }

        public String getHint() {
            return hint;
        }

        public String getAnswer() {
            return answer;
        }

        public boolean checkAnswer(String userAnswer) {
            return answer.equalsIgnoreCase(userAnswer.trim());
        }
    }
}
