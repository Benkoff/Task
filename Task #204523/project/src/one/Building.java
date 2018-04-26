package one;

public class Building {
    private int groundFloor;
    private int topFloor;

    public Building(int groundFloor, int topFloor) {
        this.groundFloor = groundFloor;
        this.topFloor = topFloor;
    }

    public int getGroundFloor() {
        return groundFloor;
    }

    public int getTopFloor() {
        return topFloor;
    }
}
