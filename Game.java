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

    public void play() {
        setUpPlayer(p1);
        setUpPlayer(p2);

        while (!gameOver) {
            System.out.println(currentPlayer.getName() + " turn");
            currentPlayer.getBoard().printBoard();
            opponent.getBoard().printBoard();

            boolean turnDone = false;
            while (!turnDone) {
                Coordinate c = readAttackCoordinate();
                String result = opponent.getBoard().attack(c);
                if (result.equals("This has already been attacked")) {
                    System.out.println(result);
                    continue;
                }
                System.out.println(result);
                turnDone = true;
            }
            boolean sunk = opponent.getBoard().allShipsSunk();
            if (sunk) {
                System.out.println(currentPlayer.getName() + " Has won the game");
                gameOver = true;
            }
            
            if(!gameOver) {
                switchTurns();
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

    private Coordinate readAttackCoordinate() {
        while (true) {
            System.out.println("Please enter an attack coordinate. [A-J][0-9] ");
            String coord = input.nextLine();
            coord = coord.trim().toUpperCase().replace(" ", "");

            if (coord.length() < 2) {
                System.out.println("Invalid, Must be at least 2 characters. Please Re-enter");
                continue;
            }

            char column = coord.charAt(0);

            if (column < 'A' || column > 'J') {
                System.out.println("Invalid, Column must be [A-J]");
                continue;
            }

            int row;
            try {
                row = Integer.parseInt(coord.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Invalid, Row must be [0-9]");
                continue;
            }

            if (row < 0 || row > 9) {
                System.out.println("Invalid, Row must be [0-9]");
                continue;
            }

            int colIndex = column - 'A';
            Coordinate coordinate = new Coordinate(row, colIndex);

            return coordinate;

        }
    }

}
