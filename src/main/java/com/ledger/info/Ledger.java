package com.ledger.info;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger {

    static ArrayList<TransactionsInfo> transactions = loadTransactions();
    static Scanner consoleInput = new Scanner(System.in);

    public static void addDeposit() {
        System.out.println("Enter Date: YYYY-MM-DD");
        String date = consoleInput.nextLine();
        System.out.println("Enter Time: HH:MM:ss ");
        String time = consoleInput.nextLine();
        System.out.println("Enter vendor: ");
        String vendor = consoleInput.nextLine();
        System.out.println("Enter brief description: ");
        String description = consoleInput.nextLine();
        System.out.println("Enter transaction amount: ");
        double amount = consoleInput.nextDouble();
        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            fileWriter.write(String.format("%s|%s|%s|%s|+%.2f\n", date, time, description, vendor, amount));
            System.out.println("Deposit added ");
        } catch (IOException e) {
            System.out.println("Input not processed... Try again ");
        }
        Main.homescreen();
    }

    public static void makePayment() {
        System.out.println("Enter Date: YYYY-MM-DD");
        String date = consoleInput.nextLine();
        System.out.println("Enter Time: HH-MM-ss ");
        String time = consoleInput.nextLine();
        System.out.println("Enter vendor: ");
        String vendor = consoleInput.nextLine();
        System.out.println("Enter brief description: ");
        String description = consoleInput.nextLine();
        System.out.println("Enter transaction amount: ");
        double amount = consoleInput.nextDouble();

        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            fileWriter.write(String.format("%s|%s|%s|%s|-%.2f\n", date, time, description, vendor, amount));
            fileWriter.close();
            System.out.println("Payment added ");
        } catch (IOException e) {
            System.out.println("Input not processed... Try again ");
        }
        Main.homescreen();
    }

    public static ArrayList<TransactionsInfo> loadTransactions() {
        ArrayList<TransactionsInfo> transactions = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] details = input.split("\\|");
                LocalDate date = LocalDate.parse(details[0]);
                LocalTime time = LocalTime.parse(details[1]);
                String description = details[2];
                String vendor = details[3];
                double amount = Double.parseDouble(details[4]);

                TransactionsInfo transaction = new TransactionsInfo(date, time, description, vendor, amount);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("File not available ");
            System.exit(0);
        }
        Comparator<TransactionsInfo> compareByDate = Comparator.comparing(TransactionsInfo::getDate).reversed();
        Comparator<TransactionsInfo> compareByTime = Comparator.comparing(TransactionsInfo::getTime).reversed();
        Comparator<TransactionsInfo> compareByDateTime = compareByDate.thenComparing(compareByTime);
        transactions.sort(compareByDateTime);
        return transactions;

    }

    public static void showEntries() {
        System.out.println("\n-------ALL TRANSACTIONS-------- \n");
        for (TransactionsInfo i : transactions) {
            System.out.printf("%s %s %s %s $%.2f\n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
        }
    }

    public static void showDeposits() {
        System.out.println("\n--------LIST OF DEPOSITS------ \n");
        for (TransactionsInfo d : transactions) {
            if (d.getAmount() > 0) {
                System.out.printf("%s %s %s %s $%.2f\n", d.getDate(), d.getTime(), d.getDescription(), d.getVendor(), d.getAmount());
            }
        }
    }

    public static void showPayments() {
        System.out.println("\n--------LIST OF PAYMENTS------- \n");
        for (TransactionsInfo p : transactions) {
            if (p.getAmount() < 0) {
                System.out.printf("%s %s %s %s $%.2f\n", p.getDate(), p.getTime(), p.getDescription(), p.getVendor(), p.getAmount());
            }
        }
    }

    public static void showReports() {
        System.out.println("Select Report Structure");
        System.out.println("""
                [1] - Month To Date
                [2] - Previous Month
                [3] - Year To Date
                [4] - Previous Year
                [5] - Search by Vendor
                [0] - Back
                """);
        System.out.print("Your choice: ");
        int choice = consoleInput.nextInt();
        consoleInput.nextLine();
        switch (choice) {
            case 1:
                monthToDate();
                break;

            case 2:
                previousMonth();
                break;
            case 3:
                yearToDate();
            case 4:
                previousYear();
            case 5:
                searchByVendor();
            case 0: //go back to report screen
            default:
                System.out.println("Invalid option... Try again");
        }

    }

    private static void searchByVendor() {
    }

    private static void previousYear() {

    }

    private static void yearToDate() {

    }

    private static void previousMonth() {

    }

    private static void monthToDate() {

    }
}


