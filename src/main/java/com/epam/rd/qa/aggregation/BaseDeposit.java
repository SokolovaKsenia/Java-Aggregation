package com.epam.rd.qa.aggregation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaseDeposit extends Deposit {
    public BaseDeposit(BigDecimal amount, int period) {
        super(amount, period);
    }

    BigDecimal income() {
        BigDecimal sum = amount;
        if (period > 0) {
            for (int i = 1; i < period + 1; i++) {
                sum = sum.add(sum.multiply(BigDecimal.valueOf(5).divide(BigDecimal.valueOf(100))));
            }
            return sum.subtract(amount).setScale(2, RoundingMode.FLOOR);
        } else return BigDecimal.valueOf(0).setScale(2);
    }
}

