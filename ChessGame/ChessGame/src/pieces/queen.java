
package pieces;

import java.util.List;

import board.board;
import board.position;

// A queen slides like a rook and bishop combined.
public class queen extends piece {

    /**
     * Creates a queen.
     *
     * @param color the side the piece belongs to
     * @param position the starting square
     */
    public queen(color color, position position) {
        super(color, position);
    }

    /**
     * Gets the letter used in the display code.
     *
     * @return "Q"
     */
    @Override
    protected String getLetter() {
        return "Q";
    }

    /**
     * Lists the squares this queen can move to.
     *
     * @param board the board the piece is on
     * @return the squares the queen can move to or capture on
     */
    @Override
    public List<position> possibleMoves(board board) {

        // Horizontal and vertical movement
        List<position> moves = slideMoves(board, STRAIGHT);

        // Diagonal movement
        moves.addAll(slideMoves(board, DIAGONAL));

        return moves;
    }
}
