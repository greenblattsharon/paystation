package paystation.domain;

/**
 * The linear rate strategy gives you 2 minutes for every 5 cents entered or $1.50 per hour
 */

public class LinearRateStrategy implements RateStrategy {

    public LinearRateStrategy() {
    }

    @Override
    public int calculateParkingTime(int insertedSoFar) {
        return insertedSoFar / 5 * 2;
    }
}
