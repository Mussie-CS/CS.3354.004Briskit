package pieces;
 
import java.util.List;
 
import board.board;
import board.position;
 

 //A knight: jumps in an L shape and can hop over other pieces.

public class knight extends piece {
 
    //The eight jumps
    private static final int[][] OFFSETS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };
 
    /**
     * Creates a knight
     *
     * @param color    the side the piece belongs to
     * @param position the starting square
     */
    public knight(color color, position position) {
        super(color, position);
    }
 
    /**
     * Gets the letter used in  display code.
     *
     * @return "N"
     */
    @Override
    protected String getLetter() {
        return "N";
    }
 
    /**
     * Lists the squares this knight can move to
     *
     * @param board the board the piece is on
     * @return the squares the piece can move to or capture on
     */
    @Override
    public List<position> possibleMoves(board board) {
        return stepMoves(board, OFFSETS);
    }
}
