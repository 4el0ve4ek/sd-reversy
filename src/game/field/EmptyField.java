package game.field;


import game.cell.Cell;
import game.player.Move;

public class EmptyField implements Field {
    private final int width, height;
    private final Cell[][] field;

    public EmptyField(int height, int width) {
        this.width = width;
        this.height = height;
        this.field = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.field[i][j] = new Cell();
            }
        }
    }


    public Cell getCell(int i, int j) {
        if (i < 0 || j < 0 || i >= height || j >= width) {
            return new Cell();
        }
        return this.field[i][j];
    }

    public Cell getCell(Move move) {
        return getCell(move.i(), move.j());
    }

    public void print() {
        String horizontalLine = "   +" + "———+".repeat(width);


        System.out.print("   ");
        for (int i = 0; i < width; ++i) {
            System.out.printf("  %d ", i);
        }
        System.out.println();

        System.out.println(horizontalLine);
        for (int i = 0; i < height; i++) {

            System.out.printf(" %d ⏐", i);
            for (int j = 0; j < width; j++) {
                System.out.printf(" %s ", getCell(i, j).getType());
                System.out.print("⏐");
            }
            System.out.println();

            System.out.println(horizontalLine);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
