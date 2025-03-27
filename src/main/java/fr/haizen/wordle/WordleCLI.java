package fr.haizen.wordle;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import fr.haizen.wordle.objects.GuessResult;
import fr.haizen.wordle.objects.LetterFeedback;
import fr.haizen.wordle.utils.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created at 27/03/2025 - HaiZen
 * The main class of the game (CLI version, for play)
 */
public class WordleCLI {

    /**
     * Instance of the game logic
     */
    private final GameLogic gameLogic;

    /**
     * Scanner to read user input
     */
    private final Scanner scanner;

    /**
     * List of guesses (for display)
     */
    private final List<String> guesses;

    /**
     * Constructor
     *
     * @param targetWord the word to guess
     */
    public WordleCLI(String targetWord) {
        this.gameLogic = new GameLogic(targetWord);
        this.scanner = new Scanner(System.in);
        this.guesses = new ArrayList<>();
    }

    /**
     * Start the game
     */
    public void start() {
        /*
         * Print the default game information
         */
        clearScreen();
        printLogo();
        printInstructions();

        /*
         * Loop until the game is over
         */
        while (!gameLogic.isGameOver()) {
            /*
             * Print the game state (guesses, remaining attempts)
             */
            printGameState();

            /*
             * Get the guess from the user
             */
            String guess = getGuess();
            try {
                /*
                 * Check the guess (valid world, the word is correct..)
                 */
                GuessResult result = gameLogic.checkGuess(guess);
                guesses.add(guess);

                /*
                 * If the word is correct, print the game state and the win message
                 */
                if (result.isCorrect()) {
                    printGameState();
                    printWinMessage();
                    break;
                }
            } catch (IllegalArgumentException e) {
                /*
                 * If the word is not valid, print an error message (and continue the loop)
                 */
                printError();
                continue;
            }

            /*
             * Decrement the remaining attempts
             */
            gameLogic.decrementAttempts();
        }
    }

    /**
     * Clear the screen (for a better display)
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Print the logo
     */
    private void printLogo() {
        for (String line : WordUtils.WORDLE_LOGO) {
            System.out.println(Ansi.colorize(line, Attribute.BLUE_TEXT()));
        }
        System.out.println();
    }

    /**
     * Print the instructions
     */
    private void printInstructions() {
        System.out.println(Ansi.colorize("Welcome to Wordle!", Attribute.YELLOW_TEXT()));
        System.out.println(Ansi.colorize("Try to guess the 5-letter word in 6 attempts.", Attribute.WHITE_TEXT()));
        System.out.println(Ansi.colorize("After each guess, the color of the tiles will change to show how close your guess was to the word.", Attribute.WHITE_TEXT()));
        System.out.println();
    }

    /**
     * Print the game state (previous guesses, remaining attempts)
     */
    private void printGameState() {
        /*
         * Clear the screen and print the logo
         */
        clearScreen();
        printLogo();

        /*
         * Print the previous guesses and their feedback (colors for each letter)
         */
        for (String guess : guesses) {
            GuessResult result = gameLogic.checkGuess(guess);
            printGuessResult(guess, result.getFeedback());
        }

        /*
         * Print empty rows for the remaining attempts
         */
        for (int i = guesses.size(); i < 6; i++) {
            printEmptyRow();
        }

        /*
         * Print the remaining attempts
         */
        System.out.println();
        System.out.println(Ansi.colorize("Remaining attempts: " + gameLogic.getRemainingAttempts(), Attribute.YELLOW_TEXT()));
    }

    /**
     * Print the guess and its feedback
     *
     * @param guess    the guess
     * @param feedback the feedback
     */
    private void printGuessResult(String guess, LetterFeedback[] feedback) {
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            Attribute color = feedback[i].getColor();
            System.out.print(Ansi.colorize("[" + guess.charAt(i) + "]", color));
        }
        System.out.println();
    }

    /**
     * Print an empty row
     */
    private void printEmptyRow() {
        System.out.print("  ");
        for (int i = 0; i < 5; i++) {
            System.out.print(Ansi.colorize("[ ]", Attribute.WHITE_TEXT()));
        }
        System.out.println();
    }

    /**
     * Get the guess from the user (with scanner)
     *
     * @return the guess
     */
    private String getGuess() {
        System.out.print(Ansi.colorize("Enter your guess: ", Attribute.CYAN_TEXT()));
        return scanner.nextLine().toUpperCase();
    }

    /**
     * Print an error message
     */
    private void printError() {
        System.out.println(Ansi.colorize("Invalid word! Please enter a 5-letter word.", Attribute.RED_TEXT()));

        /*
         * Wait 2 seconds (for a better display, the user can see the error message)
         */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Print the win message
     */
    private void printWinMessage() {
        System.out.println(Ansi.colorize("\nCongratulations! You won!", Attribute.GREEN_TEXT()));
        System.out.println(Ansi.colorize("You found the word in " + guesses.size() + " attempts!", Attribute.YELLOW_TEXT()));
    }

    /**
     * Main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        /*
         * The word to guess (for testing, we'll use a fixed word "HELLO")
         */
        String word = "HELLO";

        /*
         * Create a new instance of the game and start it
         */
        WordleCLI game = new WordleCLI(word);
        game.start();
    }
} 