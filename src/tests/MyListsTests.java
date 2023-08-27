package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        String articleTitle = "Java (programming language)";
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
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        String firstArticleTitle = "Java (programming language)";
        String secondArticleTitle = "JavaScript";
        String nameOfFolder = "Ex5. Save Two Articles.";

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        articlePageObject.addArticleToList(nameOfFolder);

        this.navigateBack();
        this.navigateBack();

        searchPageObject.clickByArticleWithSubstring("High-level programming language");

        articlePageObject.addArticleToListAlreadyCreated(nameOfFolder);

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);

        myListsPageObject.swipeByArticleToDelete(secondArticleTitle);
        myListsPageObject.waitForArticleToDisappearByTitle(secondArticleTitle);
        myListsPageObject.clickOnArticleFromList(firstArticleTitle);

        articlePageObject.waitForTitleElement(firstArticleTitle);
    }
}