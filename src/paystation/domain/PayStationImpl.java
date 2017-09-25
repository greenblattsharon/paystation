package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Accept payment;
 * 2) Calculate parking time based on payment;
 * 3) Know earning, parking time bought;
 * 4) Issue receipts;
 * 5) Handle buy and cancel events.
 * <p>
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 * <p>
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {

    private int insertedSoFar;
    private int timeBought;
    private int totalAmount;
    private Map<Integer, Integer> coinsCollected = new HashMap<>();
    private RateStrategy rateStrategy = new LinearRateStrategy();


    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        Boolean isPresent;
        switch (coinValue) {
            case 5:
                int five = 0;
                isPresent = coinsCollected.containsKey(5);
                if ((isPresent != null) & isPresent) {
                    five = coinsCollected.get(5);
                }
                ++five;
                coinsCollected.put(5, five);

                break;
            case 10:
                int ten = 0;
                isPresent = coinsCollected.containsKey(10);
                if ((isPresent != null) & isPresent) {
                    ten = coinsCollected.get(10);
                }
                ten++;
                coinsCollected.put(10, ten);
                break;
            case 25:
                int twentyFive = 0;
                isPresent = coinsCollected.containsKey(25);
                if ((isPresent != null) & isPresent) {
                    twentyFive = coinsCollected.get(25);
                }
                twentyFive++;
                coinsCollected.put(25, twentyFive);
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = rateStrategy.calculateParkingTime(insertedSoFar);
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalAmount += insertedSoFar;
        coinsCollected.clear();
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        Map<Integer, Integer> coinsCollected_return = new HashMap<>();

        if (coinsCollected.containsKey(5)) {
            coinsCollected_return.put(5, coinsCollected.get(5));
        }
        if (coinsCollected.containsKey(10)) {
            coinsCollected_return.put(10, coinsCollected.get(10));
        }
        if (coinsCollected.containsKey(25)) {
            coinsCollected_return.put(25, coinsCollected.get(25));
        }

        reset();
        coinsCollected.clear();
        return coinsCollected_return;
    }

    @Override
    public int empty() {
        int totalAmount_return = totalAmount;
        totalAmount = 0;
        return totalAmount_return;

    }

    @Override
    public void changeRateStrategy(RateStrategy rateStrategy) {
        this.rateStrategy = rateStrategy;
    }

    private void reset() {
        timeBought = insertedSoFar = 0;
    }

}
