package com.booleanuk.core;

import java.util.ArrayList;
import java.util.HashMap;

public class Bank {
    private HashMap<String, Account> accounts;
    private int accountIterator = 968736856;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public String addAccount(String firstName, String lastName, String type, String branch, double amount) {
        String accountNr;
        while(true) {
            this.accountIterator++;
            accountNr = Integer.toString(accountIterator);
            if (!accounts.containsKey(accountNr))
                break;
        }
        if (type.equals("Current Account"))
            accounts.put(accountNr, new CurrentAccount(accountNr, firstName, lastName, branch));
        else if (type.equals("Savings Account"))
            accounts.put(accountNr, new SavingsAccount(accountNr, firstName, lastName, branch));
        else
            return null;

        accounts.get(accountNr).addTransactions(amount);

        return accounts.get(accountNr).getAccountNr();
    }

    public Account getAccount(String accountNr) {
        if (accounts.containsKey(accountNr))
            return accounts.get(accountNr);
        return null;
    }

    public double getBalance(String accountNr) {
        double balance = 0;
        ArrayList<Transaction> transactions = accounts.get(accountNr).getTransactions();

        for (Transaction transaction: transactions){
            balance += transaction.getAmount();
        }
        return balance;
    }

    public boolean deposit(String accountNr, double amount) {
        if (accounts.containsKey(accountNr)){
            accounts.get(accountNr).addTransactions(amount);
            return true;
        }
        return false;
    }

    public double withdraw(String accountNr, double amount) {
        if (accounts.containsKey(accountNr)) {
            double balance = getBalance(accountNr);

            if (balance == 0.0)
                return 0.0;

            if (amount < balance) {
                accounts.get(accountNr).addTransactions(-amount);
                return amount;
            }
            else { // Attempt of overdrawing, return remaining of account
                accounts.get(accountNr).addTransactions(-balance);
                return balance;
            }
        }
        else
            return 0.0;
    }

}
