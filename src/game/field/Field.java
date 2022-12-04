package game.field;

import game.cell.Cell;
import game.player.Move;

public interface Field {
    Cell getCell(int height, int width);

    Cell getCell(Move move);

    int getWidth();

    int getHeight();

    void print();

}
