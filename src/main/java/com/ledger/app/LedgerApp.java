package com.ledger.app;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class LedgerApp {
    //used across entire classed
    static Scanner consoleInput = new Scanner(System.in);

    public static void main(String[] args) {
        homescreen();
    }
    //Home screen using switch statement to and while loops
    public static void homescreen() {
        boolean done = false;
        while (!done) {
            System.out.println("\n-------WELCOME TO THE ACCOUNTING LEDGER-------\n");
            System.out.println("""
                    CHOOSE AN OPTION:
                    [D] - ADD DEPOSIT
                    [P] - MAKE PAYMENT(DEBIT)
                    [L] - DISPLAY LEDGER
                    [X] - EXIT APPLICATION
                                       """);
            System.out.print("YOUR CHOICE: ");
            String input = consoleInput.nextLine();
            switch (input.toUpperCase()) {
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "L" -> showLedger();
                case "X" -> {
                    System.out.println("\nEXITING APPLICATION...");
                    System.exit(0);
                    done = true;
                }
                default -> System.out.println("INVALID INPUT... TRY AGAIN ");
            }
        }
    }
    //used to show the ledger menu using while loops and switch statement for user input
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
                    Reports.showReports();
                    done = true;
                }
                case "H" -> {
                    System.out.println("\nRETURNING TO HOME SCREEN... \n");
                    homescreen();
                    done = true;
                }
                default -> System.out.println("INVALID INPUT... TRY AGAIN ");
            }
        }
    }
    //method writes input deposit into transactions csv file
    public static void addDeposit() {

        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            System.out.print("ENTER VENDOR NAME: ");
            String vendor = consoleInput.nextLine();
            System.out.print("ENTER DESCRIPTION: ");
            String description = consoleInput.nextLine();
            System.out.print("ENTER DEPOSIT AMOUNT: ");
            double amount = consoleInput.nextDouble();
            fileWriter.write(String.format("%s|%s|%s|%s|%.2f\n", LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount));
            fileWriter.close();
            System.out.println("\nDEPOSIT ADDED ");
        } catch (IOException e) {
            System.out.println("\nINVALID INPUT");
        }
        LedgerApp.homescreen();
    }
    //method write input payment into transactions csv file
    public static void addPayment() {
        System.out.print("ENTER VENDOR NAME: ");
        String vendor = consoleInput.nextLine();
        System.out.print("ENTER DESCRIPTION: ");
        String description = consoleInput.nextLine();
        System.out.print("ENTER PAYMENT AMOUNT: ");
        double amount = consoleInput.nextDouble();

        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            fileWriter.write(String.format("\n%s|%s|%s|%s|-%.2f\n", LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount));
            fileWriter.close();
            System.out.println("\nPAYMENT ADDED... ");
        } catch (IOException e) {
            System.out.println("INVALID INPUT ");
        }
        LedgerApp.homescreen();
    }
}


