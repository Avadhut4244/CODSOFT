package CODSOFT.GuesssingGame;
import java.util.Random;
import java.util.Scanner;

public class GuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int totalRounds = 0;
        int roundsWon = 0;

        while (playAgain) {
            System.out.println("Welcome to the Guessing Game!");
            System.out.println("You have 5 attempts to guess a number between 1 and 100.");

            int numberToGuess = random.nextInt(100) + 1; 
            int attempts = 0;
            boolean guessed = false;

            while (attempts < 5 && !guessed) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("Try Again" + ( 6 - attempts) + "  attempts left.");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Try Again" + (6 - attempts) + "  attempts left.");
                } else {
                    System.out.println("Congratulations! You have taken" + attempts + " attempts.");
                    guessed = true;
                    roundsWon++;
                }
            }

            if (!guessed) {
                System.out.println("you didn't guess the number. It was " + numberToGuess + ".");
            }

            totalRounds++;
            System.out.println("Your  score: " + roundsWon + " rounds won out of " + totalRounds + " rounds played.");
            System.out.print("Do you want to play again?");
            String input = scanner.next().toLowerCase();

            while (!input.equals("yes") && !input.equals("no")) {
                System.out.print("Invalid input");
                input = scanner.next().toLowerCase();
            }

            playAgain = input.equals("yes");
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
