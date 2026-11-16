
package board;

import java.util.ArrayList;
import java.util.List;

import pieces.bishop;
import pieces.color;
import pieces.king;
import pieces.knight;
import pieces.pawn;
import pieces.piece;
import pieces.queen;
import pieces.rook;

public class board {

    // The squares
    private final piece[][] grid = new piece[8][8];

    // Pieces that have been captured so far
    private final List<piece> captured = new ArrayList<>();

    // Creates a board with all pieces in starting positions
    public board() {
        setupBoard();
    }

    // Clears the board and sets up all pieces
    public void setupBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                grid[r][c] = null;
            }
        }

        captured.clear();

        placeBackRank(0, color.BLACK);
        placeBackRank(7, color.WHITE);

        for (int c = 0; c < 8; c++) {
            place(new pawn(color.BLACK, new position(1, c)));
            place(new pawn(color.WHITE, new position(6, c)));
        }
    }

    /**
     * Places the eight pieces on one row.
     *
     * @param row the row to fill
     * @param color the color of the pieces
     */
    private void placeBackRank(int row, color color) {
        place(new rook(color, new position(row, 0)));
        place(new knight(color, new position(row, 1)));
        place(new bishop(color, new position(row, 2)));
        place(new queen(color, new position(row, 3)));
        place(new king(color, new position(row, 4)));
        place(new bishop(color, new position(row, 5)));
        place(new knight(color, new position(row, 6)));
        place(new rook(color, new position(row, 7)));
    }

    /**
     * Puts a piece on the square given by its position.
     *
     * @param piece the piece to place
     */
    private void place(piece piece) {
        position p = piece.getPosition();
        grid[p.getRow()][p.getColumn()] = piece;
    }

    /**
     * Gets the piece on a square.
     *
     * @param position the square to look at
     * @return the piece there
     */
    public piece getPiece(position position) {
        if (position == null || !position.isValid()) {
            return null;
        }

        return grid[position.getRow()][position.getColumn()];
    }

    /**
     * Gets the list of captured pieces.
     *
     * @return captured pieces
     */
    public List<piece> getCaptured() {
        return captured;
    }

    /**
     * Moves a piece from one square to another,
     * capturing an enemy piece if present.
     *
     * @param from the starting square
     * @param to the destination square
     * @return true if the move was made
     */
    public boolean movePiece(position from, position to) {

        // Check that positions are valid
        if (from == null || to == null ||
            !from.isValid() || !to.isValid()) {
            return false;
        }

        piece piece = getPiece(from);

        // Check that a piece exists
        if (piece == null) {
            return false;
        }

        piece target = getPiece(to);

        // Prevent capturing your own pieces or a king
        if (target != null) {
            if (target.getColor() == piece.getColor()) {
                return false;
            }

            if (target instanceof king) {
                return false;
            }
        }

        // Validate the movement pattern
        if (!piece.possibleMoves(this).contains(to)) {
            return false;
        }

        // Make sure the king remains safe
        if (!isMoveSafe(from, to)) {
            return false;
        }

        // Capture enemy piece
        if (target != null) {
            captured.add(target);
        }

        // Move piece
        grid[from.getRow()][from.getColumn()] = null;
        grid[to.getRow()][to.getColumn()] = piece;

        piece.setPosition(to);

        return true;
    }

    /**
     * Checks whether a king is under attack.
     *
     * @param color the side to check
     * @return true if the king is in check
     */
    public boolean isCheck(color color) {

        position kingPos = findKing(color);

        if (kingPos == null) {
            return false;
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                piece p = grid[r][c];

                if (p != null &&
                    p.getColor() != color &&
                    p.possibleMoves(this).contains(kingPos)) {

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Tests whether a move leaves the king safe.
     *
     * @param from starting square
     * @param to destination square
     * @return true if the king is safe
     */
    public boolean isMoveSafe(position from, position to) {

        // Validate positions
        if (from == null || to == null ||
            !from.isValid() || !to.isValid()) {
            return false;
        }

        piece piece = getPiece(from);

        if (piece == null) {
            return false;
        }

        piece target = getPiece(to);

        // Reject friendly captures and king captures
        if (target != null &&
            (target.getColor() == piece.getColor() ||
             target instanceof king)) {
            return false;
        }

        // Temporarily move the piece
        grid[from.getRow()][from.getColumn()] = null;
        grid[to.getRow()][to.getColumn()] = piece;
        piece.setPosition(to);

        // Check king safety
        boolean safe = !isCheck(piece.getColor());

        // Restore original board
        grid[to.getRow()][to.getColumn()] = target;
        grid[from.getRow()][from.getColumn()] = piece;
        piece.setPosition(from);

        return safe;
    }

    /**
     * Checks whether a side has at least one legal move.
     *
     * @param color the side to check
     * @return true if the side has a legal move
     */
    public boolean hasLegalMove(color color) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                piece p = grid[r][c];

                if (p == null || p.getColor() != color) {
                    continue;
                }

                for (position dest : p.possibleMoves(this)) {

                    if (isMoveSafe(p.getPosition(), dest)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks whether a side is in checkmate.
     *
     * @param color the side to check
     * @return true if the side is checkmated
     */
    public boolean isCheckmate(color color) {
        return isCheck(color) && !hasLegalMove(color);
    }

    /**
     * Checks whether a side is in stalemate.
     *
     * @param color the side to check
     * @return true if the side is stalemated
     */
    public boolean isStalemate(color color) {
        return !isCheck(color) && !hasLegalMove(color);
    }

    /**
     * Finds the king of a given color.
     *
     * @param color the side whose king to find
     * @return the king position
     */
    private position findKing(color color) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                piece p = grid[r][c];

                if (p instanceof king &&
                    p.getColor() == color) {

                    return p.getPosition();
                }
            }
        }

        return null;
    }

    /**
     * Prints the board to the console.
     */
    public void display() {

        System.out.print("  ");

        for (char file = 'A'; file <= 'H'; file++) {
            System.out.print(file + "  ");
        }

        System.out.println();

        for (int r = 0; r < 8; r++) {

            System.out.print((8 - r) + " ");

            for (int c = 0; c < 8; c++) {

                piece p = grid[r][c];

                if (p != null) {
                    System.out.print(p.getSymbol());
                } else if ((r + c) % 2 == 1) {
                    System.out.print("##");
                } else {
                    System.out.print("  ");
                }

                System.out.print(" ");
            }

            System.out.println();
        }
    }
}
