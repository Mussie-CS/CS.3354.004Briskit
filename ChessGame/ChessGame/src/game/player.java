package game;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
import board.board;
import board.position;
import pieces.color;
import pieces.piece;
import utils.utils;
 
/**
 * One of the two players. Asks for moves and carries them out on the board.
 */
public class player {
 
    /** The side this player controls. */
    private final color color;
 
    /**
     * Creates a player.
     *
     * @param color the side the player controls
     */
    public player(color color) {
        this.color = color;
    }
 
    /**
     * Gets the player's side.
     *
     * @return WHITE or BLACK
     */
    public color getColor() {
        return color;
    }
 
    /**
     * Lists this player's pieces that are still on the board.
     *
     * @param board the board to look at
     * @return the player's remaining pieces
     */
    public List<piece> getAvailablePieces(board board) {
        List<piece> pieces = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                piece p = board.getPiece(new position(r, c));
                if (p != null && p.getColor() == color) {
                    pieces.add(p);
                }
            }
        }
        return pieces;
    }
 
    /**
     * Asks the player for a move until a valid one is entered, then makes it
     * on the board.
     *
     * @param board the board to move on
     * @param input where to read the player's typing from
     * @return true if a move was made, false if the player quit (or input ended)
     */
    public boolean makeMove(board board, Scanner input) {
        while (true) {
            System.out.print(utils.colorName(color) + ", enter your move (e.g. E2 E4, or 'quit'): ");
            if (!input.hasNextLine()) {
                return false;
            }
            String line = input.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) {
                return false;
            }
 
            position[] move = utils.parseMove(line);
            if (move == null) {
                System.out.println("Invalid format. Type two squares like \"E2 E4\".");
                continue;
            }
 
            String error = findProblem(board, move[0], move[1]);
            if (error != null) {
                System.out.println(error);
                continue;
            }
 
            board.movePiece(move[0], move[1]);
            return true;
        }
    }
 
    /**
     * Checks a move against the rules.
     *
     * @param board the board
     * @param from  the starting square
     * @param to    the destination square
     * @return a message describing the problem, or null if the move is fine
     */
    private String findProblem(board board, position from, position to) {
        piece piece = board.getPiece(from);
        if (piece == null) {
            return "There is no piece on " + from + ".";
        }
        if (piece.getColor() != color) {
            return "That piece belongs to the other player.";
        }
        if (!piece.possibleMoves(board).contains(to)) {
            return "That piece can't move from " + from + " to " + to + ".";
        }
        if (!board.isMoveSafe(from, to)) {
            return "That move would leave your king in check.";
        }
        return null;
    }
}
