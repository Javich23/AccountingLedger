package com.ledger.info;
import java.sql.SQLOutput;
import java.util.Scanner;
public class Main {
    static Scanner consoleInput = new Scanner(System.in);
    public static void main(String[] args) {
        homescreen();
    }
    public static void homescreen() {
        boolean done = false;
        while (!done) {
            System.out.println("\n-------WELCOME TO THE ACCOUNTING LEDGER-------\n");
            System.out.println("""
                    CHOOSE AN OPTION:
                    [D] - ADD DEPOSIT
                    [P] - MAKE PAYMENT(DEBIT)
                    [L] - LEDGER
                    [X] - EXIT APPLICATION
                                       """);
            System.out.print("Your choice: ");
            String input = consoleInput.nextLine();
            switch (input.toUpperCase()) {
                case "D" -> Ledger.addDeposit();
                case "P" -> Ledger.makePayment();
                case "L" -> showLedger();
                case "X" -> {
                    System.out.println("\nExiting Application...");
                    System.exit(0);
                    done = true;
                }
                default -> System.out.println("Invalid entry... Try again ");
            }
        }
    }
    public static void showLedger() {
        boolean done = false;
        while (!done) {
            System.out.println("\n-------LEDGER MENU-------\n");
            System.out.println("""
                    CHOOSE AN OPTION:
                    [A] - DISPLAY ENTRIES
                    [D] - DISPLAY DEPOSITS
                    [P] - DISPLAY PAYMENTS
                    [R] - DISPLAY REPORTS
                    [H] - RETURN TO HOME SCREEN
                                       """);
            System.out.print("YOUR CHOICE: ");
            String input = consoleInput.nextLine();
            switch (input.toUpperCase()) {
                case "A" -> Ledger.showEntries();
                case "D" -> Ledger.showDeposits();
                case "P" -> Ledger.showPayments();
                case "R" -> {
                    Ledger.showReports();
                    done =  true;
                }
                case "H" -> {
                    System.out.println("Returning to home screen... \n");
                    homescreen();
                    done = true;
                }
                default -> System.out.println("Invalid entry... Try again ");
            }
        }
    }
}


