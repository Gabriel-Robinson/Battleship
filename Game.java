import java.util.*;

public class Game {
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private Player opponent;
    private boolean gameOver;

    public Game() {
        p1 = new Player("Player 1");
        p2 = new Player("Player 2");

        setUpPlayer(p1);
        setUpPlayer(p2);

        currentPlayer = p1;
        opponent = p2;

        gameOver = false;
    }

    public TurnResult playTurn(Coordinate coord) {
        // Player Attacks
        String playerResult = opponent.getBoard().attack(coord);

        if(playerResult.contains("already")) {
            return new TurnResult(playerResult, "", false, -1, -1);
        }

        if(opponent.getBoard().allShipsSunk()) {
            gameOver = true;
            return new TurnResult(playerResult, "", true, -1, -1);
        }

        // Ai Attack
        Random rand = new Random();
        int row;
        int col;
        String aiResult;

        do {
            row = rand.nextInt(10);
            col = rand.nextInt(10);
            aiResult = currentPlayer.getBoard().attack(new Coordinate(row, col));
        } while (aiResult.contains("already"));

        if(currentPlayer.getBoard().allShipsSunk()) {
            gameOver = true;
        }

        return new TurnResult(playerResult, aiResult, gameOver, row, col);
    }

    public Player getOpponent() {
        return opponent;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setUpPlayer(Player p) {
        Random rand = new Random();

        Ship Carrier = new Ship("Carrier", 5);
        Ship BattleShip = new Ship("BattleShip", 4);
        Ship Cruiser = new Ship("Cruiser", 3);
        Ship Submarine = new Ship("Submarine", 3);
        Ship Destroyer = new Ship("Destroyer", 2);

        ArrayList<Ship> fleet = new ArrayList<>();
        fleet.add(Carrier);
        fleet.add(BattleShip);
        fleet.add(Cruiser);
        fleet.add(Submarine);
        fleet.add(Destroyer);

        Board board = p.getBoard();

        for (Ship s : fleet) {
            boolean placed = false;
            while (!placed) {
                int row = rand.nextInt(board.getRows());
                int column = rand.nextInt(board.getColumns());
                boolean horizontal = rand.nextBoolean();
                Coordinate coord = new Coordinate(row, column);

                if (horizontal) {
                    placed = board.placeShip(coord, s, Direction.HORIZONTAL);
                } else {
                    placed = board.placeShip(coord, s, Direction.VERTICAL);
                }

            }
        }
    }

    public void switchTurns() {
        if (currentPlayer == p1) {
            currentPlayer = p2;
            opponent = p1;
        } else {
            currentPlayer = p1;
            opponent = p2;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

}

class TurnResult {
    public String playerResult;
    public String aiResult;
    public boolean gameOver;
    public int aiRow;
    public int aiCol;

    public TurnResult(String p, String a, boolean g, int r, int c) {
        playerResult = p;
        aiResult = a;
        gameOver = g;
        aiRow = r;
        aiCol = c;
    }

}
