package lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_LINE_LOCATOR,
            SEARCH_RESULTS_LIST_ELEMENT;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void initSearchInput() {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element", Duration.ofSeconds(5));
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element");
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", Duration.ofSeconds(5));
    }

    public void waitForCancelButtonToDisAppear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", Duration.ofSeconds(5));
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button");
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndClick(SEARCH_INPUT, "Cannot find and click search input");
        this.waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input");
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(searchResultXpath, "Cannot find search result with substring " + substring, Duration.ofSeconds(5));
    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(searchResultXpath, "Cannot find and click search result with substring " + substring);
    }

    public int getAmountOfFindArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                Duration.ofSeconds(15)
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element.",
                Duration.ofSeconds(15)
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.waitForElementNotPresent(
                SEARCH_LINE_LOCATOR,
                "We supposed not to find any results",
                Duration.ofSeconds(15)
        );
    }

    public int getAmountOfResultListElements() {
        return this.getAmountOfElements(SEARCH_RESULTS_LIST_ELEMENT);
    }

    public List<WebElement> getListOfResultElements() {
        WebElement resultsOfSearch = this.waitForElementPresent(
                "id:org.wikipedia:id/search_results_list",
                "Cannot find result of search",
                Duration.ofSeconds(15)
        );

        return resultsOfSearch.findElements(By.id("org.wikipedia:id/page_list_item_title"));
    }

    public void assertListOfResultElementsHaveText(List<WebElement> listOfResultElements, String text) {
        for (WebElement resultElement : listOfResultElements) {
            assertTrue(
                    String.format("Result of search contains result without '%s' text.", text),
                    resultElement.getText().toLowerCase().contains(text));
        }
    }
}