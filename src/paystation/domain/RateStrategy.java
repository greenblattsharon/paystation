package paystation.domain;

/**
 * The business logic of the Rate Strategies for the PayStation
 * <p>
 * Responsibilities:
 * <p>
 * 1) Calculate parking time
 */
public interface RateStrategy {

    /**
     * Insert a coin and get the time calculated
     *
     * @param insertedSoFar is an integer value representing the coins that have been added to the paystation
     * @return the time that is calculated
     */
    int calculateParkingTime(int insertedSoFar);
}
