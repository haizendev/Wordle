package fr.haizen.wordle.utils;

public class WordUtils {

    /**
     * The max length of the word to guess
     */
    public static final int WORD_LENGTH = 5;

    /**
     * The max attempts to guess the word
     */
    public static final int MAX_ATTEMPTS = 6;

    /**
     * The Wordle logo (ASCII art)
     */
    public static final String[] WORDLE_LOGO = {
            "██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ███████╗",
            "██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██╔════╝",
            "██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     █████╗  ",
            "██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██╔══╝  ",
            "╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗███████╗",
            " ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝",
    };

    /**
     * Check if a word is valid (5 letters)
     *
     * @param word the word to check
     * @return true if the word is valid
     */
    public static boolean isValidWord(String word) {
        /*
         * Check if the string is null
         */
        if (word == null) return false;

        /*
         * Check if the word has the correct length
         */
        if (word.length() != WORD_LENGTH) return false;

        /*
         * Check if the word contains only letters
         */
        return word.chars().allMatch(Character::isLetter);
    }
} 