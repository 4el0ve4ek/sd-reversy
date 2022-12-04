package game.player;

import game.cell.CellType;

import java.util.List;

public interface Player {
    Move chooseMove(List<Move> possibleMoves);
    CellType getTurnColor();
}
