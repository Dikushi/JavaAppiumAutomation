package lib.ui;

import io.appium.java_client.AppiumDriver;

import java.time.Duration;

public class MyListsPageObject extends MainPageObject {

    public static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}'",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public void openFolderByName(String nameOfFolder) {
        this.waitForElementAndClick(
                getFolderXpathByName(nameOfFolder),
                "Cannot find folder name " + nameOfFolder
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        this.swipeElementToLeft(
                getSavedArticleXpathByTitle(articleTitle),
                "Cannot find saved article"
        );
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        this.waitForElementPresent(
                getSavedArticleXpathByTitle(articleTitle),
                "Cannot find saved article by title " + articleTitle,
                Duration.ofSeconds(15)
        );
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        this.waitForElementNotPresent(
                getSavedArticleXpathByTitle(articleTitle),
                "Saved article still present with title " + articleTitle,
                Duration.ofSeconds(15)
        );
    }

    public void clickOnArticleFromList(String articleTitle) {
        this.waitForElementAndClick(
                getSavedArticleXpathByTitle(articleTitle),
                "Cannot find article to click by title " + articleTitle
        );
    }
}