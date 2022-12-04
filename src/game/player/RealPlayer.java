package game.player;

import game.cell.CellType;

import java.util.List;
import java.util.Scanner;

public class RealPlayer implements Player {
    private final String name;

    private final CellType turnColor;

    public RealPlayer(String name, CellType turnColor) {
        this.name = name;
        this.turnColor = turnColor;
    }

    public Move chooseMove(List<Move> possibleMoves) {
        if (possibleMoves.size() == 0) {
            return null;
        }
        System.out.printf("%s, it's your turn, choose cell:\n", name);
        Move resultMove = null;
        while (resultMove == null) {
            var nextMove = readUserInput();
            if (nextMove == null) {
                System.out.println("Exception while parse, try again:");
                continue;
            }
            if (!possibleMoves.contains(nextMove)) {
                System.out.println("Impossible move, try again:");
                continue;
            }
            resultMove = nextMove;

        }
        return resultMove;
    }

    public Move readUserInput() {
        Scanner input = new Scanner(System.in);
        int i, j;
        try {
            i = input.nextInt();
            j = input.nextInt();
        } catch (Exception e) {
            return null;
        }
        return new Move(i, j);
    }

    public CellType getTurnColor() {
        return turnColor;
    }

}
