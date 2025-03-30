package fr.haizen.wordle;

import fr.haizen.wordle.objects.GuessResult;
import fr.haizen.wordle.objects.LetterFeedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created at 30/03/2025 - HaiZen
 * This class defines the unit tests for the GameLogic class
 */
class GameLogicTest {

    /**
     * Instance of the game logic
     */
    private GameLogic gameLogic;

    /**
     * Create a fake target word
     */
    private static final String TARGET_WORD = "HELLO";

    /**
     * Set up the game logic before each test
     */
    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic(TARGET_WORD);
    }

    /**
     * GameLogic - UNIT TEST #1
     * Result: Valid
     * Reason: The target word is valid
     */
    @Test
    void checkGuess_CorrectWord_ReturnsCorrectResult() {
        GuessResult result = gameLogic.checkGuess(TARGET_WORD);
        assertTrue(result.isCorrect());
        assertAll("All letters should be correct",
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[0]),
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[1]),
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[2]),
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[3]),
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[4])
        );
    }

    /**
     * GameLogic - UNIT TEST #2
     * Result: Invalid
     * Reason: All letters are incorrect
     */
    @Test
    void checkGuess_AllWrongLetters_ReturnsIncorrectResult() {
        GuessResult result = gameLogic.checkGuess("BRICK");
        assertFalse(result.isCorrect());
        assertAll("All letters should be incorrect",
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[0]),
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[1]),
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[2]),
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[3]),
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[4])
        );
    }

    /**
     * GameLogic - UNIT TEST #3
     * Result: Invalid
     * Reason: Some letters are correct
     */
    @Test
    void checkGuess_SomeCorrectPositions_ReturnsMixedResult() {
        GuessResult result = gameLogic.checkGuess("HAPPY");
        assertFalse(result.isCorrect());
        assertAll("Mixed feedback for letters",
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[0]), // H
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[1]), // A
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[2]), // P
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[3]), // P
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[4])  // Y
        );
    }

    /**
     * GameLogic - UNIT TEST #4
     * Result: Invalid
     * Reason: Letters are correct but in the wrong position
     */
    @Test
    void checkGuess_SomeWrongPositions_ReturnsMixedResult() {
        GuessResult result = gameLogic.checkGuess("OLLEH");
        assertFalse(result.isCorrect());
        assertAll("All letters correct but wrong positions",
                () -> assertEquals(LetterFeedback.WRONG_POSITION, result.getFeedback()[0]), // O
                () -> assertEquals(LetterFeedback.WRONG_POSITION, result.getFeedback()[1]), // L
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[2]), // L
                () -> assertEquals(LetterFeedback.WRONG_POSITION, result.getFeedback()[3]), // E
                () -> assertEquals(LetterFeedback.WRONG_POSITION, result.getFeedback()[4])  // H
        );
    }

    /**
     * GameLogic - UNIT TEST #5
     * Result: Invalid
     * Reason: The word is invalid (not 5 letters)
     */
    @Test
    void checkGuess_InvalidWord_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> gameLogic.checkGuess("INVALID"));
    }

    /**
     * GameLogic - UNIT TEST #6
     * Test the remaining attempts
     */
    @Test
    void checkGuess_DecrementsRemainingAttempts() {
        assertEquals(6, gameLogic.getRemainingAttempts());
        gameLogic.checkGuess("WORLD");
        gameLogic.decrementAttempts();
        assertEquals(5, gameLogic.getRemainingAttempts());
    }

    /**
     * GameLogic - UNIT TEST #7
     * The word is correct, the remaining attempts should be set to zero
     */
    @Test
    void checkGuess_CorrectGuess_SetsRemainingAttemptsToZero() {
        assertEquals(6, gameLogic.getRemainingAttempts());
        gameLogic.checkGuess(TARGET_WORD);
        assertEquals(0, gameLogic.getRemainingAttempts());
    }

    /**
     * GameLogic - UNIT TEST #8
     * The game is not over at the beginning
     */
    @Test
    void isGameOver_InitialState_ReturnsFalse() {
        assertFalse(gameLogic.isGameOver());
    }

    /**
     * GameLogic - UNIT TEST #9
     * The game is over if there are no remaining attempts
     */
    @Test
    void isGameOver_NoRemainingAttempts_ReturnsTrue() {
        for (int i = 0; i < 6; i++) {
            gameLogic.checkGuess("WORLD");
            gameLogic.decrementAttempts();
        }
        assertTrue(gameLogic.isGameOver());
    }

    /**
     * GameLogic - UNIT TEST #10
     * Result: Invalid
     * Reason: Create a game with an invalid target word
     */
    @Test
    void constructor_InvalidTargetWord_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new GameLogic("12345"));
    }

    /**
     * GameLogic - UNIT TEST #11
     * Result: Valid
     * Reason: The target word is valid
     */
    @Test
    void getTargetWord_ReturnsUpperCaseWord() {
        assertEquals("HELLO", gameLogic.getTargetWord());
    }

    /**
     * GameLogic - UNIT TEST #12
     * Result: Invalid
     * Reason: The guess contains a duplicate letter
     */
    @Test
    void checkGuess_WithDuplicateLetters_HandlesFeedbackCorrectly() {
        GameLogic duplicateGame = new GameLogic("LEVEL");

        /*
         * Check the guess "LEMON" against the target word "LEVEL"
         * L and E are correct, V and 2nd L are incorrect
         */
        GuessResult result = duplicateGame.checkGuess("LEMON");
        assertAll(
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[0]), // L
                () -> assertEquals(LetterFeedback.CORRECT, result.getFeedback()[1]), // E
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[2]), // M
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[3]), // O
                () -> assertEquals(LetterFeedback.INCORRECT, result.getFeedback()[4])  // N
        );
    }

    /**
     * GameLogic - UNIT TEST #13
     * Decrement the attempts to zero and check if the game is still callable
     */
    @Test
    void decrementAttempts_ToZero_StillCallable() {
        for (int i = 0; i < 6; i++) gameLogic.decrementAttempts();
        assertEquals(0, gameLogic.getRemainingAttempts());
        assertTrue(gameLogic.isGameOver());
    }
}