package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_LINK = "xpath://XCUIElementTypeButton[@name='Узнать подробнее о Википедии']",
            STEP_NEW_WAYS_TO_EXPLORE_TEXT = "xpath://XCUIElementTypeStaticText[@name='Новые способы изучения']",
            STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "xpath://XCUIElementTypeButton[@name='Добавить или изменить предпочтительные языки']",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "xpath://XCUIElementTypeButton[@name='Узнать подробнее о сборе данных']",
            NEXT_LINK = "xpath://XCUIElementTypeButton[@name='Далее']",
            GET_STARTED_BUTTON = "xpath://XCUIElementTypeButton[@name='Начать']",
            SKIP = "xpath://XCUIElementTypeButton[@name='Пропустить']";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(
                STEP_LEARN_MORE_LINK,
                "Cannot find link",
                Duration.ofSeconds(10));
    }

    public void clickNextButton() {
        this.waitForElementAndClick(
                NEXT_LINK,
                "Cannot find and click 'Далее' button");
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(
                STEP_NEW_WAYS_TO_EXPLORE_TEXT,
                "Cannot find text",
                Duration.ofSeconds(10));
    }

    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(
                STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK,
                "Cannot find 'Добавить или изменить предпочтительные языки' text",
                Duration.ofSeconds(10));
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(
                STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK,
                "Cannot find button with 'Узнать подробнее о сборе данных' text",
                Duration.ofSeconds(10));
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(
                GET_STARTED_BUTTON,
                "Cannot find 'Начать' button");
    }

    public void clickSkip() {
        this.waitForElementAndClick(
                SKIP,
                "Cannot find and click 'Skip' button");
    }
}