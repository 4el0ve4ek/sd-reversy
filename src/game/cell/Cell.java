package game.cell;

public class Cell {
    private CellType type;
    public Cell() {
        type = CellType.NOTHING;
    }


    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }
}
