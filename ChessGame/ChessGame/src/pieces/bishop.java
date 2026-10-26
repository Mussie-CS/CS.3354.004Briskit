package pieces;
 
import java.util.List;
 
import board.board;
import board.position;
 
 //A bishop: slides any distance diagonally.

public class bishop extends piece {
 
    /**
     * Creates a bishop.
     *
     * @param color    the side the piece belongs to
     * @param position the starting square
     */
    public bishop(color color, position position) {
        super(color, position);
    }
 
    /**
     * Gets the letter used in display code.
     *
     * @return "B"
     */
    @Override
    protected String getLetter() {
        return "B";
    }
 
    /**
     * Lists the squares this bishop can move to.
     *
     * @param board the board the piece is on
     * @return the squares the piece can move to or capture on
     */
    @Override
    public List<position> possibleMoves(board board) {
        return slideMoves(board, DIAGONAL);
    }
}
