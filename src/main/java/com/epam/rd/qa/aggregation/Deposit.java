package com.epam.rd.qa.aggregation;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.BigDecimalConversion;

import java.math.BigDecimal;
import java.util.Objects;

import java.util.NoSuchElementException;

public abstract class Deposit {
    protected BigDecimal amount;
    protected final int period;

    protected Deposit(BigDecimal depositAmount, int depositPeriod) {
        if (depositAmount == null || depositAmount.compareTo(BigDecimal.ZERO) != 1 || depositPeriod <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.amount = depositAmount;
            this.period = depositPeriod;
        }
    }

    abstract BigDecimal income();

    public BigDecimal getAmount() {
        return amount;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return "Deposit{" + "amount=" + amount + ", period=" + period + ", totalamount=" + this.amount.add(income()) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return period == deposit.period && Objects.equals(amount, deposit.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, period);
    }
}

