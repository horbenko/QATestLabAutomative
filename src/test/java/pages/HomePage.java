package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.logging.CustomReporter;

import java.util.List;

public class HomePage extends BasePage {

    private final By currencyBtn = By.cssSelector("span.expand-more._gray-darker");
    private final By eurCurrency = By.linkText("EUR €");
    private final By uahCurrency = By.linkText("UAH ₴");
    private final By usdCurrency = By.linkText("USD $");
    private final By productPrice = By.className("price");
    private final By searchField = By.className("ui-autocomplete-input");

    /**
     * Get currency from the the header of the dropdown menu.
     * @return currency.
     */
    public Currency getEstablishedCurrency() {

        String value = driver.findElement(currencyBtn).getText();
        switch (value) {
            case "UAH ₴":
                return Currency.UAH;
            case "EUR €":
                return Currency.EUR;
            case "USD $":
                return Currency.USD;
            default:
                throw new IllegalArgumentException("Currency selection problems detected.");
        }
    }

    /**
     * Clicks on the selected currency in the header of the dropdown menu.
     * @param currency supported currency (EUR, UAH, USD).
     */
    public void selectCurrency(Currency currency) {
        waitIsClickable(currencyBtn);
        driver.findElement(currencyBtn).click();
        switch (currency) {
            case EUR:
                waitIsClickable(eurCurrency);
                driver.findElement(eurCurrency).click();
                break;
            case UAH:
                waitIsClickable(this.uahCurrency);
                driver.findElement(uahCurrency).click();
                break;
            case USD:
                waitIsClickable(this.usdCurrency);
                driver.findElement(usdCurrency).click();
                break;
            default:
                throw new IllegalArgumentException("Currency selection problems detected.");
        }
    }

    /**
     * Verifies the currency of the products with the established currency of the site.
     * @param currency supported currency (EUR, UAH, USD).
     * @return false if the currency doesn't match, true - if matches. Throws IllegalArgumentException if currency not supported.
     */
    public boolean checkProductsCurrency(Currency currency) {
        waitIsClickable(productPrice);
        List<WebElement> productPrices = driver.findElements(productPrice);
        CustomReporter.log("Verifying the currency of the products with the established currency of the site.");
        switch (currency) {
            case EUR:
                for (WebElement value : productPrices) {
                    if (value.getText().contains("€") || value.getText().contains("EUR")) {
                        CustomReporter.log(value.getText() + ". Passed.");
                    } else {
                        return false;
                    }
                }
                return true;
            case UAH:
                for (WebElement value : productPrices) {
                    if (value.getText().contains("₴") || value.getText().contains("UAH")) {
                        CustomReporter.log(value.getText() + ". Passed.");
                    } else {
                        return false;
                    }
                }
                return true;
            case USD:
                for (WebElement value : productPrices) {
                    if (value.getText().contains("$") || value.getText().contains("USD")) {
                        CustomReporter.log(value.getText() + ". Passed.");
                    } else {
                        return false;
                    }
                }
                return true;
            default:
                throw new IllegalArgumentException("Unsupported currency.");
        }
    }

    /**
     * Sends string into the catalog search field.
     * @param searchString input string to search.
     * @return web page with search results.
     */
    public SearchResultPage searchProduct(String searchString) {
        waitIsClickable(searchField);
        WebElement searchField = driver.findElement(this.searchField);
        searchField.sendKeys(searchString);
        searchField.submit();
        return new SearchResultPage();
    }
}
