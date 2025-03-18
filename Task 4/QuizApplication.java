import java.util.*;
import java.util.concurrent.*;

public class QuizApplication {
    private static final List<Question> questions = new ArrayList<>();
    private static int score = 0;
    private static final List<Result> results = new ArrayList<>();

    public static void main(String[] args) {
        initializeQuestions();
        startQuiz();
        showResults();
    }

    private static void initializeQuestions() {
        questions.add(new Question(
            "What is the capital of Japan?",
            Arrays.asList("Tokyo", "Osaka", "Kyoto", "Hiroshima"),
            0,  
            15 
        ));

        questions.add(new Question(
            "What does CPU stand for?",
            Arrays.asList("Critical Power Unit","Central Processing Unit", "Computer Processing Unit", "Central Power Unit"),
            1,
            15
        ));

        questions.add(new Question(
            "Who co-founded Apple Inc.?",
            Arrays.asList("Bill Gates", "Steve Jobs", "Mark Zuckerberg", "Larry Page"),
            1,
            15
        ));

        questions.add(new Question(
            "What is the largest planet in our solar system?",
            Arrays.asList("Earth", "Saturn", "Jupiter", "Uranus"),
            2,
            15
        ));
    }

    private static void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println("=== Welcome to QuickQuiz! ===");
        System.out.println("Rules:");
        System.out.println("- Each question has a time limit");
        System.out.println("- Enter the number corresponding to your answer\n");

        for (int i = 0; i < questions.size(); i++) {
            Question current = questions.get(i);
            System.out.printf("\nQuestion %d/%d (Time: %ds)\n", 
                            i+1, questions.size(), current.getTimeLimit());
            displayQuestion(current);

            final int[] userAnswer = {-1};
            final boolean[] timeout = {false};

            try {
                Future<?> future = executor.submit(() -> {
                    try {
                        System.out.print("Your answer (1-4): ");
                        if (scanner.hasNextInt()) {
                            userAnswer[0] = scanner.nextInt() - 1;  
                        }
                    } catch (InputMismatchException e) {
                        scanner.next();  
                    }
                });

                future.get(current.getTimeLimit(), TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                timeout[0] = true;
                System.out.println("\nTime's up!");
            } catch (Exception e) {
                e.printStackTrace();
            }

            processAnswer(current, userAnswer[0], timeout[0]);
        }

        executor.shutdown();
        scanner.close();
    }

    private static void displayQuestion(Question q) {
        System.out.println("\n" + q.getText());
        List<String> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d. %s\n", i+1, options.get(i));
        }
    }

    private static void processAnswer(Question q, int userAnswer, boolean timeout) {
        String status = "Incorrect";
        boolean correct = false;

        if (!timeout && userAnswer >= 0 && userAnswer < q.getOptions().size()) {
            correct = (userAnswer == q.getCorrectAnswer());
            if (correct) {
                score++;
                status = "Correct";
            }
        } else if (timeout) {
            status = "Timed out";
        } else {
            status = "Invalid input";
        }

        results.add(new Result(
            q.getText(),
            q.getOptions().get(q.getCorrectAnswer()),
            (userAnswer != -1 && !timeout) ? q.getOptions().get(userAnswer) : "No answer",
            status
        ));
    }

    private static void showResults() {
        System.out.println("\n=== Quiz Results ===");
        System.out.printf("Final Score: %d out of %d\n", score, questions.size());
        
        System.out.println("\nDetailed Breakdown:");
        for (int i = 0; i < results.size(); i++) {
            Result r = results.get(i);
            System.out.printf("\nQ%d: %s", i+1, r.getQuestion());
            System.out.printf("\nCorrect Answer: %s", r.getCorrectAnswer());
            System.out.printf("\nYour Answer: %s", r.getUserAnswer());
            System.out.printf("\nStatus: %s\n", r.getStatus());
        }
    }

    static class Question {
        private final String text;
        private final List<String> options;
        private final int correctAnswer;
        private final int timeLimit;

        public Question(String text, List<String> options, 
                       int correctAnswer, int timeLimit) {
            this.text = text;
            this.options = options;
            this.correctAnswer = correctAnswer;
            this.timeLimit = timeLimit;
        }

        public String getText() { return text; }
        public List<String> getOptions() { return options; }
        public int getCorrectAnswer() { return correctAnswer; }
        public int getTimeLimit() { return timeLimit; }
    }

    static class Result {
        private final String question;
        private final String correctAnswer;
        private final String userAnswer;
        private final String status;

        public Result(String question, String correctAnswer, 
                     String userAnswer, String status) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.userAnswer = userAnswer;
            this.status = status;
        }

        public String getQuestion() { return question; }
        public String getCorrectAnswer() { return correctAnswer; }
        public String getUserAnswer() { return userAnswer; }
        public String getStatus() { return status; }
    }
}
