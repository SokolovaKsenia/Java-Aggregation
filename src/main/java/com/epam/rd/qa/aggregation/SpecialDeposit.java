package com.epam.rd.qa.aggregation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpecialDeposit extends Deposit {

    public SpecialDeposit(BigDecimal amount, int period) {
        super(amount, period);
    }

    BigDecimal income() {
        BigDecimal sum = amount;
        for (int i = 1; i < period + 1; i++) {
            sum = sum.add(sum.multiply(BigDecimal.valueOf(i).divide(BigDecimal.valueOf(100))));
        }
        return sum.subtract(amount).setScale(2, RoundingMode.FLOOR);
    }
}