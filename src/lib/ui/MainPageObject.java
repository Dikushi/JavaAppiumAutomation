package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 5);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        webElement.clear();
        return webElement;
    }

    public boolean assertElementHasText(By by, String expectedText, String errorMessage) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.textToBe(by, expectedText));
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction touchAction = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        touchAction
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    public void swipeElementToLeft(By by, String errorMessage) {
        WebElement webElement = waitForElementPresent(by, errorMessage, 10);

        int left_x = webElement.getLocation().getX();
        int right_x = left_x + webElement.getSize().getWidth();
        int upper_y = webElement.getLocation().getY();
        int lower_y = upper_y + webElement.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction touchAction = new TouchAction(driver);
        touchAction
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(
                        by,
                        "Cannot find element by swaping up. \n" + errorMessage,
                        0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return webElement.getAttribute(attribute);
    }

    public void assertElementPresent(By by, String errorMessage) {
        Assert.assertTrue(errorMessage, driver.findElement(by).isDisplayed());
    }
}