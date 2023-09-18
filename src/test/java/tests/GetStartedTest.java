package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests for intro app")
public class GetStartedTest extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Intro")})
    @DisplayName("Test intro notification on first run")
    @Description("Checking text on intro notification and click next")
    @Step("Starting test testPassThroughWelcome()")
    @Severity(value = SeverityLevel.NORMAL)
    public void testPassThroughWelcome() {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            return;
        }

        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWayToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForAddOrEditPreferredLangText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollectedText();
        welcomePageObject.clickGetStartedButton();
    }
}