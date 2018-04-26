package one;

public class Bella extends Cockroach {
    // there is only one die for Bella
    private final Die sixSided =  new Die(1, 6);
    // previous step led to floor 86
    private boolean isViewing86 = false;

    public Bella(String name, Building building) {
        super(name, building);
    }

    /*
        (1) If Bella is pausing for a view on the 86th floor, she doesn’t move at all for
            one step, and in the next step will roll her die (after all, she is a cockroach
            too!) to (possibly) move.
        (2) Otherwise, Bella rolls her 6-sided die and does the following:
            (a) On a 1, 2, or 3, she moves down one floor.
            (b) On a 4, she moves up 2 floors.
            (c) On a 5, she moves up 3 floors.
            (d) On a roll of a 6, Bella doesn't move.
            (e) However, if Bella moves onto (or tries to move through) the 86th floor,
                she will stop on floor 86, and will pause for a view on the next step. (So
                if, for example, Bella was on floor 85 and rolled a 4, she would stop on
                floor 86 for a view on the next step, instead of progressing to floor 87.)
        If Bella rolled a 6, and was already on floor 86, she will still pause for a view on the next step.
        (Note that this means Bella will spend two time steps on floor 86, one for the step when she
        rolled a 6, and another when she pauses for the view, as described in (1) above.)
     */
    @Override
    public void takeStep() {
        int delta = 0;
        int dieValue = sixSided.roll();
        switch (dieValue) {
            case 1:
            case 2:
            case 3: {
                delta = -1;
                break;
            }
            case 4: {
                delta = 2;
                break;
            }
            case 5: {
                delta = 3;
                break;
            }
            case 6: {
                delta = 0;
            }
        }
        int thisFloor = getFloor();
        int nextFloor = getFloor() + delta;

        if (isViewing86) {
            isViewing86 = false;
            setLogMessage("viewing");
        } else {
            setFloor(nextFloor);
            if (getFloor() == 86 || (thisFloor < 86 && 86 < nextFloor) || (thisFloor > 86 && 86 > nextFloor)) {
                isViewing86 = true;
                setFloor(86);
            }
            setLogMessage(String.format("%s =%3d", (delta >= 0 ? "+" + delta : delta), getFloor()));
        }

        incrementStep();
    }
}
