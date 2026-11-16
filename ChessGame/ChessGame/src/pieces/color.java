package pieces;

public enum color {
    WHITE,
    BLACK;

    public color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}


    

