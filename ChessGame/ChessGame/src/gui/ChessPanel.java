
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import board.position;
import pieces.*;

/**
 * Displays and manages the Phase 2 chessboard.
 */
public class ChessPanel extends JPanel {

    private piece[][] grid = new piece[8][8];
    private JButton[][] squares = new JButton[8][8];

    private position selected = null;
    private color currentTurn = color.WHITE;

    private Color lightColor = new Color(238, 238, 210);
    private Color darkColor = new Color(118, 150, 86);

    private int squareSize = 65;

    private GameHistory history;

    private boolean gameOver = false;

    // Stores previous game states for undo
    private List<piece[][]> previousBoards = new ArrayList<>();
    private List<color> previousTurns = new ArrayList<>();

    /**
     * Creates the chessboard and its buttons.
     */
    public ChessPanel(GameHistory history) {

        this.history = history;

        setLayout(new GridLayout(8, 8));

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                final int row = r;
                final int col = c;

                JButton square = new JButton();

                square.setFont(new Font("Serif", Font.PLAIN, 40));
                square.setFocusPainted(false);
                square.setOpaque(true);
                square.setBorderPainted(false);

                square.addActionListener(e -> handleClick(row, col));

                squares[r][c] = square;
                add(square);
            }
        }

        newGame();
    }

    /**
     * Resets the chessboard to its starting positions.
     */
    public void newGame() {

        grid = new piece[8][8];

        previousBoards.clear();
        previousTurns.clear();

        currentTurn = color.WHITE;
        selected = null;
        gameOver = false;

        // Black back rank
        placeBackRank(0, color.BLACK);

        // White back rank
        placeBackRank(7, color.WHITE);

        // Pawns
        for (int c = 0; c < 8; c++) {
            grid[1][c] = new pawn(
                color.BLACK, new position(1, c)
            );

            grid[6][c] = new pawn(
                color.WHITE, new position(6, c)
            );
        }

        updateBoard();
    }

    /**
     * Places the major pieces on a back rank.
     */
    private void placeBackRank(int row, color side) {

        grid[row][0] = new rook(side, new position(row, 0));
        grid[row][1] = new knight(side, new position(row, 1));
        grid[row][2] = new bishop(side, new position(row, 2));
        grid[row][3] = new queen(side, new position(row, 3));
        grid[row][4] = new king(side, new position(row, 4));
        grid[row][5] = new bishop(side, new position(row, 5));
        grid[row][6] = new knight(side, new position(row, 6));
        grid[row][7] = new rook(side, new position(row, 7));
    }

    /**
     * Handles selecting and moving chess pieces.
     */
    private void handleClick(int row, int col) {

        if (gameOver) {
            return;
        }

        position clicked = new position(row, col);
        piece clickedPiece = grid[row][col];

        // First click selects a piece
        if (selected == null) {

            if (clickedPiece != null &&
                clickedPiece.getColor() == currentTurn) {

                selected = clicked;
                updateBoard();
            }

            return;
        }

        // Clicking the selected square cancels selection
        if (selected.getRow() == row &&
            selected.getColumn() == col) {

            selected = null;
            updateBoard();
            return;
        }

        // Clicking another friendly piece selects it
        if (clickedPiece != null &&
            clickedPiece.getColor() == currentTurn) {

            selected = clicked;
            updateBoard();
            return;
        }

        movePiece(selected, clicked);
        selected = null;

        updateBoard();
    }

    /**
     * Moves a piece without standard chess movement validation.
     * Captures opposing pieces and checks for king capture.
     */
    private void movePiece(position from, position to) {

        piece movingPiece =
            grid[from.getRow()][from.getColumn()];

        piece capturedPiece =
            grid[to.getRow()][to.getColumn()];

        if (movingPiece == null) {
            return;
        }

        // Save state before moving
        saveState();

        String pieceName =
            movingPiece.getClass().getSimpleName();

        String fromSquare = squareName(from);
        String toSquare = squareName(to);

        // Move piece
        grid[from.getRow()][from.getColumn()] = null;
        grid[to.getRow()][to.getColumn()] = movingPiece;

        movingPiece.setPosition(to);

        // Record move
        history.addMove(
            currentTurn + " " + pieceName +
            ": " + fromSquare + " -> " + toSquare
        );

        // Record capture
        if (capturedPiece != null) {

            history.addCapture(
                currentTurn + " captured " +
                capturedPiece.getClass().getSimpleName()
            );
        }

        // King capture ends the game
        if (capturedPiece instanceof king) {

            gameOver = true;

            updateBoard();

            JOptionPane.showMessageDialog(
                this,
                currentTurn + " wins by capturing the King!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
            );

            // Board stays visible but no further moves allowed
            return;
        }

        // Switch turns
        currentTurn = currentTurn.opposite();
    }

    /**
     * Saves the current board state for undo.
     */
    private void saveState() {

        piece[][] copy = new piece[8][8];

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                copy[r][c] = grid[r][c];
            }
        }

        previousBoards.add(copy);
        previousTurns.add(currentTurn);
    }

    /**
     * Undoes the most recent move.
     */
    public void undoMove() {

        if (previousBoards.isEmpty()) {
            return;
        }

        int last = previousBoards.size() - 1;

        grid = previousBoards.remove(last);
        currentTurn = previousTurns.remove(last);

        // Restore each piece's stored position
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                if (grid[r][c] != null) {
                    grid[r][c].setPosition(new position(r, c));
                }
            }
        }

        selected = null;
        gameOver = false;

        history.undoLastMove();
        updateBoard();
    }

    /**
     * Converts a position into chess notation.
     */
    private String squareName(position p) {

        char file = (char) ('A' + p.getColumn());
        int rank = 8 - p.getRow();

        return "" + file + rank;
    }

    /**
     * Updates the appearance of all squares.
     */
    private void updateBoard() {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                JButton square = squares[r][c];
                piece p = grid[r][c];

                square.setText(getSymbol(p));

                if ((r + c) % 2 == 0) {
                    square.setBackground(lightColor);
                } else {
                    square.setBackground(darkColor);
                }

                if (selected != null &&
                    selected.getRow() == r &&
                    selected.getColumn() == c) {

                    square.setBackground(Color.YELLOW);
                }

                square.setPreferredSize(
                    new Dimension(squareSize, squareSize)
                );
            }
        }

        revalidate();
        repaint();
    }

    /**
     * Changes the board colors.
     */
    public void setBoardColors(Color light, Color dark) {

        lightColor = light;
        darkColor = dark;

        updateBoard();
    }

    /**
     * Changes the size of the chessboard squares.
     */
    public void setSquareSize(int size) {

        squareSize = size;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                squares[r][c].setPreferredSize(
                    new Dimension(size, size)
                );

                squares[r][c].setFont(
                    new Font("Serif", Font.PLAIN, size * 2 / 3)
                );
            }
        }

        revalidate();

        Window window = SwingUtilities.getWindowAncestor(this);

        if (window != null) {
            window.pack();
        }

        repaint();
    }

    /**
     * Returns the Unicode chess symbol for a piece.
     */
    private String getSymbol(piece p) {

    if (p == null) {
        return "";
    }

    boolean white = p.getColor() == color.WHITE;

    if (p instanceof king)
        return white ? "♔" : "♚";

    if (p instanceof queen)
        return white ? "♕" : "♛";

    if (p instanceof rook)
        return white ? "♖" : "♜";

    if (p instanceof bishop)
        return white ? "♗" : "♝";

    if (p instanceof knight)
        return white ? "♘" : "♞";

    if (p instanceof pawn)
        return white ? "♙" : "♟";

    return "";
}
}
