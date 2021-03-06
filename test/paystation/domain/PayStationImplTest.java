/**
 * Testcases for the Pay Station system.
 * <p>
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 * <p>
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

import java.util.Map;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }

    /**
     * Call to empty returns the total amount entered
     */
    @Test
    public void shouldReturnTotalAfterEmpty() throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(5);
        ps.addPayment(25);
        ps.buy();
        int totalAmount = ps.empty();
        assertEquals("Empty should return total amount entered", 40, totalAmount);
    }

    /**
     * Cancelled entry does not add to the amount returned by empty
     */
    @Test
    public void shouldNotAddToTotalAmountWhenCancelledEntry() throws IllegalCoinException {
        ps.addPayment(10);
        ps.buy();
        ps.addPayment(5);
        ps.cancel();
        int totalAmount = ps.empty();
        assertEquals("Cancelled Entry does not add to the amount returned by empty", 10, totalAmount);
    }

    /**
     * Call to empty resets the total to zero
     */
    @Test
    public void shouldResetTotalToZeroAfterEmptyCalled() throws IllegalCoinException {
        ps.addPayment(10);
        ps.buy();
        ps.empty();
        int totalAmount = ps.empty();
        assertEquals("Cancelled Entry does not add to the amount returned by empty", 0, totalAmount);
    }

    /**
     * Call to cancel returns a map containing one coin entered
     */
    @Test
    public void shouldReturnOneCoinInMapAfterCancel() throws IllegalCoinException {
        ps.addPayment(5);
        Map<Integer, Integer> oneCoin = ps.cancel();
        int size = oneCoin.size();
        assertEquals("There should be one key in the map", 1, size);
        assertEquals("The one key will have one coin in the map", (Integer) 1, oneCoin.get(5));

    }

    /**
     * Call to cancel returns a map containing a mixture of coins entered
     */
    @Test
    public void shouldReturnMixtureCoinsInMapAfterCancel() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);
        Map<Integer, Integer> mixtureCoins = ps.cancel();
        int size = mixtureCoins.size();
        assertEquals("There should be 3 keys in the map", 3, size);
        assertEquals("There should be two 5 coins in the map", (Integer) 2, mixtureCoins.get(5));
        assertEquals("There should be two 10 coins in the map", (Integer) 2, mixtureCoins.get(10));
        assertEquals("There should be two 25 coins in the map", (Integer) 2, mixtureCoins.get(25));
    }

    /**
     * Call to cancel returns a map that does not contain a key for a coin not entered
     */
    @Test
    public void shouldNotReturnKeyForCoinNotEnteredAfterCancel() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        Map<Integer, Integer> mixtureCoins = ps.cancel();
        int size = mixtureCoins.size();
        assertEquals("There should be 2 keys in the map", 2, size);
        assertEquals("There should not be a key for coin of 25", false, mixtureCoins.containsKey(25));
    }

    /**
     * Call to cancel clears the map
     */
    @Test
    public void shouldClearMapAfterCancel() throws IllegalCoinException {
        ps.addPayment(5);
        ps.cancel();
        Map<Integer, Integer> cleanMap = ps.cancel();
        int size = cleanMap.size();
        assertEquals("There should be no keys in the map", 0, size);
    }

    /**
     * Call to buy clears the map
     */
    @Test
    public void shouldClearMapAfterBuy() throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.buy();
        Map<Integer, Integer> cleanMap = ps.cancel();
        int size = cleanMap.size();
        assertEquals("There should be no keys in the map", 0, size);
    }
}
