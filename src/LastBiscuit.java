/*
    The LastBiscuit program is a simple variation of the game Nim.
    players take it in turns to chose one of two barrels and subtract a number of
    biscuits from that barrel. The player can also choose to subtract the same
    number of biscuits from both barrels or skip the round if they have not done so already.
    The player that takes the last biscuit wins.
 */

import java.util.Scanner;
import java.util.HashMap;

public class LastBiscuit {

    // Final variables should be declared here
    static final String BISCUITS_LEFT_IN_BARREL_ONE = "Biscuits Left - Barrel 1: ";
    static final String BISCUITS_LEFT_IN_BARREL_TWO = "Biscuits Left - Barrel 2: ";
    static final String PLAYER_TURN = "Player Turn: ";
    static final String CHOOSE_A_BARREL = "Choose a barrel: barrel1 (one), barrel2 (two), "
            +"or both (both), or skip turn (skip)? ";
    static final String PLAYER_HAS_USED_SKIP = "Sorry you've used your skip.";
    static final String BISCUITS_TO_TAKE = "How many biscuits are you taking? ";
    static final String INPUT_AN_INTEGER = "Please input an integer: ";
    static final String ILLEGAL_NUMBER = "Sorry, that's not a legal number of "
            + "biscuits for that/those barrel(s)";
    static final String WINNER = "Winner is player ";
    static final String ONE = "ONE";
    static final String TWO = "TWO";
    static final String BOTH = "BOTH";
    static final String SKIP = "SKIP";
    static final String ERROR = "ERROR";

    // Changing this value will change the number of players who can play the game
    static final int MAX_PLAYERS = 2;

    // Changing this value will change when the game ends
    static final int GAME_OVER_VALUE = 0;

    static final int BARREL_ONE_VALUE = 6;
    static final int BARREL_TWO_VALUE = 8;

    public static void main(String[] args) {

        int playerCounter = 1;
        int playerNumber = 1;

        int barrelOne = BARREL_ONE_VALUE;
        int barrelTwo = BARREL_TWO_VALUE;

        HashMap<Integer, Boolean> playerSkippedRound = new HashMap<Integer, Boolean>(MAX_PLAYERS);

        // Populate playerSkippedRound with player number as the key and the default value 'false'
        for (int i = 1; i <= MAX_PLAYERS; i++) {
            playerSkippedRound.put(i, false);
        }

        Scanner in = new Scanner(System.in);

        // The game will continue until there are no biscuits left in the barrels
        while ((barrelOne + barrelTwo) > GAME_OVER_VALUE) {

            System.out.println(BISCUITS_LEFT_IN_BARREL_ONE + barrelOne);
            System.out.println(BISCUITS_LEFT_IN_BARREL_TWO + barrelTwo);
            System.out.println(PLAYER_TURN + playerCounter);

            boolean isStillPlayersTurn = true;

            while (isStillPlayersTurn) {
                String choice;

                // Ask the player to choose an option from the menu
                System.out.print(CHOOSE_A_BARREL);
                choice = in.nextLine();

                // If player entered an invalid response let them try again
                while (!choice.equalsIgnoreCase(ONE)
                        && !choice.equalsIgnoreCase(TWO)
                        && !choice.equalsIgnoreCase(BOTH)
                        && !choice.equalsIgnoreCase(SKIP)) {

                    System.out.print(CHOOSE_A_BARREL);

                    choice = in.nextLine();

                }

                /*
                   This variable will be set to true only if the player has chosen
                   to skip the round and hasn't already used their skip
                 */
                boolean skipped = false;

                // Allow the player to skip only if they have not already done so
                if (choice.equalsIgnoreCase(SKIP)) {

                    if (playerSkippedRound.get(playerCounter)) {

                        System.out.println(PLAYER_HAS_USED_SKIP);

                        System.out.print(CHOOSE_A_BARREL);
                        choice = in.nextLine();

                        // Check for a valid input of either ONE, TWO, BOTH or SKIP
                        while (!choice.equalsIgnoreCase(ONE)
                                && !choice.equalsIgnoreCase(TWO)
                                && !choice.equalsIgnoreCase(BOTH)) {

                            if (choice.equalsIgnoreCase(SKIP)) {
                                System.out.println(PLAYER_HAS_USED_SKIP);
                            }
                            System.out.print(CHOOSE_A_BARREL);
                            choice = in.nextLine();

                        }
                    } else {
                        /*
                           The player has not yet used their skip
                           let the player skip and end this round
                         */
                        playerSkippedRound.put(playerCounter, true);
                        // This round has ended so exit the loop
                        skipped = true;
                        isStillPlayersTurn = false;
                    }
                }

                if (!skipped) {
                    System.out.print(BISCUITS_TO_TAKE);
                    // Player has not entered an integer so ask them to enter an integer
                    while (!in.hasNextInt()) {
                        System.out.print(INPUT_AN_INTEGER);
                        in.next();
                    }
                    // Store the number of biscuits to subtract
                    int biscuitsToRemove = in.nextInt();
                    in.nextLine();
                    choice = choice.toUpperCase();

                /*
                   Check that the number of biscuits to remove isn't greater than
                   biscuits left in the barrel(s)
                 */
                    if (biscuitsToRemove <= 0) {
                        System.out.println(ILLEGAL_NUMBER);
                    } else if (choice.equals(ONE) && biscuitsToRemove > barrelOne) {
                        System.out.println(ILLEGAL_NUMBER);
                    } else if (choice.equals(TWO) && biscuitsToRemove > barrelTwo) {
                        System.out.println(ILLEGAL_NUMBER);
                    } else if (choice.equals(BOTH) && biscuitsToRemove > barrelOne
                            || biscuitsToRemove > barrelTwo) {
                        System.out.println(ILLEGAL_NUMBER);
                    } else {
                        // Subtract from barrel ONE, TWO or BOTH
                        switch (choice) {
                            case ONE:
                                barrelOne -= biscuitsToRemove;
                                break;
                            case TWO:
                                barrelTwo -= biscuitsToRemove;
                                break;
                            case BOTH:
                                barrelOne -= biscuitsToRemove;
                                barrelTwo -= biscuitsToRemove;
                                break;
                            default:
                                System.out.println(ERROR);
                                break;
                        }
                        // This round has ended so exit the loop
                        isStillPlayersTurn = false;
                    }
                }
            }

            // Store the current player number before player counter is changed
            playerNumber = playerCounter;
            // Increment playerCounter so the next player can take their turn
            if (playerCounter < MAX_PLAYERS) {
                playerCounter++;
            } else {
                playerCounter = 1;
            }
        }

        // END GAME and print the winning player's number to the screen
        System.out.println(BISCUITS_LEFT_IN_BARREL_ONE + barrelOne);
        System.out.println(BISCUITS_LEFT_IN_BARREL_TWO + barrelTwo);
        System.out.println(WINNER + playerNumber);
        in.close();
    }
}
