package pieces;

import java.util.ArrayList;
import java.util.List;
 
import board.board;
import board.position;
 
/**
 * Abstract base class for all chess pieces. Holds the color and position
 * every piece has, plus two helpers that the subclasses use to find moves.
 */
public abstract class piece {
 
    //Directions a rook slides: up, down, left, right
    protected static final int[][] STRAIGHT = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
 
    //Directions a bishop slides: the four diagonals. 
    protected static final int[][] DIAGONAL = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
 
    //Which side this piece belongs to
    protected color color;
 
    //The square this piece is currently on
    protected position position;
 
    /**
     * Creates a piece
     *
     * @param color    the side the piece belongs to
     * @param position the starting square
     */
    public piece(color color, position position) {
        this.color = color;
        this.position = position;
    }
 
    /**
     * Gets the piece color
     *
     * @return WHITE or BLACK
     */
    public color getColor() {
        return color;
    }
 
    /**
     * Gets the piece's current square
     *
     * @return the position
     */
    public position getPosition() {
        return position;
    }
 
    /**
     * Sets the piece square
     *
     * @param newPosition the new square
     */
    public void setPosition(position newPosition) {
        this.position = newPosition;
    }
 
    /**
     * Moves this piece to a new square.
     *  
     *
     * @param newPosition the new square
     */
    public void move(position newPosition) {
        setPosition(newPosition);
    }
 
    /**
     * Gets the one character name of this piece
     *
     * @return the letter used in the display code
     */
    protected abstract String getLetter();
 
    /**
     * Gets the two character display code
     *
     * @return the color prefix followed by the piece letter
     */
    public String getSymbol() {
        return (color == color.WHITE ? "w" : "b") + getLetter();
    }
 
    /**
     * Lists the squares this piece could move to from its current square,
     *
     * @param board the board the piece is on
     * @return the squares the piece can move to or capture on
     */
    public abstract List<position> possibleMoves(board board);
 
    /**
     * Helper for pieces that slide any distance (rook, bishop, queen)

     *
     * @param board      the board
     * @param directions the directions to slide,
     * @return the reachable squares
     */
    protected List<position> slideMoves(board board, int[][] directions) {
        List<position> moves = new ArrayList<>();
        for (int[] d : directions) {
            int r = position.getRow() + d[0];
            int c = position.getColumn() + d[1];
            position p = new position(r, c);
            while (p.isValid()) {
                piece other = board.getPiece(p);
                if (other == null) {
                    moves.add(p);
                } else {
                    if (other.getColor() != color) {
                        moves.add(p);
                    }
                    break;
                }
                r += d[0];
                c += d[1];
                p = new position(r, c);
            }
        }
        return moves;
    }
 
    /**
     * Helper for pieces that jump or step to fixed squares (knight, king).
     *
     * @param board   the board
     * @param offsets the squares to try
     * @return the reachable squares
     */
    protected List<position> stepMoves(board board, int[][] offsets) {
        List<position> moves = new ArrayList<>();
        for (int[] o : offsets) {
            position p = new position(position.getRow() + o[0], position.getColumn() + o[1]);
            if (p.isValid()) {
                piece other = board.getPiece(p);
                if (other == null || other.getColor() != color) {
                    moves.add(p);
                }
            }
        }
        return moves;
    }
}
