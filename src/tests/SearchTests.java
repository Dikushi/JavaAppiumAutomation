package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

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
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        assertTrue(
                "Result of search more then zero results",
                searchPageObject.getAmountOfResultListElements() > 0
        );

        searchPageObject.clickCancelSearch();

        assertTrue(
                "Result of search is zero results",
                searchPageObject.getAmountOfResultListElements() == 0
        );
    }

    @Test
    public void testCheckTextInSearchResults() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        List<WebElement> listOfResultElements = searchPageObject.getListOfResultElements();

        searchPageObject.assertListOfResultElementsHaveText(listOfResultElements, "Java");
    }
}