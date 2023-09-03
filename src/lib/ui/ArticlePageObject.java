package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "xpath://*[@resource-id='pcs']//android.widget.TextView[@text='{SUBSTRING}']",
            FOOTER_ELEMENT = "xpath://*[@text='View article in browser']",
            SAVE_TO_LIST_BUTTON = "id:org.wikipedia:id/page_save",
            ACTION_BUTTON_AFTER_SAVE = "id:org.wikipedia:id/snackbar_action",
            INPUT_NAME_FOR_FOLDER = "id:org.wikipedia:id/text_input",
            OK_BUTTON_FOR_CREATE_LIST = "xpath://*[@text='OK']",
            TITLE_LOCATOR = "xpath://*[@resource-id='pcs']//*[contains(@resource-id,'title')]";

    public ArticlePageObject(AppiumDriver driver) {
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
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
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
}