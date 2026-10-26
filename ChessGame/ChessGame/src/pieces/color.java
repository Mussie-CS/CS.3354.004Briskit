package pieces;

public enum color {

    // The white side moves first, the black
    WHITE,
    BLACK;
 
    /**
     * Gets the other colo
     *
     * @return BLACK if this is WHITE, otherwise WHITE
     */
    public color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}

