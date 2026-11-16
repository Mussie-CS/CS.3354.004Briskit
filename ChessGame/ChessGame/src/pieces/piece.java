
package pieces;

import java.util.ArrayList;
import java.util.List;

import board.board;
import board.position;

/**
 * Abstract base class for all chess pieces.
 * Holds the color and position of each piece
 * and provides helper methods for movement.
 */
public abstract class piece {

    // Directions for rook movement
    protected static final int[][] STRAIGHT = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    // Directions for bishop movement
    protected static final int[][] DIAGONAL = {
        {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    // Piece color and position
    protected color color;
    protected position position;

    /**
     * Creates a chess piece.
     */
    public piece(color color, position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns the piece color.
     */
    public color getColor() {
        return color;
    }

    /**
     * Returns the current position.
     */
    public position getPosition() {
        return position;
    }

    /**
     * Updates the piece position.
     */
    public void setPosition(position newPosition) {
        this.position = newPosition;
    }

    /**
     * Moves the piece to a new position.
     */
    public void move(position newPosition) {
        setPosition(newPosition);
    }

    /**
     * Returns the letter representing the piece.
     */
    protected abstract String getLetter();

    /**
     * Returns the piece symbol, such as wK or bQ.
     */
    public String getSymbol() {
        return (color == color.WHITE ? "w" : "b") + getLetter();
    }

    /**
     * Returns the possible moves for the piece.
     */
    public abstract List<position> possibleMoves(board board);

    /**
     * Handles sliding movements for rooks,
     * bishops, and queens.
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
     * Handles fixed movements for knights and kings.
     */
    protected List<position> stepMoves(board board, int[][] offsets) {

        List<position> moves = new ArrayList<>();

        for (int[] o : offsets) {

            position p = new position(
                position.getRow() + o[0],
                position.getColumn() + o[1]
            );

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
