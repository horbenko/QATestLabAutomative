package config;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.logging.CustomReporter;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public static void login(WebDriver driver){
        CustomReporter.logAction("Login to the account.");
        WebElement login = driver.findElement(By.id("email"));
        login.sendKeys(Config.getUsername());
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys(Config.getPassword());
        WebElement submit = driver.findElement(By.name("submitLogin"));
        submit.click();

    }

    public static void logout(WebDriver driver){
        CustomReporter.logAction("Logout from the account.");
        WebElement logoutBtn = driver.findElement(By.id("header_employee_box"));
        logoutBtn.click();
        WebElement logout = driver.findElement(By.id("header_logout"));
        logout.click();
    }
}
