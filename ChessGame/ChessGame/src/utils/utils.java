package utils;

import board.position;
import pieces.color;

/**
 * Helper methods shared by the rest of the game.
 */
public class utils {

    /**
     * Turns text like "E2 E4" into a start and end square.
     *
     * @param input the text the player typed
     * @return a two-element array
     */
    public static position[] parseMove(String input) {
        if (input == null) {
            return null;
        }
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            return null;
        }
        position from = position.fromString(parts[0]);
        position to = position.fromString(parts[1]);
        if (from == null || to == null) {
            return null;
        }
        return new position[] {from, to};
    }

    /**
     * Gets a readable name for a side.
     *
     * @param color the side
     * @return "White" or "Black"
     */
    public static String colorName(color color) {
        return color == color.WHITE ? "White" : "Black";
    }
}
