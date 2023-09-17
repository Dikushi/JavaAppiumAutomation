package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String login = "Gsigpwg32";
    private static final String password = "Gsigpwg32123";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        String articleTitle = "Java (programming language)";
        String nameOfFolder = "Learning programming";

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(articleTitle);
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(articleTitle);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToList(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement("bject-oriented programming language");

            articlePageObject.addArticlesToMySaved();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();

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
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);

        if (Platform.getInstance().isMW()) {
            navigationUI.openNavigation();

            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement("bject-oriented programming language");
        }

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToList(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.navigateBack();
            this.navigateBack();
            this.navigateBack();
        } else {
            this.driver.navigate().back();
        }

        searchPageObject.clickByArticleWithSubstring("igh-level programming language");

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToListAlreadyCreated(nameOfFolder);
        } else {
            articlePageObject.addArticlesToMySaved();
        }

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            myListsPageObject.swipeByArticleToDelete(secondArticleTitle);
            myListsPageObject.waitForArticleToDisappearByTitle(secondArticleTitle);
            myListsPageObject.clickOnArticleFromList(firstArticleTitle);
            articlePageObject.waitForTitleElement(firstArticleTitle);
        } else {
            navigationUI.openNavigation();
            navigationUI.clickMyLists();

            myListsPageObject.swipeByArticleToDelete(secondArticleTitle);
            myListsPageObject.waitForArticleToDisappearByTitle(secondArticleTitle);
        }
    }
}