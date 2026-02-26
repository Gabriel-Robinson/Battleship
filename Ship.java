public class Ship {
    private int length;
    private String type;
    private Coordinate[] position;
    private boolean[] hits;

    public Ship(String type, int length) {
        this.length = length;
        this.type = type;
        this.position = new Coordinate[length];
        this.hits = new boolean[length];
    }

    public void setPosition(int index, Coordinate coord) {
        if(index >= position.length || index < 0) {
            throw new IllegalArgumentException("Invalid posistion index: " + index);
        }

        position[index] = coord;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public boolean isSunk() {
    
        for(int i = 0; i < this.hits.length; i++) {
            if(this.hits[i] == false) {
                return false;
            }
        }    
        return true; 
    }

    public boolean hit(Coordinate coord) {
        if(coord == null) {
            throw new IllegalArgumentException( "Invalid coord");
        }

        for(int i = 0; i < position.length; i++) {
            if(coord.equals(position[i])) {
                hits[i] = true;
                return true; // Hit
            }
        }
        return false; //Miss
    }

    
}
