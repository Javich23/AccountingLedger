package com.ledger.app;

import java.time.LocalDate;
import java.util.Scanner;

import static com.ledger.app.Ledger.transactions;

public class Reports {
    static Scanner consoleInput = new Scanner(System.in);
    //report menu using while loop and switch statement for user input
    public static void showReports() {
        boolean done = false;
        while (!done) {
            System.out.println("\nSELECT REPORT TYPE\n");
            System.out.println("""
                    [1] - MONTH TO DATE
                    [2] - PREVIOUS MONTH
                    [3] - YEAR TO DATE
                    [4] - PREVIOUS YEAR
                    [5] - SEARCH BY VENDOR
                    [0] - BACK
                    """);
            System.out.print("YOUR CHOICE: ");
            int choice = consoleInput.nextInt();
            consoleInput.nextLine();
            switch (choice) {
                case 1 -> monthToDate();
                case 2 -> previousMonth();
                case 3 -> yearToDate();
                case 4 -> previousYear();
                case 5 -> searchByVendor();
                case 0 -> {
                    LedgerApp.showLedger();
                    done = true;
                }
                default -> System.out.println("\nINVALID OPTION... TRY AGAIN");

            }
        }
    }
    // display only month to date transactions in a report
    private static void monthToDate() {
        LocalDate today = LocalDate.now();
        System.out.println("\n------------------------------------MONTH TO DATE REPORT--------------------------------------\n");
        printHeader();
        for (TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if (transactionDate.getMonth() == today.getMonth() && transactionDate.getYear() == today.getYear()) {
                System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }
    //displays year to date transactions in a report
    private static void yearToDate() {
        LocalDate today = LocalDate.now();
        System.out.println("\n----------------------------------YEAR TO DATE REPORT-----------------------------------------\n");
        printHeader();
        for (TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if (transactionDate.getYear() == today.getYear()) {
                System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }
    //display previous year transactions in a report
    private static void previousYear() {
        LocalDate today = LocalDate.now();
        int previousYear = today.minusYears(1).getYear();
        System.out.println("\n---------------------------------PREVIOUS YEAR REPORT-----------------------------------------\n");
        printHeader();
        for (TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if (transactionDate.getYear() == previousYear) {
                System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());

            }
        }
    }
    // displays previous month transactions in a report
    private static void previousMonth() {
        LocalDate today = LocalDate.now();
        int previousMonth = today.minusMonths(1).getMonthValue();
        System.out.println("\n------------------------------------PREVIOUS MONTH REPORT--------------------------------------\n");
        printHeader();
        for (TransactionsInfo i : transactions) {
            LocalDate transactionDate = i.getDate();
            if (transactionDate.getMonthValue() == previousMonth) {
                System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
            }
        }
    }
    // displays transactions filtered by the vendor name user desires
    private static void searchByVendor() {
        System.out.print("ENTER VENDOR NAME: ");
        String vendorName = consoleInput.nextLine();
        System.out.print("\n-----------------------------------TRANSACTIONS FOR: " + vendorName + "-------------------------------------\n\n");
        printHeader();

        for (TransactionsInfo i : transactions) {
            if (i.getVendor().equalsIgnoreCase(vendorName)) {
                System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());

            }
        }
    }
    //header used throughout the class and other classes
    public static void printHeader() {
        System.out.printf("%-15s %-15s %-25s %-20s %15s \n", "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");
        System.out.println("..............................................................................................");
    }
}
