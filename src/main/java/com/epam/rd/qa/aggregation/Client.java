package com.epam.rd.qa.aggregation;
import java.util.NoSuchElementException;
import java.math.BigDecimal;

public class Client {
    private Deposit[] deposits;

    public Client() {
        this.deposits = new Deposit[10];
    }

    public Client(Deposit[] deposits) {
        if (deposits == null || deposits.length == 0) {
            throw new NoSuchElementException();
        } else {
            this.deposits = deposits;
        }
    }

    public boolean addDeposit(Deposit deposit) {
        if (deposit == null) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < deposits.length; i++) {
                if (deposits[i] == null) {
                    deposits[i] = deposit;
                    return true;
                }
            }
        }
        return false;
    }

    public BigDecimal totalIncome() {
        BigDecimal sum = new BigDecimal(0);
        for (Deposit deposit : deposits) {
            if (deposit != null) {
                sum = sum.add(deposit.income());
            }
        }
        return sum;
    }

    public BigDecimal maxIncome() {
        BigDecimal max = BigDecimal.valueOf(0);
        for (Deposit d : deposits) {
            if (d != null && d.income().compareTo(max) == 1) {
                max = d.income();
            }
        }
        return max;
    }

    public BigDecimal getIncomeByNumber(int number) {
        if (deposits[number] != null) return deposits[number].income();
        else return BigDecimal.valueOf(0).setScale(2);
    }
}


