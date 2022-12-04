package game.player;

import game.cell.CellType;
import game.field.Field;

import java.util.List;

import static java.lang.Math.max;

public class Bot implements Player {
    private static final int SLEEP_TIME = 1400;
    private final Field field;
    private final CellType turnColor;

    public Bot(Field field, CellType turnColor) {
        this.field = field;
        this.turnColor = turnColor;
    }

    public Move chooseMove(List<Move> possibleMoves) {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Move resultMove = possibleMoves.get(0);
        int bestResult = 0;
        for (var move : possibleMoves) {
            int moveValue = calculateValue(move);
            if (moveValue > bestResult) {
                bestResult = moveValue;
                resultMove = move;
            }
        }
        System.out.printf("%s makes move %d %d\n", turnColor.getLongName(), resultMove.i(), resultMove.j());
        return resultMove;
    }

    @Override
    public CellType getTurnColor() {
        return turnColor;
    }

    private int calculateValue(Move move) {
        var i = move.i();
        var j = move.j();
        var res = getMoveCellValue(i, j);
        for (int di = -1; di <= 1; ++di) {
            for (int dj = -1; dj <= 1; ++dj) {
                if (di == dj && di == 0) {
                    continue;
                }
                if (!checkDirection(i, j, di, dj, turnColor)) {
                    continue;
                }
                for (int step = 1; step <= max(field.getHeight(), field.getWidth()); ++step) {
                    int enemyHeight = i + di * step;
                    int enemyWidth = j + dj * step;
                    var enemyPiece = field.getCell(enemyHeight, enemyWidth);
                    if (enemyPiece.getType() == turnColor) {
                        break;
                    }
                    res += getEatenPieceValue(enemyHeight, enemyWidth);
                }
            }
        }
        return res;
    }

    private int getMoveCellValue(int i, int j) {
        boolean borderHorizontal = (i == 0 || i + 1 == field.getHeight());
        boolean borderVertical = (j == 0 || j + 1 == field.getWidth());
        if(borderVertical && borderHorizontal) {
            return 8;
        }
        if (borderHorizontal || borderVertical) {
            return 4;
        }
        return 0;
    }

    private int getEatenPieceValue(int i, int j) {
        if (i == 0 || i + 1 == field.getHeight() || j == 0 || j + 1 == field.getWidth()) {
            return 20;
        }
        return 10;
    }

    private boolean checkDirection(int i, int j, int di, int dj, CellType cellType) {
        for (int step = 1; step <= max(field.getHeight(), field.getWidth()); ++step) {
            var neighbourType = field.getCell(i + di * step, j + dj * step).getType();
            if (neighbourType == CellType.NOTHING) {
                break;
            }
            if (neighbourType != cellType) {
                continue;
            }
            return step != 1;
        }
        return false;
    }

}
