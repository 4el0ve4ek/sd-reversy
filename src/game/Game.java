package game;

import game.cell.CellType;
import game.field.*;
import game.player.*;
import game.score.Score;

import java.util.*;

import static java.lang.Math.max;


public class Game {
    private static final int width = 8;
    private static final int height = 8;
    private Field field;
    private final List<Player> players = new ArrayList<>();
    int playerTurn;

    public Game() {

    }

    public void init() {
        field = new EmptyField(height, width);
        field.getCell(height / 2, width / 2).setType(CellType.WHITE);
        field.getCell(height / 2 - 1, width / 2).setType(CellType.BLACK);
        field.getCell(height / 2, width / 2 - 1).setType(CellType.BLACK);
        field.getCell(height / 2 - 1, width / 2 - 1).setType(CellType.WHITE);

        players.add(selectPlayer(CellType.BLACK));
        players.add(selectPlayer(CellType.WHITE));
        playerTurn = 0;
    }

    public Player selectPlayer(CellType playerColor) {
        System.out.printf("Who will be playing as %s player?\n", playerColor.getLongName());
        System.out.println("1) Real player");
        System.out.println("2) Computer");
        Scanner scanner = new Scanner(System.in);
        int res = 0;
        boolean parsed = false;
        while(!parsed) {
            try {
                res = scanner.nextInt();
                if (res < 1 || res > 2) {
                    System.out.println("Impossible value, please, try again:");
                    continue;
                }
                parsed = true;
            } catch (Exception e) {
                System.out.println("Exception while parse, try again:");
            }
        }
        return buildPlayer(res, playerColor);
    }

    private Player buildPlayer(int id, CellType playerColor) {
        return switch (id) {
            case 1 -> new RealPlayer(playerColor.getLongName() + " player", playerColor);
            case 2 -> new Bot(field, playerColor);
            default -> throw new IllegalArgumentException("unknown type of player");
        };
    }
    public void processGameLoop() {

        var currentPlayer = players.get(playerTurn);
        var possibleMoves = getPossibleMoves(currentPlayer.getTurnColor());

        System.out.print("\n\n\n\n");
        this.addPossibleMoves(possibleMoves);
        field.print();
        this.clearCells(possibleMoves);
        System.out.println();

        if (!possibleMoves.isEmpty()) {
            Move nextMove = currentPlayer.chooseMove(possibleMoves);
            this.makeMove(nextMove, currentPlayer.getTurnColor());
        } else {
            System.out.println("No possible moves -- skip this turn");
        }

        playerTurn = (playerTurn + 1) % players.size();
    }

    public boolean isFinished() {
        return players.stream().allMatch(
                (player) -> (getPossibleMoves(player.getTurnColor()).size() == 0)
        );
    }

    public void finish() {
        field.print();
    }

    public Score getScore() {
        var resultScores = new HashMap<CellType, Integer>();
        for (var player : players) {
            resultScores.put(player.getTurnColor(), countCells(player.getTurnColor()));
        }
        return new Score(resultScores);
    }

    private void addPossibleMoves(List<Move> moves) {
        for (var move : moves) {
            field.getCell(move).setType(CellType.POSSIBLE);
        }
    }

    private void clearCells(List<Move> moves) {
        for (var move : moves) {
            field.getCell(move).setType(CellType.NOTHING);
        }
    }

    private void makeMove(Move move, CellType cellType) {
        field.getCell(move).setType(cellType);
        var i = move.i();
        var j = move.j();
        for (int di = -1; di <= 1; ++di) {
            for (int dj = -1; dj <= 1; ++dj) {
                if (di == dj && di == 0) {
                    continue;
                }
                if (!checkDirection(i, j, di, dj, cellType)) {
                    continue;
                }
                for (int step = 1; step <= max(width, height); ++step) {
                    var enemyPiece = field.getCell(i + di * step, j + dj * step);
                    if (enemyPiece.getType() == cellType) {
                        break;
                    }
                    enemyPiece.setType(cellType);
                }
            }
        }
    }

    private boolean checkDirection(int i, int j, int di, int dj, CellType cellType) {
        for (int step = 1; step <= max(width, height); ++step) {
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


    private List<Move> getPossibleMoves(CellType type) {
        List<Move> res = new ArrayList<>();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                var move = new Move(i, j);
                if (!isValidMove(move, type)) {
                    continue;
                }
                res.add(move);
            }
        }
        return res;
    }

    private boolean isValidMove(Move move, CellType playerType) {
        if (field.getCell(move).getType() != CellType.NOTHING) {
            return false;
        }
        var i = move.i();
        var j = move.j();
        for (int di = -1; di <= 1; ++di) {
            for (int dj = -1; dj <= 1; ++dj) {
                if (di == dj && di == 0) {
                    continue;
                }
                if (checkDirection(i, j, di, dj, playerType)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int countCells(CellType cellType) {
        int res = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field.getCell(i, j).getType() == cellType) {
                    ++res;
                }
            }
        }
        return res;
    }
}
