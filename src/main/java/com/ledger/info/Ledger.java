package com.ledger.info;

import java.util.Scanner;

public  class Ledger {
    public static void showLedger() {
        Scanner consoleInput = new Scanner(System.in);
        System.out.println("Welcome to the Accounting Ledger ");
        System.out.println("""
                Choose an option from the main menu:\s
                [A] - Display Entries
                [D] - Display Deposits
                [P] - Display Payments
                [R] - Display Reports
                                   """);
        String input = consoleInput.nextLine();
        switch (input.toUpperCase()) {
            case "A":
                showEntries();
                break;
            case "D":
                showDeposits();
                break;
            case "R":
                showPayments();
                break;
            case "R":
                showReports();
                break;
            default:
                System.out.println("Invalid entry... Try again ");
                break;
        }
    }
}
