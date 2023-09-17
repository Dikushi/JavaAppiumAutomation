package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        String articleTitle = "Java (programming language)";
        String nameOfFolder = "Learning programming";

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(articleTitle);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(articleTitle);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToList(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameOfFolder);
        }

        myListsPageObject.swipeByArticleToDelete("Java (programming language)");
    }

    @Test
    public void testSaveToFavoriteTwoArticles() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        String firstArticleTitle = "Java (programming language)";
        String secondArticleTitle = "JavaScript";
        String nameOfFolder = "Ex5. Save Two Articles.";

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.addArticleToList(nameOfFolder);

        this.navigateBack();
        this.navigateBack();
        this.navigateBack();

        searchPageObject.clickByArticleWithSubstring("High-level programming language");

        articlePageObject.addArticleToListAlreadyCreated(nameOfFolder);

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        myListsPageObject.swipeByArticleToDelete(secondArticleTitle);
        myListsPageObject.waitForArticleToDisappearByTitle(secondArticleTitle);
        myListsPageObject.clickOnArticleFromList(firstArticleTitle);

        articlePageObject.waitForTitleElement(firstArticleTitle);
    }
}