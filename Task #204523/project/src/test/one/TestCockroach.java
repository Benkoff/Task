package test.one;

import one.Bella;
import one.Building;
import one.Cockroach;
import one.Don;

public class TestCockroach {
    public static void main(String[] args) {
        Building esb = new Building(1, 102);
        Cockroach don = new Don("Don", esb);
        Cockroach bella = new Bella("Bella", esb);

        test(don);
        test(bella);
    }

    private static void test(Cockroach cockroach) {
        System.out.println("Testing " + cockroach.getName());
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 20; j++) {
                cockroach.takeStep();
                System.out.print("" + cockroach.getLogMessage() + " ");
            }
            System.out.println("steps: " + cockroach.getStep());
        }
    }
}
