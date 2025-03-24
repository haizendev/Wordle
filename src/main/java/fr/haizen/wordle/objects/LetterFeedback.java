package fr.haizen.wordle.objects;

import com.diogonunes.jcolor.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created at 24/03/2025 - HaiZen
 * This enum defines the feedback of a letter (colors)
 */
@Getter
@AllArgsConstructor
public enum LetterFeedback {

    /**
     * Green - correct letter, correct position
     */
    CORRECT(Attribute.GREEN_TEXT()),

    /**
     * Yellow - correct letter, wrong position
     */
    WRONG_POSITION(Attribute.YELLOW_TEXT()),

    /**
     * White - letter not in word
     */
    INCORRECT(Attribute.WHITE_TEXT());

    /**
     * Get the color of the feedback
     */
    final Attribute color;
} 