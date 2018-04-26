package one;

public class Don extends Cockroach {

    // this cockroach has 2 dice
    private final Die tenthPercent = new Die(1000);
    private final Die sixSided =  new Die(1, 6);

    public Don(String name, Building building) {
        super(name, building);
    }

    /*
        In any single time step, this is how Don moves:
        (1) With a 0.1% chance, Don flies into the center of the stairwell and ends up falling all the way back to
            the ground floor.
        (2) Otherwise, Don rolls his 6-sided die (since we all know cockroaches love to carry around and use
            dice) and does the following:
        (a) On a 1 or a 2, Don moves down one floor.
        (b) On a roll of a 3, 4, or 5, Don moves up one floor.
        (c) On a roll of a 6, Don re-rolls his six-sided die and moves up the number of floors equal to this
            second roll of the die.
     */
    @Override
    public void takeStep() {
        if (tenthPercent.roll() == 1) {
            setFloor(getBuilding().getGroundFloor());
            incrementStep();

            setLogMessage(String.format("\nDropped down to %3d...", getFloor()));
            return;
        }

        int delta = 0;
        int dieValue = sixSided.roll();
        switch (dieValue) {
            case 1:
            case 2: {
                delta = -1;
                break;
            }
            case 3:
            case 4:
            case 5: {
                delta = 1;
                break;
            }
            case 6: {
                delta = sixSided.roll();
            }
        }
        setFloor(getFloor() + delta);
        incrementStep();

        setLogMessage(String.format("%s =%3d", (delta >= 0 ? "+" + delta : delta), getFloor()));
    }

}
