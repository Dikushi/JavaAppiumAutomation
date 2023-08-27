package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "//android.widget.TextView[@text='{SUBSTRING}']",
            FOOTER_ELEMENT = "//*[@text='View article in browser']",
            SAVE_TO_LIST_BUTTON = "org.wikipedia:id/page_save",
            ACTION_BUTTON_AFTER_SAVE = "org.wikipedia:id/snackbar_action",
            INPUT_NAME_FOR_FOLDER = "org.wikipedia:id/text_input",
            OK_BUTTON_FOR_CREATE_LIST = "//*[@text='OK']",
            TITLE_LOCATOR = "//*[@resource-id='pcs']//*[contains(@resource-id,'title')]";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getTitleElement(String substring) {
        return TITLE.replace("{SUBSTRING}", substring);
    }

    public WebElement waitForTitleElement(String substring) {
        String titleWithSubstring = getTitleElement(substring);
        return this.waitForElementPresent(By.xpath(titleWithSubstring), "Cannot find article title on page", 15);
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToList(String nameOfFolder) {
        this.waitForElementAndClick(
                By.id(SAVE_TO_LIST_BUTTON),
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(
                By.id(ACTION_BUTTON_AFTER_SAVE),
                "Cannot find button 'Add to List'"
        );

        this.waitForElementAndClear(
                By.id(INPUT_NAME_FOR_FOLDER),
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                By.id(INPUT_NAME_FOR_FOLDER),
                nameOfFolder,
                "Cannot put text into articles folder input"
        );

        this.waitForElementAndClick(
                By.xpath(OK_BUTTON_FOR_CREATE_LIST),
                "Cannot press 'OK' button"
        );

        this.waitForElementAndClick(
                By.id(ACTION_BUTTON_AFTER_SAVE),
                "Cannot find button 'Add to List'"
        );
    }

    public void addArticleToListAlreadyCreated(String nameOfFolder) {
        this.waitForElementAndClick(
                By.id(SAVE_TO_LIST_BUTTON),
                "Cannot find option to add article to reading list"
        );

        this.waitForElementAndClick(
                By.id(ACTION_BUTTON_AFTER_SAVE),
                "Cannot find button 'Add to List'"
        );

        this.waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot press to already exist folder"
        );

        this.waitForElementAndClick(
                By.id(ACTION_BUTTON_AFTER_SAVE),
                "Cannot find button 'View List'"
        );
    }

    public void assertTitleIsPresent() {
        this.assertElementPresent(
                By.xpath(TITLE_LOCATOR),
                "Title is not displayed in Article"
        );
    }
}