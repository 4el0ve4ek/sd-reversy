package game.cell;

public enum CellType {
    NOTHING(" "),
    POSSIBLE("+"),
    BLACK("Black"),
    WHITE("White");

    private final String name;
    private final String shortName;


    CellType(String name) {
        this.name = name;
        this.shortName = name.substring(0, 1);
    }

    @Override
    public String toString() {
        return shortName;
    }

    public String getLongName() {
        return name;
    }
}