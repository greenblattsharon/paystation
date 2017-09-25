package paystation.domain;

/**
 * The progressive rate strategy follows these rules:
 * <p>
 * 1) First hour: $1.50
 * 2) Second hour: $2.00
 * 3) Third hour and more: $3.00 per hour
 */

public class ProgressiveRateStrategy implements RateStrategy {

    public ProgressiveRateStrategy() {
    }

    @Override
    public int calculateParkingTime(int insertedSoFar) {

        if (insertedSoFar <= 150) {
            return insertedSoFar / 5 * 2;
        } else if (insertedSoFar > 150 & insertedSoFar <= 350) {
            return (((insertedSoFar - 150) / 10) * 3) + 60;
        } else {
            return ((insertedSoFar - 350) / 5) + 120;
        }
    }
}
