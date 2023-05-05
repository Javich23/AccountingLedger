package com.ledger.app;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger {
    //array list made to hold transactions file
    public static ArrayList<TransactionsInfo> transactions = loadTransactions();
    //scanner used throughout the entire class
    static Scanner consoleInput = new Scanner(System.in);
    //made to read the transactions file and hold in array list
    public static ArrayList<TransactionsInfo> loadTransactions() {

        ArrayList<TransactionsInfo> transactions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                if (!input.isEmpty()) {
                    String[] details = input.split("\\|");
                    LocalDate date = LocalDate.parse(details[0]);
                    LocalTime time = LocalTime.parse(details[1]);
                    String description = details[2];
                    String vendor = details[3];
                    double amount = Double.parseDouble(details[4]);

                    TransactionsInfo transaction = new TransactionsInfo(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("FILE NOT AVAILABLE ");
            System.exit(0);
        }
        //used to sort by date and time in array list
        transactions.sort(Comparator.comparing(TransactionsInfo::getDate)
                .thenComparing(TransactionsInfo::getTime)
                .reversed());

        return transactions;
    }
    //prints all the transactions inside the transactions file
    public static void showEntries() {
        System.out.println("\n--------------------------------------ALL TRANSACTIONS----------------------------------------\n");
        Reports.printHeader();
        for (TransactionsInfo i : transactions) {
            System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", i.getDate(), i.getTime(), i.getDescription(), i.getVendor(), i.getAmount());
        }
    }
    // only prints the deposits inside transactions file
    public static void showDeposits() {
        System.out.println("\n-------------------------------------LIST OF DEPOSITS-----------------------------------------\n");
        Reports.printHeader();
        for (TransactionsInfo d : transactions) {
            if (d.getAmount() > 0) {
                System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", d.getDate(), d.getTime(), d.getDescription(), d.getVendor(), d.getAmount());
            }
        }
    }
    //only prints payments inside transactions file
    public static void showPayments() {
        System.out.println("\n--------------------------------------LIST OF PAYMENTS----------------------------------------\n");
        Reports.printHeader();
        for (TransactionsInfo p : transactions) {
            if (p.getAmount() < 0) {
                System.out.printf("%-15s %-15s %-25s %-20s %15.2f \n", p.getDate(), p.getTime(), p.getDescription(), p.getVendor(), p.getAmount());
            }
        }
    }
}


