package pieces;

public class pieces {
    
    
}

public abstract class piece {
    public piece(color color, position position)  
    public color getColor()
    public position getPosition()
    public void setPosition(position p)
    public String getSymbol()                      
    public abstract List<position> possibleMoves(board board);
}
