import java.util.*;

public class Game {
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private Player opponent;
    private boolean gameOver;
    private Scanner input;

    public Game() {
        p1 = new Player("Player 1");
        p2 = new Player("Player 2");

        currentPlayer = p1;
        opponent = p2;

        gameOver = false;
        
        input = new Scanner(System.in);

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

        for (Ship s : fleet ) {
            boolean placed = false;
            while(!placed) {
                int row = rand.nextInt(board.getRows());
                int column = rand.nextInt(board.getColumns());
                boolean horizontal = rand.nextBoolean();
                Coordinate coord = new Coordinate(row, column);

                if(horizontal) {
                    placed = board.placeShip(coord, s, Direction.HORIZONTAL);
                } else {
                    placed = board.placeShip(coord, s, Direction.VERTICAL);
                }

            }
        }
    }

    public void play() {
        setUpPlayer(p1);
        setUpPlayer(p2);

        while(!gameOver) {
            System.out.println("Player 1's turn");
            currentPlayer.getBoard().printBoard();
            opponent.getBoard().printBoard();




        }
    }

    public void switchTurns() {
        if(currentPlayer == p1) {
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
