package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class SearchTests extends CoreTestCase {

    MainPageObject mainPageObject;

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisAppear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        String searchLine = "Linkin Park discography";
        searchPageObject.typeSearchLine(searchLine);

        int amountOfSearchResult = searchPageObject.getAmountOfFindArticles();

        assertTrue(
                "We found too few results!",
                amountOfSearchResult > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        String searchLine = "zxcvasdfqwer";
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testCancelResultSearch() {
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Onboarding Skip Button"
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input"
        );

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input"
        );

        WebElement resultsOfSearch = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find result of search",
                15
        );

        assertNotEquals(
                "Result of search more then zero results",
                0,
                resultsOfSearch.findElements(By.className("android.view.ViewGroup")).size()
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search"
        );

        assertEquals(
                "Result of search is zero results",
                0,
                resultsOfSearch.findElements(By.className("android.view.ViewGroup")).size()
        );
    }

    @Test
    public void testCheckTextInSearchResults() {
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find Onboarding Skip Button"
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input"
        );

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input"
        );

        WebElement resultsOfSearch = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find result of search",
                15
        );

        List<WebElement> webElementList = resultsOfSearch
                .findElements(By.id("org.wikipedia:id/page_list_item_title"));

        for (WebElement webElement : webElementList) {
            assertTrue(
                    "Result of search contains result without 'java' text.",
                    webElement.getText().toLowerCase().contains("java"));
        }
    }
}