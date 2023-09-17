package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, Duration timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds.getSeconds());
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, Duration.ofSeconds(5));
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, Duration.ofSeconds(5));
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, Duration timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds.getSeconds());
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, Duration timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        webElement.clear();
        return webElement;
    }

    public boolean assertElementHasText(By by, String expectedText, String errorMessage) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.textToBe(by, expectedText));
    }

    public void swipeUp(WaitOptions timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            TouchAction touchAction = new TouchAction(driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            touchAction
                    .press(PointOption.point(x, start_y))
                    .waitAction(timeOfSwipe)
                    .moveTo(PointOption.point(x, end_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance());
        }
    }

    public void swipeElementToLeft(String locator, String errorMessage) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WebElement webElement = waitForElementPresent(locator, errorMessage, Duration.ofSeconds(10));

            int left_x = webElement.getLocation().getX();
            int right_x = left_x + webElement.getSize().getWidth();
            int upper_y = webElement.getLocation().getY();
            int lower_y = upper_y + webElement.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction touchAction = new TouchAction(driver);
            touchAction.press(PointOption.point(right_x, middle_y));
            touchAction.waitAction(new WaitOptions().withDuration(Duration.ofMillis(300)));

            if (Platform.getInstance().isAndroid()) {
                touchAction.moveTo(PointOption.point(left_x, middle_y));
            } else {
                int offsetX = (-1 * webElement.getSize().getWidth());
                touchAction.moveTo(PointOption.point(offsetX, 0));
            }
            touchAction.release();
            touchAction.perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance());
        }
    }

    public void swipeUpQuick() {
        swipeUp(WaitOptions.waitOptions(Duration.ofMillis(300)));
    }

    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        By by = getLocatorByString(locator);
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(
                        locator,
                        "Cannot find element by swaping up. \n" + errorMessage,
                        Duration.ofSeconds(5));
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = this.waitForElementPresent(locator, "Cannot find element by locator", Duration.ofSeconds(5))
                .getLocation()
                .getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = driver;
            Object jsResult = javascriptExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocatedOnTheScreen(locator));
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMessage) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WebElement webElement = this.waitForElementPresent(locator + "/..", errorMessage, Duration.ofSeconds(5));
            int rightX = webElement.getLocation().getX();
            int upperY = webElement.getLocation().getY();
            int lowerY = upperY + webElement.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;
            int width = webElement.getSize().getWidth();

            int pointToClickX = (rightX + width) - 3;
            int pointToClickY = middleY;

            TouchAction touchAction = new TouchAction(driver);
            touchAction.tap(PointOption.point(pointToClickX, pointToClickY)).perform();
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner() does nothing for platform " + Platform.getInstance());
        }
    }

    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) {
        int currentAttempts = 0;
        boolean needMoreAttempts = true;

        while (needMoreAttempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (currentAttempts > amountOfAttempts) {
                    this.waitForElementAndClick(locator, errorMessage);
                }
            }
            ++currentAttempts;
        }
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = driver;
            javascriptExecutor.executeScript("window.scrollBy(0, 255)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance());
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        WebElement webElement = this.waitForElementPresent(locator, errorMessage, Duration.ofSeconds(10));

        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++alreadySwiped;
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, webElement.isDisplayed());
            }
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(locator, errorMessage, Duration.ofSeconds(timeoutInSeconds));
        return webElement.getAttribute(attribute);
    }

    public void assertElementPresent(String locator, String errorMessage) {
        By by = this.getLocatorByString(locator);
        Assert.assertTrue(errorMessage, driver.findElement(by).isDisplayed());
    }

    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return By.id(locator);
        } else if (byType.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator: Locator: " + locatorWithType);
        }
    }
}