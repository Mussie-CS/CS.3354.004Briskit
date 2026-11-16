
package game;

import java.util.Scanner;

import board.board;
import pieces.color;
import utils.utils;

/**
 * Runs a game of chess: sets up the board, alternates turns,
 * and ends the game on checkmate, stalemate, or resignation.
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

    /** Reads the players' input from the console. */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Sets up a new game.
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
     * Main game loop.
     * Displays the board, asks for moves,
     * switches turns, and checks for game over.
     */
    public void play() {

        while (true) {

            board.display();

            System.out.println("\n" + utils.colorName(currentTurn) + "'s turn.");

            if (board.isCheck(currentTurn)) {
                System.out.println(utils.colorName(currentTurn) + " is in check!");
            }

            // Select the current player
            player player;

            if (currentTurn == color.WHITE) {
                player = white;
            } else {
                player = black;
            }

            // Ask the player to make a move
            if (!player.makeMove(board, scanner)) {

                end(utils.colorName(currentTurn) + " resigned. "
                        + utils.colorName(currentTurn.opposite()) + " wins!");

                return;
            }

            // Switch turns
            currentTurn = currentTurn.opposite();

            System.out.println();

            // Check for checkmate
            if (board.isCheckmate(currentTurn)) {

                board.display();

                end("Checkmate! "
                        + utils.colorName(currentTurn.opposite())
                        + " wins!");

                return;
            }

            // Check for stalemate
            if (board.isStalemate(currentTurn)) {

                board.display();

                end("Stalemate. The game is a draw.");

                return;
            }
        }
    }

    /**
     * Ends the game and announces the result.
     *
     * @param message the result to display
     */
    public void end(String message) {

        System.out.println("\n" + message);
        System.out.println("Game over.");
    }
}
