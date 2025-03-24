package fr.haizen.wordle;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import fr.haizen.wordle.objects.GuessResult;
import fr.haizen.wordle.objects.LetterFeedback;
import fr.haizen.wordle.utils.WordUtils;
import lombok.Getter;

/**
 * Created at 24/03/2025 - HaiZen
 * This class defines all the logic of the game
 */
@Getter
public class GameLogic {

    /**
     * The word to guess
     */
    private final String targetWord;

    /**
     * The remainign attempts (default : 6)
     */
    private int remainingAttempts;

    /**
     * Constructor
     *
     * @param targetWord the word to guess
     */
    public GameLogic(String targetWord) {
        /*
         * Check if the word is valid (full letter...)
         */
        if (!WordUtils.isValidWord(targetWord)) {
            /*
             * Throw an exception if the word is not valid
             */
            throw new IllegalArgumentException("Invalid target word");
        }

        this.targetWord = targetWord.toLowerCase();
        this.remainingAttempts = WordUtils.MAX_ATTEMPTS;
    }

    /**
     * Get the target word
     *
     * @return the target word
     */
    public String getTargetWord() {
        return targetWord.toUpperCase();
    }

    /**
     * Check the guess and return the result
     *
     * @param guess the guess
     * @return the result
     */
    public GuessResult checkGuess(String guess) {
        /*
         * Check if the word is valid (full letter...)
         */
        if (!WordUtils.isValidWord(guess)) {
            throw new IllegalArgumentException("Invalid guess");
        }

        /*
         * Check if the guess is correct
         */
        guess = guess.toLowerCase();
        if (guess.equals(targetWord)) {
            remainingAttempts = 0;

            /*
             * Return [GameResult] object with a true value and the feedback (game won)
             */
            return new GuessResult(true, this.generateFeedback(guess));
        }

        /*
         * Return [GameResult] object with a false value and the feedback (wrong guess)
         */
        return new GuessResult(false, this.generateFeedback(guess));
    }

    /**
     * Generate the feedback for the guess (correct, wrong position, incorrect for each letter)
     *
     * @param guess the guess
     * @return the feedback
     */
    private LetterFeedback[] generateFeedback(String guess) {
        LetterFeedback[] feedback = new LetterFeedback[5];
        boolean[] usedTarget = new boolean[5];
        boolean[] usedGuess = new boolean[5];

        /*
         * Check if the letter is at the correct position
         * If it is, mark it as correct (green)
         */
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == targetWord.charAt(i)) {
                feedback[i] = LetterFeedback.CORRECT;
                usedTarget[i] = true;
                usedGuess[i] = true;
            }
        }

        /*
         * Check if the letter is in the word but not at the correct position
         * If it is, mark it as wrong position (yellow)
         */
        for (int i = 0; i < 5; i++) {
            if (!usedGuess[i]) {
                for (int j = 0; j < 5; j++) {
                    if (!usedTarget[j] && guess.charAt(i) == targetWord.charAt(j)) {
                        feedback[i] = LetterFeedback.WRONG_POSITION;
                        usedTarget[j] = true;
                        usedGuess[i] = true;
                        break;
                    }
                }
            }
        }

        /*
         * Check if the letter is not in the word
         * If it is, mark it as incorrect (gray)
         */
        for (int i = 0; i < 5; i++) {
            if (!usedGuess[i]) feedback[i] = LetterFeedback.INCORRECT;
        }

        return feedback;
    }

    /**
     * Check if the player has lose the game (no remaining attempts)
     * If it is the case, print the game over messages
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        boolean isGameOver = remainingAttempts <= 0;
        if (isGameOver) {
            System.out.println(Ansi.colorize("\nGame Over!", Attribute.RED_TEXT()));
            System.out.println(Ansi.colorize("The word was: " + this.getTargetWord(), Attribute.YELLOW_TEXT()));
        }
        return isGameOver;
    }

    /**
     * Decrement the remaining attempts
     */
    public void decrementAttempts() {
        remainingAttempts--;
    }
}