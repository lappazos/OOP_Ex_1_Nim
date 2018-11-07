/**
 * Move class represents a single move a player is doing.
 *
 * @author lioraryepaz
 */

public class Move {

    private int intRow;
    private int intLeft;
    private int intRight;

    /**
     * Constructor
     *
     * @param inRow   move row #
     * @param inLeft  move left bound index
     * @param inRight move right bound index
     */
    public Move(int inRow, int inLeft, int inRight) {
        intLeft = inLeft;
        intRight = inRight;
        intRow = inRow;
    }

    public int getLeftBound() {

        return intLeft;
    }

    public int getRightBound() {

        return intRight;
    }

    public int getRow() {

        return intRow;
    }

    /**
     * string representation of the move
     *
     * @return "Row: leftBound - rightBound"
     */
    public String toString() {
        return intRow + ":" + intLeft + "-" + intRight;
    }
}
