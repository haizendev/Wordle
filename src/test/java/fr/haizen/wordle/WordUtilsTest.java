package fr.haizen.wordle;

import fr.haizen.wordle.utils.WordUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created at 27/03/2025 - HaiZen
 * This class defines the unit tests for the WordUtils class
 */
class WordUtilsTest {

    /**
     * WordUtils - UNIT TEST #1
     * Result: Valid
     * Reason: The word is valid (full letter, 5 letters)
     */
    @Test
    void validateWord_ValidFiveLetterWord_ReturnsTrue() {
        assertTrue(WordUtils.isValidWord("HELLO"));
    }

    /**
     * WordUtils - UNIT TEST #2
     * Result: Invalid
     * Reason: The word contains numbers
     */
    @Test
    void validateWord_WordWithNumbers_ReturnsFalse() {
        assertFalse(WordUtils.isValidWord("HELL0"));
    }

    /**
     * WordUtils - UNIT TEST #3
     * Result: Invalid
     * Reason: The word contains special characters
     */
    @Test
    void validateWord_WordWithSpecialCharacters_ReturnsFalse() {
        assertFalse(WordUtils.isValidWord("HELL!"));
    }

    /**
     * WordUtils - UNIT TEST #4
     * Result: Invalid
     * Reason: The word is shorter than 5 letters
     */
    @Test
    void validateWord_WordShorterThanFiveLetters_ReturnsFalse() {
        assertFalse(WordUtils.isValidWord("HELL"));
    }

    /**
     * WordUtils - UNIT TEST #5
     * Result: Invalid
     * Reason: The word is longer than 5 letters
     */
    @Test
    void validateWord_WordLongerThanFiveLetters_ReturnsFalse() {
        assertFalse(WordUtils.isValidWord("HELLOO"));
    }

    /**
     * WordUtils - UNIT TEST #6
     * Result: Invalid
     * Reason: The word is null
     */
    @Test
    void validateWord_NullWord_ReturnsFalse() {
        assertFalse(WordUtils.isValidWord(null));
    }

    /**
     * WordUtils - UNIT TEST #7
     * Result: Invalid
     * Reason: The word is empty
     */
    @Test
    void validateWord_EmptyWord_ReturnsFalse() {
        assertFalse(WordUtils.isValidWord(""));
    }
} 