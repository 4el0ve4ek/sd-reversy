package game;

import game.cell.CellType;
import game.score.Score;

import java.util.Scanner;

public class Session {

    private final Score sessionScores = new Score();

    public Session() {

    }

    public boolean run() {
        runGame();
        return askForContinue();
    }

    private void runGame() {
        Game game = new Game();
        game.init();
        while (!game.isFinished()) {
            game.processGameLoop();
        }
        game.finish();

        Score gameScore = game.getScore();
        printGameScore(gameScore);
        sessionScores.extend(gameScore);
        printSessionScore();
    }

    private boolean askForContinue() {
        System.out.println("Do you want play more? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String ans = scanner.next();
        return ans.equals("y");
    }

    public void printGameScore(Score score) {
        for(CellType cellType : score.keys() ){
            System.out.printf("game score for %s is %d\n", cellType.getLongName(), score.get(cellType));
        }
        var winner = score.getWinner();
        System.out.printf("%s wins this match!\n", winner.getLongName());
    }

    public void printSessionScore() {
        for(var cellType : sessionScores.keys() )
            System.out.printf("best score for %s is %d\n", cellType.getLongName(), sessionScores.get(cellType));
    }
}
