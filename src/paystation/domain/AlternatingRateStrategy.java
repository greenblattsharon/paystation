package paystation.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * The alternating rate strategy implements the following rules:
 * <p>
 * 1) On the weekends, the linear rate applies
 * 2) On the weekdays, the progressive rate applies
 */

public class AlternatingRateStrategy implements RateStrategy {


    public AlternatingRateStrategy() {
    }

    @Override
    public int calculateParkingTime(int insertedSoFar) {
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int day_of_the_week = calendar.get(Calendar.DAY_OF_WEEK);

        if (day_of_the_week == 1 || day_of_the_week == 7) {
            LinearRateStrategy linearRateStrategy = new LinearRateStrategy();
            return linearRateStrategy.calculateParkingTime(insertedSoFar);
        } else {
            ProgressiveRateStrategy progressiveRateStrategy = new ProgressiveRateStrategy();
            return progressiveRateStrategy.calculateParkingTime(insertedSoFar);
        }
    }
}
