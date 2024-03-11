package io.github.matheusfsantos.tgid.model.utils.math;

import java.math.BigDecimal;

public class PriceAdjustmentWithInterest {

	public static BigDecimal getPriceAdjustmentWithInterest(BigDecimal value) {
		BigDecimal interestRate = value.multiply(new BigDecimal("0.04")); /* adds 4% interest for each withdrawal made from a company */
		return value.add(interestRate);
	}
	
}
