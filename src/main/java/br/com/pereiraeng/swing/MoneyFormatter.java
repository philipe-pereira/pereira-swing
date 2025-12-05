package br.com.pereiraeng.swing;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class MoneyFormatter extends AbstractFormatter {
	private static final long serialVersionUID = 1L;

	private NumberFormat currencyFormatter;

	public MoneyFormatter() {
		this(Locale.getDefault());
	}

	public MoneyFormatter(Locale l) {
		this.currencyFormatter = NumberFormat.getCurrencyInstance(l);
	}

	@Override
	public Object stringToValue(String string) throws ParseException {
		return currencyFormatter.parse(string).doubleValue();
	}

	@Override
	public String valueToString(Object object) throws ParseException {
		return currencyFormatter.format(((Number) object).doubleValue());
	}
}
