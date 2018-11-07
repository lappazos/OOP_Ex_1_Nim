import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of rounds.
 * It also keeps track of the number of victories of each player.
 *
 * @author lioraryepaz
 */
public class Competition {

    private final Player Player1;
    private final Player Player2;

    private final boolean Display;

    private int player1Score;
    private int player2Score;

    private static final String a = "Starting a Nim competition of %s rounds between a %s player and a %s player.";
    private static final String b = "Welcome to the sticks game!";
    private static final String c = "Player %s, it is now your turn!";
    private static final String d = "Player %s made the move: %s";
    private static final String e = "Invalid move. Enter another:";
    private static final String f = "Player %s won!";
    private static final String g = "The results are %s:%s";

    /**
     * Constructor
     *
     * @param player1        player 1
     * @param player2        player 2
     * @param displayMessage if one of the players is human, will be true
     */
    public Competition(Player player1, Player player2, boolean displayMessage) {
        Player1 = player1;
        Player2 = player2;
        Display = displayMessage;
        player1Score = 0;
        player2Score = 0;
    }

    public int getPlayerScore(int playerPosition) {
        if (playerPosition == 1) {
            return player1Score;
        } else {
            return player2Score;
        }
    }

    /*
     * Returns the integer representing the type of player 1; returns -1 on bad
     * input.
     */
    private static int parsePlayer1Type(String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (Exception E) {
            return -1;
        }
    }

    /*
     * Returns the integer representing the type of player 2; returns -1 on bad
     * input.
     */
    private static int parsePlayer2Type(String[] args) {
        try {
            return Integer.parseInt(args[1]);
        } catch (Exception E) {
            return -1;
        }
    }

    /*
     * Returns the integer representing the type of player 2; returns -1 on bad
     * input.
     */
    private static int parseNumberOfGames(String[] args) {
        try {
            return Integer.parseInt(args[2]);
        } catch (Exception E) {
            return -1;
        }
    }

    /**
     * The method runs a Nim competition between two players according to the three user-specified arguments.
     * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
     * player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
     * (2) The type of the second player, which is a positive integer between 1 and 4.
     * (3) The number of rounds to be played in the competition.
     *
     * @param args an array of string representations of the three input arguments, as detailed above.
     */
    public static void main(String[] args) {

        boolean message = false;

        int p1Type = parsePlayer1Type(args);
        int p2Type = parsePlayer2Type(args);
        int numGames = parseNumberOfGames(args);

        // scanner object in order to receive input from the human players /
        Scanner scanner = new Scanner(System.in);

        Player player1 = new Player(p1Type, 1, scanner);
        Player player2 = new Player(p2Type, 2, scanner);

        String stringRepOfPlayer1 = player1.getTypeName();
        String stringRepOfPlayer2 = player2.getTypeName();

        // declares if messages will be displayed or not /
        if ((p1Type == 4) || (p2Type == 4)) {
            message = true;
        }

        Competition competition = new Competition(player1, player2, message);

        System.out.println(String.format(a, numGames, stringRepOfPlayer1, stringRepOfPlayer2));

        competition.playMultipleRounds(numGames);

        System.out.println(String.format(g, competition.player1Score, competition.player2Score));
    }

    /**
     * Competition method that in charge of running the different rounds.
     *
     * @param numberOfRounds received as an argument from the user
     */
    public void playMultipleRounds(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            Board board = new Board();
            this.printMessage(this.Player1, 1, "");
            // winRound will receive boolean from playRound, for each and every round. /
            winRound(playRound(board));
        }
    }

    /**
     * in charge or printing the different messages throughout the entire game.
     *
     * @param currentPlayer the current player in the moment the message is printed
     * @param msgId         message identifier
     * @param string        an additional string we would like to use in our printing
     */
    private void printMessage(Player currentPlayer, int msgId, String string) {
        // only if DisplayMessage is set to true these are gonna be printed /
        if (this.Display) {
            if (msgId == 1) {
                System.out.println(b);
            }
            if (msgId == 2) {
                System.out.println(String.format(c, currentPlayer.getPlayerId()));
            }
            if (msgId == 3) {
                System.out.println(String.format(d, currentPlayer.getPlayerId(), string));
            }
            if (msgId == 4) {
                System.out.println(e);
            }
            if (msgId == 5) {
                System.out.println(String.format(f, currentPlayer.getPlayerId()));
            }
            if (msgId == 6) {
                System.out.println();
            }
        }
    }

    /**
     * executes one round of the game
     *
     * @param board game board
     * @return the player who won the round
     */
    private Player playRound(Board board) {
        Player currentPlayer = this.Player1;
        // every loop executes one turn of a specific round /
        while (board.getNumberOfUnmarkedSticks() > 0) {
            this.printMessage(currentPlayer, 2, "");
            boolean notLegalMove = true;
            while (notLegalMove) {
                Move currentMove = currentPlayer.produceMove(board);
                if (board.markStickSequence(currentMove) == 0) {
                    this.printMessage(currentPlayer, 3, currentMove.toString());
                    notLegalMove = false;
                } else {
                    this.printMessage(currentPlayer, 4, "");
                }
            }
            if (currentPlayer == this.Player1) {
                currentPlayer = this.Player2;
            } else {
                currentPlayer = this.Player1;
            }
        }
        return currentPlayer;
    }

    /**
     * executes winning process in the end of every round
     *
     * @param currentPlayer winning player
     */
    private void winRound(Player currentPlayer) {
        if (currentPlayer == Player1) {
            this.player1Score += 1;
            printMessage(currentPlayer, 5, "");
        } else {
            this.player2Score += 1;
            printMessage(currentPlayer, 5, "");
        }
    }
}
