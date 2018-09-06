package utils;

import org.testng.Assert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConverter {

    private DataConverter() {
    }

    /**
     * Extracts price value from price labels.
     *
     * @param label The content of some label with price value.
     * @return Parsed float value of the price.
     */
    public static float parsePriceValue(String label) {
        Matcher priceMatcher = Pattern.compile("^(.*) $").matcher(label);
        Assert.assertTrue(priceMatcher.find(), "Unable to extract price value!");
        try {
            DecimalFormatSymbols separators = new DecimalFormatSymbols();
            separators.setDecimalSeparator(',');
            return new DecimalFormat("#0.00", separators).parse(priceMatcher.group(1)).floatValue();
        } catch (ParseException e) {
            throw  new RuntimeException(e);
        }
    }
}
