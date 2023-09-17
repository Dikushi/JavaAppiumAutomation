package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            SAVE_TO_LIST_BUTTON,
            ACTION_BUTTON_AFTER_SAVE,
            INPUT_NAME_FOR_FOLDER,
            OK_BUTTON_FOR_CREATE_LIST,
            TITLE_LOCATOR,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getTitleElement(String substring) {
        return TITLE.replace("{SUBSTRING}", substring);
    }

    public WebElement waitForTitleElement(String substring) {
        String titleWithSubstring = getTitleElement(substring);
        return this.waitForElementPresent(titleWithSubstring, "Cannot find article title on page", Duration.ofSeconds(15));
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        }
    }

    public void addArticleToList(String nameOfFolder) {
        this.waitForElementAndClick(
                SAVE_TO_LIST_BUTTON,
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(
                ACTION_BUTTON_AFTER_SAVE,
                "Cannot find button 'Add to List'"
        );

        this.waitForElementAndClear(
                INPUT_NAME_FOR_FOLDER,
                "Cannot find input to set name of articles folder",
                Duration.ofSeconds(5)
        );

        this.waitForElementAndSendKeys(
                INPUT_NAME_FOR_FOLDER,
                nameOfFolder,
                "Cannot put text into articles folder input"
        );

        this.waitForElementAndClick(
                OK_BUTTON_FOR_CREATE_LIST,
                "Cannot press 'OK' button"
        );

        this.waitForElementAndClick(
                ACTION_BUTTON_AFTER_SAVE,
                "Cannot find button 'Add to List'"
        );
    }

    public void addArticleToListAlreadyCreated(String nameOfFolder) {
        this.waitForElementAndClick(
                SAVE_TO_LIST_BUTTON,
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(
                ACTION_BUTTON_AFTER_SAVE,
                "Cannot find button 'Add to List'"
        );

        this.waitForElementAndClick(
                "xpath://*[@text='" + nameOfFolder + "']",
                "Cannot press to already exist folder"
        );

        this.waitForElementAndClick(
                ACTION_BUTTON_AFTER_SAVE,
                "Cannot find button 'View List'"
        );
    }

    public void assertTitleIsPresent() {
        this.assertElementPresent(
                TITLE_LOCATOR,
                "Title is not displayed in Article"
        );
    }

    public void addArticlesToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(
                SAVE_TO_LIST_BUTTON,
                "Cannot find and click 'Save to list' button");
    }

    public void removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove article from saved"
            );
            this.waitForElementPresent(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before",
                    Duration.ofSeconds(5)
            );
        }
    }
}