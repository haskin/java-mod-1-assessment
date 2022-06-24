import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    private static final String[] INFORMATION = {
            "Easy: guess a number - program will tell you if it was higher or equal (you win) or lower (computer wins) than the program's number.",
            "Medium: guess a number - program will tell you if it was strictly higher (you win) or lower or equal (computer wins) than the program's number.",
            "Hard: guess a number - program will tell you if it was equal (you win) or not (you lose) to the program's number." };

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD,
        INFO;

        public static Optional<Difficulty> getDifficulty(String difficultyValue) {
            String difficultyValueFormatted = difficultyValue.trim().toUpperCase();
            if (EASY.name().equals(difficultyValueFormatted)) {
                return Optional.of(EASY);
            }

            if (MEDIUM.name().equals(difficultyValueFormatted)) {
                return Optional.of(MEDIUM);
            }

            if (HARD.name().equals(difficultyValueFormatted)) {
                return Optional.of(HARD);
            }

            if (INFO.name().equals(difficultyValueFormatted)) {
                return Optional.of(INFO);
            }

            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        System.out.println("\n ------- GAME START --------\n");
        try (Scanner scanner = new Scanner(System.in)) {
            Difficulty userDifficulty = getUserDifficulty(scanner);
            Integer userGuess = getUserGuess(scanner);
            Integer computerGuess = getRandomNumber(10);
            boolean isWin = playGame(userDifficulty, userGuess, computerGuess);
            System.out
                    .println(String.format("%nProgram Parameters: Difficulty: %s, Your Guess: %s, Computer Guess: %s",
                            userDifficulty, userGuess, computerGuess));
            if (isWin) {
                System.out.println("\nWinner winner, chicken dinner!");
            } else {
                System.out.println("\nYou lost :( At least you didn't pay any cost!");
            }
        } catch (Exception e) {
            System.out.println("\nERROR: Problem reading user input. The program will end.");
            System.exit(0);
        }
        System.out.println("\n ------- GAME ENDED --------");
        return;
    }

    /**
     * Plays the game. Logic of game is determinated by game difficulty.
     * 
     * @param gameDifficulty EASY, MEDIUM, HARD
     * @param userGuess      1 - 10
     * @param randomGuess    1 - 10
     * @return
     */
    private static final boolean playGame(Difficulty gameDifficulty, Integer userGuess, Integer randomGuess) {
        switch (gameDifficulty) {
            // higher or equal (you win)
            case EASY:
                return userGuess >= randomGuess;
            // strictly higher (you win)
            case MEDIUM:
                return userGuess.compareTo(randomGuess) > 0;
            // it was equal (you win)
            case HARD:
                return userGuess.compareTo(randomGuess) == 0;
            default:
                System.out.println("Wow. You should not have been able to get here. Have a win!");
                return true;
        }
    }

    /**
     * Outputs difficulty information in terminal
     */
    private static final void outputDifficultyInformation() {
        System.out.println("\n-------- Difficult Information -----------");
        IntStream.range(0, INFORMATION.length)
                .mapToObj(index -> INFORMATION[index])
                .forEach(System.out::println);
        System.out.println("------------------------------------------\n");
    }

    /**
     * Gets user difficulty. Possible values are EASY, MEDIUM, or HARD.
     * If there is an error with reading System.in, then the default value
     * EASY is chosen.
     * 
     * @return Difficulty
     */
    private static Difficulty getUserDifficulty(Scanner scanner) {
        final String difficultyPrompt = "Please choose a difficulty: EASY, MEDIUM, or HARD. Type INFO for difficulty information.";
        while (true) {
            try {
                System.out.println(difficultyPrompt);
                String userDifficulty = scanner.next();
                Optional<Difficulty> optionalDifficulty = Difficulty.getDifficulty(userDifficulty);
                if (optionalDifficulty.isPresent()) {
                    Difficulty difficulty = optionalDifficulty.get();
                    if (difficulty == Difficulty.INFO) {
                        outputDifficultyInformation();
                        continue;
                    }
                    return difficulty;
                }

                throw new Exception();
            } catch (Exception e) {
                System.out.println("\nERROR: Invalid answer. Please try again.");
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
        }
    }

    /**
     * Get a user guess user terminal. If there is an error getting
     * the connection to System.in the application is ended.
     * 
     * @return Integer
     */
    private static Integer getUserGuess(Scanner scanner) {
        String userGuessPrompt = "\nPlease enter a number in the range 1-10 (1 and 10 are allowed values).";
        while (true) {
            try {
                System.out.println(userGuessPrompt);
                Integer userGuess = scanner.nextInt();
                if (userGuess < 1 || userGuess > 10)
                    throw new Exception();
                return userGuess;
            } catch (Exception e) {
                System.out.println("\nERROR: Invalid answer. Please try again.");
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
        }
    }

    /**
     * Get random number between 1 (inclusive) and exclusiveUpperBound
     * 
     * @param exclusiveUpperBound
     * @return
     */
    private static int getRandomNumber(int exclusiveUpperBound) {
        Random random = new Random();
        return random.nextInt(exclusiveUpperBound) + 1;
    }
}