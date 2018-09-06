package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.logging.CustomReporter;

import java.text.DecimalFormat;
import java.util.List;

public class SearchResultPage extends HomePage {
    private final By productTile = By.className("product-title");
    private final By totalProductsLabel = By.className("total-products");
    private final By productPrice = By.className("price");
    private final By regularPrice = By.xpath("//div[@class='product-price-and-shipping']/span[1]");
    private final By productsWithDiscount = By.className("discount");
    private final By labelOfDiscount = By.className("discount-percentage");
    private final By actualPriceOfDiscountProducts = By.xpath("//span[@class='discount-percentage']/following-sibling::span");
    private final By regularPriceOfDiscountProducts = By.xpath("//span[@class='discount-percentage']/preceding-sibling::span");
    private final By sortingTypeField = By.className("select-title");
    private final By sortingType = By.className("select-list");
    private final int priceLength = 4;

    /**
     * Finds, calculate and return the total quantity products displayed on the search result page.
     * @return the quantity of all products displayed on the search result page.
     */
    public int getAllProductsQuantity() {
        waitIsClickable(productTile);
        return driver.findElements(productTile).size();
    }

    /**
     * Finds and return value of the total quantity products displayed on the search result page.
     * @return the value of the total quantity products displayed on the search result page.
     */
    public String getTotalQuantityOfFoundProducts() {
        waitIsVisible(this.totalProductsLabel);
        return driver.findElement(totalProductsLabel).getText();
    }

    /**
     * Verifies the currency of the searched products with the established currency of the site.
     * @param currency supported currency (EUR, UAH, USD).
     * @return false if the currency doesn't match, true - if matches. Throws IllegalArgumentException if currency not supported.
     */
    public boolean checkCurrencyOfFoundProducts(Currency currency) {
        waitIsClickable(productPrice);
        List<WebElement> prices = driver.findElements(productPrice);
        CustomReporter.log("Verifying currency of product prices ...");
        switch (currency) {
            case EUR:
                for (WebElement value : prices) {
                    if (!value.getText().contains("€") && !value.getText().contains("EUR")) {
                        CustomReporter.log("Some product price is not displayed in EUR currency. Failed.");
                        return false;
                    }
                }
                CustomReporter.log("All products prices are displayed in EUR currency. Passed.");
                return true;
            case UAH:
                for (WebElement value : prices) {
                    if (!value.getText().contains("₴") && !value.getText().contains("UAH")) {
                        CustomReporter.log("Some product price is not displayed in UAH currency. Failed.");
                        return false;
                    }
                }
                CustomReporter.log("All products prices are displayed in UAH currency. Passed.");
                return true;
            case USD:
                for (WebElement value : prices) {
                    if (!value.getText().contains("USD") && !value.getText().contains("$")) {
                        CustomReporter.log("Some product is not displayed in USD currency. Failed.");
                        return false;
                    }
                }
                CustomReporter.log("All products prices are displayed in USD currency. Passed.");
                return true;
            default:
                throw new IllegalArgumentException("Unsupported currency.");
        }
    }

    public boolean checkDescendingSorting() {
        wait.until((ExpectedConditions.urlContains("search?controller=search&order=product.price.desc&s=")));
        List<WebElement> regularPrices = driver.findElements(regularPrice);
        if (regularPrices.size() >= 2) {
            CustomReporter.log("Attempt to check descending price sorting.");
            for (int i = 0; i + 1 < regularPrices.size(); i++) {
                float price = Float.parseFloat(regularPrices.get(i).getText().substring(0, priceLength).replace(",", "."));
                float nextPrice = Float.parseFloat(regularPrices.get(i + 1).getText().substring(0, priceLength).replace(",", "."));
                if (price < nextPrice) {
                    CustomReporter.log(price + " is less than " + nextPrice + ". Failed.");
                    return false;
                }
                CustomReporter.log(price + " is more than or equals " + nextPrice + ". Passed.");
            }
            return true;
        } else {
            throw new IllegalStateException("Impossible to check sorting method, there are less than 2 products on page");
        }
    }

    public boolean checkDiscountProductPriceAndLabel() {
        waitIsClickable(productTile);
        List<WebElement> discountProduct = driver.findElements(this.productsWithDiscount);
        List<WebElement> labelsOfDiscount = driver.findElements(this.labelOfDiscount);
        List<WebElement> regularPriceOfDiscountProducts = driver.findElements(this.regularPriceOfDiscountProducts);
        List<WebElement> actualPriceOfDiscountProducts = driver.findElements(this.actualPriceOfDiscountProducts);
        CustomReporter.log("Verifying each On-Sale product has its own discount label, actual and regular price ...");
        CustomReporter.log("Found on page: " + discountProduct.size() + " products On-Sale.");
        CustomReporter.log("Found on page: " + labelsOfDiscount.size() + " labels of products On-Sale.");
        CustomReporter.log("Found on page: " + regularPriceOfDiscountProducts.size() + " regular prices of products On-Sale.");
        CustomReporter.log("Found on page: " + actualPriceOfDiscountProducts.size() + " actual prices of products On-Sale.");
        CustomReporter.log("Verifying the price is displayed in percentages ...");
        for (WebElement label : labelsOfDiscount) {
            if (label.getText().contains("%")) {
                CustomReporter.log("Label: " + label.getText() + ". Passed.");
            }
            else {
                CustomReporter.log("Label: " + label.getText() + ". Failed.");
                return false;
            }

        }
        if (discountProduct.size() == labelsOfDiscount.size() && discountProduct.size() == regularPriceOfDiscountProducts.size() && discountProduct.size() == actualPriceOfDiscountProducts.size()) {
            CustomReporter.log("Quantity of On-Sale products: " + discountProduct.size() + " equals the quantity of discount labels: " + labelsOfDiscount.size() + " quantity of regular prices: " + regularPriceOfDiscountProducts.size() + " quantity of actual prices: " + actualPriceOfDiscountProducts.size() + ". Passed.");
            return true;
        } else {
            CustomReporter.log("Quantity of On-Sale products: " + discountProduct.size() + " IS NOT equal the quantity of discount labels: " + labelsOfDiscount.size() + " quantity of regular prices: " + regularPriceOfDiscountProducts.size() + " quantity of actual prices: " + actualPriceOfDiscountProducts.size() + ". Passed.");
            return false;
        }
    }

    /**
     * Verifies price before and after the discount.
     * @return true if calculated value coincides with the specified discount size, false - otherwise.
     */
    public boolean checkDiscountCalculation() {
        waitIsClickable(productTile);
        List<WebElement> productsWithDiscount = driver.findElements(this.productsWithDiscount);
        List<WebElement> labelsOfDiscount = driver.findElements(this.labelOfDiscount);
        List<WebElement> regularPriceOfDiscountProducts = driver.findElements(this.regularPriceOfDiscountProducts);
        List<WebElement> actualPriceOfDiscountProducts = driver.findElements(this.actualPriceOfDiscountProducts);
        DecimalFormat f = new DecimalFormat("##.00");
        CustomReporter.log("Verifying the discount calculation.");
        for (int i = 0; i < productsWithDiscount.size(); i++) {
            int discountPercentage = Integer.parseInt(labelsOfDiscount.get(i).getText().replace("%", ""));
            float regularPrice = Float.parseFloat(regularPriceOfDiscountProducts.get(i).getText().substring(0, priceLength).replace(",", "."));
            float actualPrice = Float.parseFloat(actualPriceOfDiscountProducts.get(i).getText().substring(0, priceLength).replace(",", "."));
            float discountAmount = Float.parseFloat(f.format(((regularPrice * (Math.abs(discountPercentage))) / 100)).replace(",", "."));
            if ((regularPrice - discountAmount) != actualPrice) {
                System.out.println(productsWithDiscount.get(i).getTagName() + " product has " + labelsOfDiscount.get(i).getText() + " . Actual price from " + regularPrice + " is " + actualPrice + ". Failed.");
                return false;
            }
            CustomReporter.log("Product #" + (i + 1) + " has " + labelsOfDiscount.get(i).getText() + "($" + discountAmount + ")" + " discount amount. Actual price from " + regularPrice + " is " + actualPrice + ". Passed.");
        }
        return true;
    }

    /**
     * Sets available type of sorting to search result page.
     * @param type supported sorting.
     */
    public void toChangeSorting(sortList type) {
        waitIsClickable(this.sortingTypeField);
        driver.findElement(this.sortingTypeField).click();
        for (WebElement value : driver.findElements(sortingType)) {
            if (value.getText().trim().equals(type.getDescription())) {
                waitIsClickable(sortingType);
                value.click();
            }
        }
    }
}
