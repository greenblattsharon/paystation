package paystation.domain;

import java.util.Map;
import java.util.Scanner;

public class MainDisplay {

    public static void main(String[] args) throws IllegalCoinException {
        PayStation ps = new PayStationImpl();
        MainDisplay md = new MainDisplay();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the PayStation!");

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Please add payment:");
                    int coin = scanner.nextInt();
                    ps.addPayment(coin);
                    break;

                case 2:
                    int timeBought = ps.readDisplay();
                    System.out.println("The time that can be purchased is: " + timeBought + " minutes");
                    break;

                case 3:
                    Receipt receipt = ps.buy();
                    System.out.println("The time that was purchased is: " + receipt.value() + " minutes");
                    break;

                case 4:
                    Map<Integer, Integer> coinsReturned = ps.cancel();

                    System.out.println("The following coins were returned: ");
                    System.out.println("$.05 coins: " + coinsReturned.get(5));
                    System.out.println("$.10 coins: " + coinsReturned.get(10));
                    System.out.println("$.25 coins: " + coinsReturned.get(25));
                    break;

                case 5:
                    System.out.println("Are you authorized to do this? (Yes/No)");
                    String confirmation = scanner.next();
                    if (confirmation.equalsIgnoreCase("Yes")) {
                        System.out.println("Which rate strategy do you want to implement?");
                        System.out.println("1) Linear Rate Strategy");
                        System.out.println("2) Progressive Rate Strategy");
                        System.out.println("3) Alternating Rate Strategy");

                        int rate_strategy_change = scanner.nextInt();
                        switch (rate_strategy_change) {
                            case 1:
                                ps.changeRateStrategy(new LinearRateStrategy());
                                System.out.println("Rate Strategy has been changed to Linear Rate Strategy");
                                break;

                            case 2:
                                ps.changeRateStrategy(new ProgressiveRateStrategy());
                                System.out.println("Rate Strategy has been changed to Progressive Rate Strategy");
                                break;

                            case 3:
                                ps.changeRateStrategy(new AlternatingRateStrategy());
                                System.out.println("Rate Strategy has been changed to Alternating Rate Strategy");
                                break;

                            default:
                                System.out.println("This is not an option. Please try again.");
                        }
                    } else {
                        System.out.println("You are not authorized. Please try again.");
                    }
                    break;

                default:
                    System.out.println("That is not an option. Please try again.");
                    break;
            }
        }
    }

    public static void displayMenu() {
        System.out.println("");
        System.out.println("1) Deposit Coins");
        System.out.println("2) Display");
        System.out.println("3) Buy Ticket");
        System.out.println("4) Cancel");
        System.out.println("5) Change Rate Strategy");

        System.out.println("\nPlease enter the number of what choice you would like:");
    }

}
