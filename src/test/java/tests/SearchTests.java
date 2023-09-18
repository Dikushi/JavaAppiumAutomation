package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

@Epic("Tests for search")
public class SearchTests extends CoreTestCase {

    @Test
    @Features(value = @Feature(value = "Search"))
    @DisplayName("Test searching text")
    @Description("Test searching text after run app")
    @Step("Starting test testSearch()")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Features(value = @Feature(value = "Search"))
    @DisplayName("Test cancel searching text")
    @Description("Test cancel searching text after search")
    @Step("Starting test testCancelSearch()")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisAppear();
    }

    @Test
    @Features(value = @Feature(value = "Search"))
    @DisplayName("Test amount of searching result")
    @Description("Test amount of searching result is more then zero")
    @Step("Starting test testAmountOfNotEmptySearch()")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();

        String searchLine = "Linkin Park discography";
        searchPageObject.typeSearchLine(searchLine);

        int amountOfSearchResult = searchPageObject.getAmountOfFindArticles();

        Assert.assertTrue(
                "We found too few results!",
                amountOfSearchResult > 0
        );
    }

    @Test
    @Features(value = @Feature(value = "Search"))
    @DisplayName("Test amount of searching result is zero")
    @Description("Test amount of searching result is zero")
    @Step("Starting test testAmountOfEmptySearch()")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();

        String searchLine = "zxcvasdfqwer";
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Features(value = @Feature(value = "Search"))
    @DisplayName("Test cancel result search")
    @Description("Test cancel result search and check clear")
    @Step("Starting test testCancelResultSearch()")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelResultSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        Assert.assertTrue(
                "Result of search more then zero results",
                searchPageObject.getAmountOfResultListElements() > 0
        );

        searchPageObject.clickCancelSearch();

        Assert.assertTrue(
                "Result of search is zero results",
                searchPageObject.getAmountOfResultListElements() == 0
        );
    }

    @Test
    @Features(value = @Feature(value = "Search"))
    @DisplayName("Check text in search results")
    @Description("Test text is exist in results of search")
    @Step("Starting test testCheckTextInSearchResults()")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckTextInSearchResults() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        List<WebElement> listOfResultElements = searchPageObject.getListOfResultElements();

        searchPageObject.assertListOfResultElementsHaveText(listOfResultElements, "java");
    }
}