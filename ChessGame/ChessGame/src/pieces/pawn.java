package pieces;

import java.util.ArrayList;
import java.util.List;

import board.board;
import board.position;


 //A pawn: moves forward one square , and captures one square diagonally forward.
 
public class pawn extends piece {

    /**
     * Creates a pawn.
     *
     * @param color    the side the piece belongs to
     * @param position the starting square
     */
    public pawn(color color, position position) {
        super(color, position);
    }

    /**
     * Gets the letter used display code.
     *
     * @return "p"
     */
    @Override
    protected String getLetter() {
        return "p";
    }

    /**
     * Lists the squares this pawn can move to. White moves up the board
     *
     * @param board the board the piece is on
     * @return the squares the pawn can move to or capture on
     */
    @Override
    public List<position> possibleMoves(board board) {
        List<position> moves = new ArrayList<>();
        int direction = (color == color.WHITE) ? -1 : 1;
        int startRow = (color == color.WHITE) ? 6 : 1;
        int row = position.getRow();
        int col = position.getColumn();

        // one square forward
        position one = new position(row + direction, col);
        if (one.isValid() && board.getPiece(one) == null) {
            moves.add(one);

            // two squares forward
            position two = new position(row + 2 * direction, col);
            if (row == startRow && board.getPiece(two) == null) {
                moves.add(two);
            }
        }

        // diagonal captures
        for (int dc = -1; dc <= 1; dc += 2) {
            position diag = new position(row + direction, col + dc);
            if (diag.isValid()) {
                piece other = board.getPiece(diag);
                if (other != null && other.getColor() != color) {
                    moves.add(diag);
                }
            }
        }
        return moves;
    }
}
