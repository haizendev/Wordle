package fr.haizen.wordle.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuessResult {

    /**
     * If the guess is correct
     */
    private final boolean correct;

    /**
     * The feedback of the guess (LetterFeedback[], colors for each letter)
     */
    private final LetterFeedback[] feedback;
}