package tests;

import config.BaseConfig;
import org.testng.annotations.*;
import pages.*;
import pages.BasePage.Currency;
import utils.logging.CustomReporter;

import static java.lang.String.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Tests extends BaseConfig {

    @Test
    public void testCase() {
        HomePage homePage = new HomePage();

        CustomReporter.logAction("Start logging.");
        assertTrue(homePage.checkProductsCurrency(homePage.getEstablishedCurrency()), "The product currency doesn't match with selected currency."); // Step 1, 2.
        homePage.selectCurrency(Currency.USD); // Step 3.
        SearchResultPage searchResultPage = homePage.searchProduct("dress"); //Step 4.
        CustomReporter.log("Verifying that the \"Товаров\" label is displayed and shows the correct quantity of the products.");
        assertEquals(searchResultPage.getTotalQuantityOfFoundProducts(), format("Товаров: %d.", searchResultPage.getAllProductsQuantity())); // Step 5.
        CustomReporter.log("The label: \"Товаров: " + searchResultPage.getAllProductsQuantity() + "\" is displayed and shows correct quantity. Passed.");
        assertTrue(searchResultPage.checkCurrencyOfFoundProducts(Currency.USD), "Some product currency doesn't correspond to the selected currency in the header."); // Step 6.
        searchResultPage.toChangeSorting(BasePage.sortList.PRICE_FROM_HIGH_TO_LOW); // Step 7.
        assertTrue(searchResultPage.checkDescendingSorting(), "The products are not sorted in a descending way."); // Step 8.
        assertTrue(searchResultPage.checkDiscountProductPriceAndLabel(), "Some of the products doesn't have discount label, actual or regular price."); // Step 9.
        assertTrue(searchResultPage.checkDiscountCalculation(), "Some of the products has incorrect discount calculation"); // Step 10.
    }

}
