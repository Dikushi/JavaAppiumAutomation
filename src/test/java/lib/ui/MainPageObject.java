package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getLocalizedMessage());
        }
        return bytes;
    }

    @Step("Waiting for element present")
    public WebElement waitForElementPresent(String locator, String errorMessage, Duration timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds.getSeconds());
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Step("Waiting for element present and click")
    public WebElement waitForElementAndClick(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, Duration.ofSeconds(5));
        element.click();
        return element;
    }

    @Step("Waiting for element and send keys")
    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, Duration.ofSeconds(5));
        element.sendKeys(value);
        return element;
    }

    @Step("Waiting for element not present")
    public boolean waitForElementNotPresent(String locator, String errorMessage, Duration timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds.getSeconds());
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    @Step("Waiting for element and clear")
    public WebElement waitForElementAndClear(String locator, String errorMessage, Duration timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        webElement.clear();
        return webElement;
    }

    @Step("Swiping up")
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

    @Step("Swiping element to left")
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

    @Step("Swiping up to find element")
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

    @Step("Checking is element is on the screen")
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

    @Step("Swiping up to element")
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

    @Step("Get amount of elements")
    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    @Step("Check element is present")
    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    @Step("Click to element")
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

    @Step("Click to element with few attempts")
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

    @Step("Scrolling page to head")
    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = driver;
            javascriptExecutor.executeScript("window.scrollBy(0, 255)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance());
        }
    }

    @Step("Scrolling to element")
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

    @Step("Check element is present")
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

    @Step("Take screenshot")
    public String takeScreenshot(String name) {
        TakesScreenshot takesScreenshot = driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken: " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }
}