package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.MyListsPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTests extends CoreTestCase {

    MainPageObject mainPageObject;

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        String articleTitle = "Java";
        String nameOfFolder = "Learning programming";

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(articleTitle);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement(articleTitle);
        articlePageObject.addArticleToList(nameOfFolder);

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.swipeByArticleToDelete("Java (programming language)");
    }

    @Test
    public void testSaveToFavoriteTwoArticles() {
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Onboarding Skip Button"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input"
        );

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']" +
                        "//*[@text='Object-oriented programming language']"),
                "Cannot find results"
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find option to add article to reading list"
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find button 'Add to List'"
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String nameOfFolder = "Ex5. Save Two Articles.";

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button"
        );

        driver.navigate().back();

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']" +
                        "//*[@text='High-level programming language']"),
                "Cannot find results"
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find option to add article to reading list"
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find button 'Add to List'"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot press to already exist folder"
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find button 'View list'"
        );

        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='JavaScript']"),
                "Cannot find saved article"
        );

        mainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Article is not present",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Java (programming language)']"),
                "Article is not present"
        );

        mainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find title of article",
                15
        );
    }
}