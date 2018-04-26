package one;

import java.util.concurrent.ThreadLocalRandom;

public class Die {
    private int value;
    private int min;
    private int max;

    public Die(int max) {
        min = 1;
        this.max = max;
    }

    public Die(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int roll() {
        return value = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int getValue() {
        return value;
    }
}
