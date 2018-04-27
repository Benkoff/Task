package tests;

import analytics.Data;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataTest {
    private static final Double[] DOUBLES = new Double[]{0.1, 0.2, 0.3};

    @Test
    public void minimumTest() {
        Double actual = Data.minimum(DOUBLES);
        Double expected = 0.1d;

        assertEquals("Problem in Data minimum", expected, actual);
    }

    @Test
    public void maximumTest() {
        Double actual = Data.maximum(DOUBLES);
        Double expected = 0.3d;

        assertEquals("Problem in Data maximum", expected, actual);
    }

    @Test
    public void averageTest() {
        Double actual = Data.average(DOUBLES);
        Double expected = 0.2d;

        assertEquals("Problem in Data average", expected, actual, 0.000000000000001);
    }

    @Test
    public void standardDeviationTest() {
        Double actual = Data.standardDeviation(new Integer[]{2, 4, 4, 4, 5, 5, 7, 9});
        Double expected = 2d;

        assertEquals("Problem in Data standardDeviation", expected, actual);
    }
}
