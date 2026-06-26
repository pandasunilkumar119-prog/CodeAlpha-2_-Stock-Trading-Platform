import java.util.ArrayList;
import java.util.Scanner;

public class StockTradingPlatform {

    // Stock Class
    static class Stock {
        String symbol;
        String name;
        double price;

        Stock(String symbol, String name, double price) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
        }
    }

    // Portfolio Class
    static class PortfolioItem {
        Stock stock;
        int quantity;

        PortfolioItem(Stock stock, int quantity) {
            this.stock = stock;
            this.quantity = quantity;
        }

        double getValue() {
            return stock.price * quantity;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Available Stocks
        ArrayList<Stock> market = new ArrayList<>();
        market.add(new Stock("AAPL", "Apple", 180));
        market.add(new Stock("GOOG", "Google", 2700));
        market.add(new Stock("TSLA", "Tesla", 900));
        market.add(new Stock("AMZN", "Amazon", 3400));

        // User Portfolio
        ArrayList<PortfolioItem> portfolio = new ArrayList<>();

        double balance = 10000; // Initial balance

        while (true) {

            System.out.println("\n====== Stock Trading Platform ======");
            System.out.println("Available Balance: $" + balance);
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nMarket Data");
                    System.out.println("-------------------------------------");
                    System.out.printf("%-5s %-10s %-10s%n", "Code", "Company", "Price");

                    for (Stock s : market) {
                        System.out.printf("%-5s %-10s $%.2f%n",
                                s.symbol,
                                s.name,
                                s.price);
                    }
                    break;

                case 2:

                    System.out.print("Enter Stock Symbol: ");
                    String buySymbol = sc.next().toUpperCase();

                    Stock selected = null;

                    for (Stock s : market) {
                        if (s.symbol.equals(buySymbol)) {
                            selected = s;
                            break;
                        }
                    }

                    if (selected == null) {
                        System.out.println("Stock not found.");
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    double cost = qty * selected.price;

                    if (cost > balance) {
                        System.out.println("Insufficient Balance.");
                    } else {

                        balance -= cost;

                        boolean found = false;

                        for (PortfolioItem item : portfolio) {

                            if (item.stock.symbol.equals(selected.symbol)) {

                                item.quantity += qty;
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            portfolio.add(new PortfolioItem(selected, qty));
                        }

                        System.out.println("Stock Purchased Successfully!");
                    }

                    break;

                case 3:

                    System.out.print("Enter Stock Symbol: ");
                    String sellSymbol = sc.next().toUpperCase();

                    boolean sold = false;

                    for (int i = 0; i < portfolio.size(); i++) {

                        PortfolioItem item = portfolio.get(i);

                        if (item.stock.symbol.equals(sellSymbol)) {

                            System.out.print("Enter Quantity: ");
                            int sellQty = sc.nextInt();

                            if (sellQty > item.quantity) {
                                System.out.println("Not enough shares.");
                            } else {

                                item.quantity -= sellQty;

                                balance += sellQty * item.stock.price;

                                if (item.quantity == 0) {
                                    portfolio.remove(i);
                                }

                                System.out.println("Stock Sold Successfully.");
                            }

                            sold = true;
                            break;
                        }
                    }

                    if (!sold) {
                        System.out.println("Stock not found in portfolio.");
                    }

                    break;

                case 4:

                    System.out.println("\n========== Portfolio ==========");

                    if (portfolio.isEmpty()) {

                        System.out.println("Portfolio is Empty.");
                    } else {

                        double total = 0;

                        System.out.printf("%-5s %-10s %-10s %-10s%n",
                                "Code",
                                "Qty",
                                "Price",
                                "Value");

                        for (PortfolioItem item : portfolio) {

                            double value = item.getValue();

                            total += value;

                            System.out.printf("%-5s %-10d $%-9.2f $%.2f%n",
                                    item.stock.symbol,
                                    item.quantity,
                                    item.stock.price,
                                    value);
                        }

                        System.out.println("------------------------------------");
                        System.out.println("Portfolio Value : $" + total);
                        System.out.println("Cash Balance    : $" + balance);
                        System.out.println("Net Worth       : $" + (balance + total));
                    }

                    break;

                case 5:
                    System.out.println("Thank you for using Stock Trading Platform.");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
