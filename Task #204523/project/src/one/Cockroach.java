package one;

import java.util.Objects;

public abstract class Cockroach {
    private String name;
    private int step;
    private int floor;
    private Building building;

    // test purpose only
    private String logMessage;

    // cockroaches don't move outside buildings, so we need one
    public Cockroach(String name, Building building) {
        this.name = name;
        this.building = building;
    }

    public abstract void takeStep();

    public void start() {
        step = 0;
        floor = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStep() {
        return step;
    }

    public void incrementStep() {
        step++;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        if (floor < building.getGroundFloor()) {
            this.floor = building.getGroundFloor();
        } else if(floor > building.getTopFloor()) {
            this.floor = building.getTopFloor();
        } else {
            this.floor = floor;
        }
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    protected void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cockroach cockroach = (Cockroach) o;
        return Objects.equals(name, cockroach.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}

