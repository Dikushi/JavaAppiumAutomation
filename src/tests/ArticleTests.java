package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticleTests extends CoreTestCase {

    MainPageObject mainPageObject;

    @Test
    public void testArticleTitleIsDisplayed() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        WebElement articleTitle = articlePageObject.waitForTitleElement("Java");

        assertTrue("Is not visible!", articleTitle.isDisplayed());
    }

    @Test
    public void testSwipeArticle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement("Appium");
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testOpenArticleAndAssertTitle() {
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
                "Cannot find result"
        );

        mainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find title of article",
                15
        );

        String locatorOfTitle = "//*[@resource-id='pcs']//*[contains(@resource-id,'title')]";
        mainPageObject.assertElementPresent(
                By.xpath(locatorOfTitle),
                "Title is not displayed in Article"
        );
    }
}