package io.github.matheusfsantos.tgid.model.utils.format;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormat {

	public static String getFormattedPrice(BigDecimal price) {
	    Locale locale = new Locale("pt", "BR");
	    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
	    return numberFormat.format(price);
	}
	
}
