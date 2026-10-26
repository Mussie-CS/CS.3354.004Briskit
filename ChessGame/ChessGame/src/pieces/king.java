package pieces;

public class king {
    
 
import java.util.List;
 
import board.board;
import board.position;
 

//A king: moves one square in any direction. (Castling is not included yet.)
public class king extends piece {
 
    // The eight neighboring squares
    private static final int[][] OFFSETS = {
        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
        {0, 1}, {1, -1}, {1, 0}, {1, 1}
    };
 
    /**
     * Creates a king.
     *
     * @param color    the side the piece belongs to
     * @param position the starting square
     */
    public king(color color, position position) {
        super(color, position);
    }
 
    /**
     * Gets the letter used in this piece's display code.
     *
     * @return "K"
     */
    @Override
    protected String getLetter() {
        return "K";
    }
 
    /**
     * Lists the squares this king can move to.
     *
     * @param board the board the piece is on
     * @return the squares the piece can move to or capture on
     */
    @Override
    public List<position> possibleMoves(board board) {
        return stepMoves(board, OFFSETS);
    }
}
