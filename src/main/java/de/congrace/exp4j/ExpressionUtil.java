package de.congrace.exp4j;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Locale;

public abstract class ExpressionUtil {
	/**
	 * normalize a number to an acceptable format for exp4j
	 * e.g. normalizing "314e-2" yields "3.14"
	 * @param number
	 */
	public static String normalizeNumber(String number,Locale loc) throws UnparsableExpressionException{
		String result=number.replaceAll("e|E", "*10^");
		return result;
	}
	public static String normalizeNumber(String number) throws UnparsableExpressionException{
		return normalizeNumber(number, Locale.getDefault());
	}
}
