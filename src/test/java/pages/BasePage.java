package pages;

import config.BaseConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    final WebDriver driver;
    final WebDriverWait wait;

    BasePage() {
        driver = BaseConfig.getDriver();
        wait = new WebDriverWait(driver, 10);
    }

    /**
     * Waits while an element appears on the DOM of a page and becomes clickable.
     * @param by supported methods by class By.
     */
    void waitIsClickable(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * Waits while an element appears on the DOM of a page and become visible.
     * @param by supported methods by class By.
     */
    void waitIsVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Set of available currency on the page.
     */
    public enum Currency {
        EUR,
        UAH,
        USD
    }

    /**
     * Set of available types of sorting on the page.
     */
    public enum sortList {
        RELEVANCE("Релевантность"),
        NAME_FROM_A_TO_Z("Названию: от А к Я"),
        NAME_FROM_Z_TO_A("Названию: от Я к А"),
        PRICE_FROM_LOW_TO_HIGH("Цене: от низкой к высокой"),
        PRICE_FROM_HIGH_TO_LOW("Цене: от высокой к низкой");

        private final String description;

        sortList(String description) {
            this.description = description;
        }

        String getDescription() {
            return description;
        }
    }
}