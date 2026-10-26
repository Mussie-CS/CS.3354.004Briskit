package pieces;

public enum color {

    // The white side moves first, the black

public enum color {

    // The white side moves first, then black
    WHITE,
    BLACK;
 
    /**
     * Gets the other colo
     * Gets the other side.
     *
     * @return BLACK if this is WHITE, otherwise WHITE
     */
    public color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}


    

