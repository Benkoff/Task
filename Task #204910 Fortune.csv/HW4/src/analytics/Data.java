package analytics;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collector;

/**
 * Set of useful reusable tools for analyzing sets of data.
 */
public class Data {

    public Data() {
    }

    /**
     * Finds the minimum value in the passed array. NOTE: The array does not need to be completely populated.
     * All relevant data to be processed starts at index 0.
     * Unused elements will be null and should not be considered in computations.
     * Once the first null is encountered it is assumed remaining elements are unused.
     * @param data Collection of data to find minimum value of.
     * @param <E> Type of data passed. The type must implement the Comparable interface.
     * @return Minimum value in passed data.
     */
    public static <E extends java.lang.Comparable<E>> E minimum(E[] data) {
        return Arrays.stream(data).min(Comparable::compareTo).orElse(null);
    }

    /**
     * Finds the maximum value in the passed array. NOTE: The array does not need to be completely populated.
     * All relevant data to be processed starts at index 0.
     * Unused elements will be null and should not be considered in computations.
     * Once the first null is encountered it is assumed remaining elements are unused.
     * @param data Collection of data to find maximum value of.
     * @param <E> Type of data passed. The type must implement the Comparable interface.
     * @return Maximum value in passed data.
     */
    public static <E extends java.lang.Comparable<E>> E maximum(E[] data) {
        return Arrays.stream(data).max(Comparable::compareTo).orElse(null);
    }

    /**
     * Finds the average of values in the passed array.
     * NOTE: The array does not need to be completely populated. All relevant data to be processed starts at index 0.
     * Unused elements will be null and should not be considered in computations.
     * Once the first null is encountered it is assumed remaining elements are unused.
     * @param data Collection of data to find average of.
     * @param <N> Type of data passed. The type must extend the Number class.
     *           See the assignment supplemental for useful information on the Number class.
     * @return Average of passed data. Regardless of type passed will always return a Double.
     */
    public static <N extends java.lang.Number> java.lang.Double average(N[] data) {
        return Arrays.stream(data).mapToDouble(Number::doubleValue).average().orElse(0.0d);
    }

    /**
     * Finds the population standard deviation of values in the passed array.
     * NOTE: The array does not need to be completely populated.
     * All relevant data to be processed starts at index 0.
     * Unused elements will be null and should not be considered in computations.
     * Once the first null is encountered it is assumed remaining elements are unused.
     * @param data Collection of data to find standard deviation of.
     * @param <N> Type of data passed. The type must extend the Number class.
     *           See the assignment supplemental for useful information on the Number class.
     * @return Population Standard Deviation of passed data. Regardless of type passed will always return a Double.
     */
    public static <N extends java.lang.Number> java.lang.Double standardDeviation(N[] data) {
        return Arrays.stream(data)
                .map(Number::doubleValue)
                .collect(Collector.of(
                        DoubleStatistics::new,
                        DoubleStatistics::accept,
                        DoubleStatistics::combine,
                        DoubleStatistics::getStandardDeviation));
    }

    /**
     * https://stackoverflow.com/questions/36263352/java-streams-standard-deviation
     */
    private static class DoubleStatistics extends DoubleSummaryStatistics {

        private double sumOfSquare = 0.0d;
        private double sumOfSquareCompensation; // Low order bits of sum
        private double simpleSumOfSquare; // Used to compute right sum for non-finite inputs

        @Override
        public void accept(double value) {
            super.accept(value);
            double squareValue = value * value;
            simpleSumOfSquare += squareValue;
            sumOfSquareWithCompensation(squareValue);
        }

        DoubleStatistics combine(DoubleStatistics other) {
            super.combine(other);
            simpleSumOfSquare += other.simpleSumOfSquare;
            sumOfSquareWithCompensation(other.sumOfSquare);
            sumOfSquareWithCompensation(other.sumOfSquareCompensation);
            return this;
        }

        private void sumOfSquareWithCompensation(double value) {
            double tmp = value - sumOfSquareCompensation;
            double velvel = sumOfSquare + tmp; // Little wolf of rounding error
            sumOfSquareCompensation = (velvel - sumOfSquare) - tmp;
            sumOfSquare = velvel;
        }

        double getSumOfSquare() {
            double tmp =  sumOfSquare + sumOfSquareCompensation;
            if (Double.isNaN(tmp) && Double.isInfinite(simpleSumOfSquare)) {
                return simpleSumOfSquare;
            }
            return tmp;
        }

        final double getStandardDeviation() {
            return getCount() > 0 ? Math.sqrt((getSumOfSquare() / getCount()) - Math.pow(getAverage(), 2)) : 0.0d;
        }
    }
}
