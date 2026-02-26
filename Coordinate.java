public class Coordinate {
    private int row;
    private int column;

    public Coordinate(int row, int column) {
        if(row < 0 || row >= 10 || column < 0 || column >= 10) {
            throw new IllegalArgumentException("Coordinate out of bounds");
        }
        this.column = column;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String toString() {
        char letter = (char) ('A' + column);
        return letter + "" + row;    
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        Coordinate other = (Coordinate) obj;
        return this.row == other.row && this.column == other.column;
    }
}
