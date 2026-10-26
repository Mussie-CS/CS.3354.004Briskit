package board;

public class position {
    // Row index, 0 to 7
    private final int row;
 
    // Column index, 0  to 7
    private final int column;
 
    /**
     * Creates a position from array
     *
     * @param row    row index (0-7)
     * @param column column index (0-7)
     */
    public position(int row, int column) {
        this.row = row;
        this.column = column;
    }
 
    /**
     * Gets the row index
     *
     * @return row index (0-7)
     */
    public int getRow() {
        return row;
    }
 
    /**
     * Gets the column index
     *
     * @return column index (0-7)
     */
    public int getColumn() {
        return column;
    }
 
    /**
     * Checks whether this position is on the board
     *
     * @return true if both row and column are between 0 and 7
     */
    public boolean isValid() {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }
 
    /**
     * Converts chess notation
     *
     * @param text a square like E2
     * @return the Position
     */
    public static position fromString(String text) {
        if (text == null) {
            return null;
        }
        text = text.trim().toUpperCase();
        if (text.length() != 2) {
            return null;
        }
        char file = text.charAt(0);
        char rank = text.charAt(1);
        if (file < 'A' || file > 'H' || rank < '1' || rank > '8') {
            return null;
        }
        return new position('8' - rank, file - 'A');
    }
 
    /**
     * Converts this position back to chess notation
     *
     * @return a square like "E2"
     */
    @Override
    public String toString() {
        return "" + (char) ('A' + column) + (8 - row);
    }
 
    /**
     * Two positions are equal if they have the same row and column
     *
     * @param other object to compare with
     * @return true if equal
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof position)) {
            return false;
        }
        position p = (position) other;
        return row == p.row && column == p.column;
    }
 
    /**
     * Hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return row * 8 + column;
    }
    
}
