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
        System.out.print("Enter vendor: ");
        String vendor = consoleInput.nextLine();
        System.out.print("Enter brief description: ");
        String description = consoleInput.nextLine();
        System.out.print("Enter transaction amount: ");
        double amount = consoleInput.nextDouble();
        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            fileWriter.write(String.format("%s|%s|%s|%s|%.2f\n", LocalDate.now(), LocalTime.now(), description, vendor, amount));
            System.out.println("Deposit added ");
        } catch (IOException e) {
            System.out.println("Input not processed... Try again ");
        }
        Main.homescreen();
    }

    public static void makePayment() {
        System.out.println("Enter vendor: ");
        String vendor = consoleInput.nextLine();
        System.out.println("Enter brief description: ");
        String description = consoleInput.nextLine();
        System.out.println("Enter transaction amount: ");
        double amount = consoleInput.nextDouble();

        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            fileWriter.write(String.format("%s|%s|%s|%s|-%.2f\n", LocalDate.now(), LocalTime.now(), description, vendor, amount));
            fileWriter.close();
            System.out.println("PAYMENT ADDED... ");
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
            System.out.println("FILE NOT AVAILABLE ");
            System.exit(0);
        }
        Comparator<TransactionsInfo> compareByDate = Comparator.comparing(TransactionsInfo::getDate).reversed();
        Comparator<TransactionsInfo> compareByTime = Comparator.comparing(TransactionsInfo::getTime).reversed();
        Comparator<TransactionsInfo> compareByDateTime = compareByDate.thenComparing(compareByTime);
        transactions.sort(compareByDateTime);
        return transactions;

    }

    public static void showEntries() {
        System.out.println("\n-------------------------ALL TRANSACTIONS-------------------------\n");
        printHeader();
        for (TransactionsInfo i : transactions) {
            System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
        }
    }

    public static void showDeposits() {
        System.out.println("\n-------------------------LIST OF DEPOSITS-------------------------\n");
        printHeader();
        for (TransactionsInfo d : transactions) {
            if (d.getAmount() > 0) {
                System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", d.getDate(), d.getTime(), d.getDescription(), d.getVendor(), d.getAmount());
            }
        }
    }

    public static void showPayments() {
        System.out.println("\n-------------------------LIST OF PAYMENTS-------------------------\n");
        printHeader();
        for (TransactionsInfo p : transactions) {
            if (p.getAmount() < 0) {
                System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", p.getDate(), p.getTime(), p.getDescription(), p.getVendor(), p.getAmount());
            }
        }
    }

    public static void showReports() {
        boolean done = false;
        while(!done) {
            System.out.println("SELECT REPORT TYPE");
            System.out.println("""
                    [1] - MONTH TO DATE
                    [2] - PREVIOUS MONTH
                    [3] - YEAR TO DATE
                    [4] - PREVIOUS YEAR
                    [5] - SEARCH BY VENDOR
                    [0] - BACK
                    """);
            System.out.print("Your choice: ");
            int choice = consoleInput.nextInt();
            consoleInput.nextLine();
            switch (choice) {
                case 1 -> monthToDate();
                case 2 -> previousMonth();
                case 3 -> yearToDate();
                case 4 -> previousYear();
                case 5 -> searchByVendor();
                case 0 -> showReports();
                default -> {
                    System.out.println("INVALID OPTION... TRY AGAIN ");
                    done = true;
                }
            }
        }
    }
    private static void monthToDate() {
        LocalDate today = LocalDate.now();
        System.out.println("\n-------------------------MONTH TO DATE-------------------------\n");
        for (TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if (transactionDate.getMonth() == today.getMonth() && transactionDate.getYear() == today.getYear()) {
                System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", i.getDate(), i.getTime(), i.getVendor(), i.getDescription(), i.getAmount());
            }
        }
    }
    private static void yearToDate() {
        LocalDate today = LocalDate.now();
        System.out.println("\n-------------------------YEAR TO DATE REPORT-------------------------\n");
        printHeader();
        for(TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if(transactionDate.getYear()== today.getYear()){
                System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", i.getDate(), i.getTime(), i.getVendor(), i.getDescription(),i.getAmount());
            }
        }
    }
    private static void previousYear() {
        LocalDate today = LocalDate.now();
        int previousYear = today.minusYears(1).getYear();
        System.out.println("\n-------------------------PREVIOUS YEAR REPORT-------------------------\n");
        printHeader();
        for (TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if (transactionDate.getYear() == previousYear) {
                System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", i.getDate(), i.getTime(), i.getVendor(), i.getDescription(), i.getAmount());

            }
        }
    }
    private static void previousMonth() {
        LocalDate today = LocalDate.now();
        int previousMonth = today.minusMonths(1).getMonthValue();
        System.out.println("\n-------------------------PREVIOUS MONTH REPORT-------------------------\n");
        printHeader();
        for (TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if (transactionDate.getMonthValue() == previousMonth) {
                System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", i.getDate(), i.getTime(), i.getVendor(), i.getDescription(), i.getAmount());
            }
        }
    }
    private static void searchByVendor() {
        System.out.println("ENTER VENDOR NAME: ");
        String vendorName = consoleInput.nextLine();
        printHeader();
        for(TransactionsInfo i : transactions) {
            if(i.getVendor().equalsIgnoreCase(vendorName)) {
                System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", i.getDate(), i.getTime(), i.getVendor(), i.getDescription(),i.getAmount());

            }
        }
    }
    public static void printHeader() {
        System.out.printf("%-15s %-15s %-20s %-20s %-10s \n", "DATE", "TIME", "VENDOR", "DESCRIPTION", "AMOUNT");
    }
}


