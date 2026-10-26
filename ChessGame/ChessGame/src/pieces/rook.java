package pieces;
 
import java.util.List;
 
import board.board;
import board.position;
 

 //A rook: slides any distance in straight lines.

public class rook extends piece {
 
    /**
     * Creates a rook.
     *
     * @param color    the side the piece belongs to
     * @param position the starting square
     */
    public rook(color color, position position) {
        super(color, position);
    }
 
    /**
     * Gets the letter used in display code.
     *
     * @return "R"
     */
    @Override
    protected String getLetter() {
        return "R";
    }
 
    /**
     * Lists the squares this rook can move to
     *
     * @param board the board the piece is on
     * @return the squares the piece can move to
     */
    @Override
    public List<position> possibleMoves(board board) {
        return slideMoves(board, STRAIGHT);
    }
}
