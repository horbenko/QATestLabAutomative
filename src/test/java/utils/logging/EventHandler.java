package utils.logging;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.util.Arrays;
import java.util.stream.Collectors;
import static java.lang.String.format;
import static java.lang.String.valueOf;

public class EventHandler implements WebDriverEventListener {

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {
        CustomReporter.log("Attempt to accept alert.");
    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {
        CustomReporter.log("The alert was accepted.");
    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {
        CustomReporter.log("Attempt to dismiss alert.");
    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {
        CustomReporter.log("The alert was dismissed.");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        CustomReporter.log(format("Attempt to navigate to %s.", url));
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        CustomReporter.log(format("Navigation to %s was successful.", url));
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        CustomReporter.log("Attempt to navigate back.");
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        CustomReporter.log(format("Navigation back to %s was successful.", driver.getCurrentUrl()));
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        CustomReporter.log("Attempt to navigate forward.");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        CustomReporter.log(format("Navigation forward to %s was successful.", driver.getCurrentUrl()));
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        CustomReporter.log("Attempt to refresh page.");
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        CustomReporter.log(format("The refreshing was successful, current URL: %s.", driver.getCurrentUrl()));
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        CustomReporter.log(format("Attempt to find something using %s. The root element is %s.", by.toString(), valueOf(element)));
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        if (element != null) {
            CustomReporter.log(format("The searching for something using %s has been finished. The root element was %s.", by.toString(), valueOf(element)));
        }
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        CustomReporter.log(format("Attempt to click on the element %s.", element.getTagName()));
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
       /* if (element != null) { //TODO To debug.
            CustomReporter.log(format("The element %s was clicked.", element.getTagName()));
        }*/
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        String value = Arrays.stream(keysToSend).map(CharSequence::toString).collect(Collectors.joining(""));
        CustomReporter.log(format("Attempt to change value %s of the %s.", value, element.getTagName()));
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        CustomReporter.log(format("The element %s was changed.", element.getTagName()));
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        CustomReporter.log(format("Attempt to execute script: %s.", script));
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        CustomReporter.log(format("Script %s was executed.", script));
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        CustomReporter.log(format("The exception %s was thrown.", throwable.getClass()));
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {
        CustomReporter.log(format("Attempt to take screenshot. Target type is %s.", outputType));
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {
        CustomReporter.log(format("Screenshot was taken successfully. Target type is %s, result is %s", outputType, x));
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver webDriver) {
        CustomReporter.log("Attempt to get text of the element.");
    }

    @Override
    public void afterGetText(WebElement element, WebDriver webDriver, String s) {
        CustomReporter.log("Got the text of an element.");
    }

    @Override
    public void beforeSwitchToWindow(String window, WebDriver webDriver) {
        CustomReporter.log(format("Attempt to switch to window %s.", window));
    }

    @Override
    public void afterSwitchToWindow(String window, WebDriver webDriver) {
        CustomReporter.log(format("Driver is switched to window %s.", window));
    }

}
