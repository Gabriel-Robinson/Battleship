import java.util.*;

public class Board {
    private char[][] grid;
    private ArrayList<Ship> ships;

    public Board() {
        grid = new char[10][10];
        ships = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = '-';
            }
        }
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    public char getCell(int row, int column) {
        if (!inBounds(row, column)) {
            throw new IllegalArgumentException("Out of Bounds");
        } else {
            return grid[row][column];
        }

    }

    public String attack(Coordinate coord) {
        if (coord == null) {
            throw new IllegalArgumentException("Invalid coord");
        }
        int row = coord.getRow();
        int column = coord.getColumn();
        char cell = getCell(row, column);

        if (cell == 'X' || cell == 'O') {
            return "This has already been attacked";
        }

        for (Ship s : ships) {
            if (s.hit(coord)) {
                grid[row][column] = 'X';

                if (s.isSunk()) {
                    return "Hit and Sunk " + s.getType();
                }

                return "Hit";
            }
        }

        grid[row][column] = 'O';
        return "Miss";
    }   

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public boolean placeShip(Coordinate coord, Ship s, Direction direction) {
        if (ships.contains(s)) {
            return false;
        }

        if (coord == null) {
            throw new IllegalArgumentException("Invalid Coord");
        }
        if (s == null) {
            throw new IllegalArgumentException("Invalid Ship");
        }
        if (direction == null) {
            throw new IllegalArgumentException("Invalid direction");
        }
        int row = coord.getRow();
        int column = coord.getColumn();

        if (direction == Direction.HORIZONTAL) {
            for (int i = 0; i < s.getLength(); i++) {
                if (column + i >= grid[0].length) {
                    return false;
                }

                if (grid[row][column + i] != '-') {
                    return false;
                }

            }
            for (int i = 0; i < s.getLength(); i++) {
                s.setPosition(i, new Coordinate(row, column + i));
            }
            for (int i = 0; i < s.getLength(); i++) {
                grid[row][column + i] = 'S';
            }

            ships.add(s);
            return true;
        }

        if (direction == Direction.VERTICAL) {
            for (int i = 0; i < s.getLength(); i++) {
                if (row + i >= grid.length) {
                    return false;
                }

                if (grid[row + i][column] != '-') {
                    return false;
                }
            }
            for (int i = 0; i < s.getLength(); i++) {
                s.setPosition(i, new Coordinate(row + i, column));
            }
            for (int i = 0; i < s.getLength(); i++) {
                grid[row + i][column] = 'S';
            }
            ships.add(s);
            return true;
        }
        return false;

    }

    public void printBoard() {
        System.out.print("  ");
        for (int c = 0; c < grid[0].length; c++) {
            char letter = (char) ( 'A' + c);
            System.out.print(letter + " ");

        }
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean allShipsSunk() {
        for (int i = 0; i < ships.size(); i++) {
            if (!(ships.get(i).isSunk())) {
                return false;
            }
        }
        return true;
    }

    public int getRows() {
        return grid.length;
    }

    public int getColumns() {
        return grid[0].length;
    }
}
