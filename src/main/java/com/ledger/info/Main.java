package com.ledger.info;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.util.Scanner;

public class Main {
    static Scanner consoleInput = new Scanner(System.in);

    public static void main(String[] args) {
        homescreen();
    }

    public static void homescreen() {
        System.out.println("Welcome to the Accounting Ledger ");
        System.out.println("""
                Choose an option from the main menu:\s
                [D] - Add Deposit
                [P] - Make Payment(Debit)
                [L] - Ledger
                [X] - Exit Application
                                   """);
        String input = consoleInput.nextLine();
        switch (input.toUpperCase()) {
            case "D":
                addDeposit();
                break;
            case "P":
                makePayment();
                break;
            case "L":
                showLedger();
                break;
            case "X":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid entry... Try again ");
                break;
        }
    }

    public static void addDeposit() {
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
            fileWriter.write("\n" + date + "|" +
                    time + "|" +
                    description + "|" +
                    vendor + "|" +
                    amount);
            fileWriter.close();
            System.out.println("Deposit added ");
        } catch (IOException e) {
            System.out.println("Input not processed... Try again ");
        }
        homescreen();


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
            fileWriter.write("\n" + date + "|" +
                    time + "|" +
                    description + "|" +
                    vendor + "|" + "-" +
                    amount);
            fileWriter.close();
            System.out.println("Payment added ");
        } catch (IOException e) {
            System.out.println("Input not processed... Try again ");
        }
        homescreen();
    }

    public static void showLedger() {
      Ledger.showLedger();
    }
}
