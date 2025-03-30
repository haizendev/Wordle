# Wordle

This project implements a Wordle game in Java with comprehensive unit testing. The implementation follows test-first
development principles and includes various testing techniques.

## Features

- Word validation (5 letters, alphabetic characters only)
- Guess-checking logic with color feedback
- Game state management
- Comprehensive unit tests with high coverage

## Requirements

- Java 17 or higher
- Gradle 7.0 or higher

## Project Structure

```
src/
├── main/java/fr/haizen/wordle/
│   ├── GameLogic.java
│   ├── WordleCLI.java
│   ├── objects/
│   │   ├── GuessResult.java
│   │   └── LetterFeedback.java
│   └── utils/
│       └── WordUtils.java
└── test/java/fr/haizen/wordle/
    ├── GameLogicTest.java
    └── WordUtilsTest.java

```

## Building and Running

1. Clone the repository
2. Build the project:
   ```bash
   ./gradlew build
   ```
3. Run the tests:
   ```bash
   ./gradlew test
   ```
4. View test coverage report:
   ```bash
   ./gradlew jacocoTestReport
   ```
5. Run the game:
   ```bash
   ./gradlew run
   ```

## Testing Strategy

The project follows these testing principles:

- Test one behavior at a time
- No test dependencies
- No long-running tests
- Deterministic tests
- Isolated tests

Test coverage is maintained above 70% as required.

## Implementation Details

### WordUtils

- Validates 5-letter words
- Ensures only alphabetic characters
- Handles edge cases (null, empty, invalid length)

### GameLogic

- Manages game state
- Processes guesses
- Provides feedback for each guess
- Tracks remaining attempts

### LetterFeedback

- CORRECT: Green - correct letter, correct position
- WRONG_POSITION: Yellow - correct letter, wrong position
- INCORRECT: Gray - letter not in word

### WordleCLI

- Beautiful ASCII art logo
- Colorful interface (With JColor)
- Clear feedback for each guess
- Error handling for invalid inputs
- Game state visualization