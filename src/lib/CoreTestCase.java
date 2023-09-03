package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    private static final String APPIUM_URL = "http://127.0.0.1:4723/";
    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setDriverByPlatformEnv(this.getCapabilitiesByPlatformEnv());

        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();

        super.tearDown();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(Duration seconds) {
        driver.runAppInBackground(seconds);
    }

    protected void navigateBack() {
        driver.navigate().back();
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            desiredCapabilities.setCapability("platformName", "Android");
            desiredCapabilities.setCapability("deviceName", "and80");
            desiredCapabilities.setCapability("platformVersion", "8.1");
            desiredCapabilities.setCapability("automationName", "Appium");
            desiredCapabilities.setCapability("appPackage", "org.wikipedia");
            desiredCapabilities.setCapability("appActivity", ".main.MainActivity");
            desiredCapabilities.setCapability("app",
                    "/Users/movchinnikov/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");
            desiredCapabilities.setCapability("automationName", "UiAutomator2");
        } else if (platform.equals(PLATFORM_IOS)) {
            desiredCapabilities.setCapability("platformName", "iOS");
            desiredCapabilities.setCapability("deviceName", "iPhone 11");
            desiredCapabilities.setCapability("platformVersion", "16.4");
            desiredCapabilities.setCapability("app",
                    "/Users/movchinnikov/IdeaProjects/JavaAppiumAutomation/apps/Wikipedia.app");
            desiredCapabilities.setCapability("automationName", "XCUITest");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }

        return desiredCapabilities;
    }

    private void setDriverByPlatformEnv(DesiredCapabilities desiredCapabilities) throws Exception {
        String platform = System.getenv("PLATFORM");

        if (platform.equals(PLATFORM_ANDROID)) {
            driver = new AndroidDriver<>(new URL(APPIUM_URL), desiredCapabilities);
        } else if (platform.equals(PLATFORM_IOS)) {
            driver = new IOSDriver(new URL(APPIUM_URL), desiredCapabilities);
        } else {
            throw new Exception("Cannot get driver from env variable. Platform value " + platform);
        }
    }
}