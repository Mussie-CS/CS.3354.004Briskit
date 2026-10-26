package game;
 
import java.util.Scanner;
 
import board.board;
import pieces.color;
import utils.utils;
 
/**
 * Runs a game of chess: sets up the board, alternates turns, and ends the
 * game on checkmate, stalemate, or when a player quits.
 */
public class game {
 
    /** The board being played on. */
    private board board;
 
    /** The white player. */
    private player white;
 
    /** The black player. */
    private player black;
 
    /** Whose turn it is. */
    private color currentTurn;
 
    /** Reads the players' typing from the console. */
    private final Scanner scanner = new Scanner(System.in);
 
    /**
     * Sets up a new game: a fresh board, two players, and white to move.
     */
    public void start() {
        board = new board();
        white = new player(color.WHITE);
        black = new player(color.BLACK);
        currentTurn = color.WHITE;
        System.out.println("Welcome to Console Chess!");
        System.out.println("Enter moves like \"E2 E4\". Type \"quit\" to resign.\n");
    }
 
    /**
     * The main loop. Shows the board, asks the current player for a move
     * switches turns and checks if game is over.
     */
    public void play() {
        while (true) {
            board.display();
            System.out.println("\n" + utils.colorName(currentTurn) + "'s turn.");
            if (board.isCheck(currentTurn)) {
                System.out.println(utils.colorName(currentTurn) + " is in check!");
            }
 
            player player;
            if (currentTurn == color.WHITE) {
                player = white;
            } else {
                player = black;
}
            if (!player.makeMove(board, scanner)) {
                end(utils.colorName(currentTurn) + " resigned. "
                        + utils.colorName(currentTurn.opposite()) + " wins!");
                return;
            }
 
            currentTurn = currentTurn.opposite();
            System.out.println();
 
            if (board.isCheckmate(currentTurn)) {
                board.display();
                end("Checkmate! " + utils.colorName(currentTurn.opposite()) + " wins!");
                return;
            }
            if (board.isStalemate(currentTurn)) {
                board.display();
                end("Stalemate. The game is a draw.");
                return;
            }
        }
    }
 
    /**
     * Ends the game by announcing the result.
     *
     * @param message the result to show the players
     */
    public void end(String message) {
        System.out.println("\n" + message);
        System.out.println("Game over.");
    }
}

