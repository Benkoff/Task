package one;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

public class RW {
    public static void main(String[] args) {

        Building empireStateBulding = new Building(1, 102);

        // An explanation why there is no Die field present at parent class
        Cockroach dead = new Cockroach("Dead Cockroach", empireStateBulding) {
            @Override
            public void takeStep() {
                // Dead don't move so there is no need in dice
            }
        };

        Cockroach don = new Don("Don", empireStateBulding);
        Cockroach bella = new Bella("Bella", empireStateBulding);

        runForMaxFloor(don,2000, 100);
        runForMaxFloor(bella,2000, 100);

        runForStepsToReachTop(don, 2000);
        runForStepsToReachTop(bella, 2000);

        runForCooccurencies(don, bella, 2000, 2000);
    }

    // @NotNull is declaring that a variable cannot hold null value.
    private static void runForMaxFloor(@NotNull Cockroach runner, int experiments, int steps) {
        int topFloor = runner.getBuilding().getTopFloor();
        int[] maxFloorReached = new int[experiments];

        System.out.println("\n----------\n" + runner.getName() + "\n----------");
        for (int i = 0; i < experiments; i++) {
            runner.start();
            maxFloorReached[i] = 0;
            int floor = 1;

            for (int s = 0; s < steps; s++) {
                runner.takeStep();
                floor = runner.getFloor();

                if (floor > maxFloorReached[i]) {
                    maxFloorReached[i] = floor;
                }
                if (floor == topFloor) {
                    break;
                }
            }

        }
        double averageValue = Arrays.stream(maxFloorReached).average().orElse(1.0);

        System.out.println(experiments + " experiments, walking " + steps + " steps, the maximum height achieved");
        System.out.println("has average value: " + averageValue + ".");
    }

    private static void runForStepsToReachTop(@NotNull Cockroach runner, int experiments) {
        int topFloor = runner.getBuilding().getTopFloor();
        int[] stepsToReach = new int[experiments];

        System.out.println("\n----------\n" + runner.getName() + "\n----------");
        for (int i = 0; i < experiments; i++) {
            runner.start();
            stepsToReach[i] = 0;

            for (int s = 0; ; s++) {
                runner.takeStep();
                stepsToReach[i] = runner.getStep();

                if (runner.getFloor() == topFloor) {
                    break;
                }
            }

        }
        double averageValue = Arrays.stream(stepsToReach).average().orElse(1.0);

        System.out.println(experiments + " experiments, walking steps to reach the top floor for ");
        System.out.println("the first time has average value: " + averageValue + ".");
    }

    private static void runForCooccurencies(@NotNull Cockroach first, @NotNull Cockroach second,
                                            int experiments, int runs) {
        int[] cooccurencies = new int[experiments];

        System.out.println("\n----------\n" + first.getName() + ", " + second.getName() + "\n----------");
        for (int i = 0; i < experiments; i++) {
            first.start();
            second.start();
            cooccurencies[i] = 0;

            for (int s = 0; s < runs; s++) {
                first.takeStep();
                second.takeStep();

                if (first.getFloor() == second.getFloor()) {
                    cooccurencies[i]++;
                }
            }

        }
        double averageValue = Arrays.stream(cooccurencies).average().orElse(0.0);

        System.out.println(experiments + " experiments, walking "
                + runs + " steps to count the number of times that ");
        System.out.println("they are on the same floor has average value: " + averageValue + ".");
    }
}
