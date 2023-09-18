package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

@Epic("Tests for conditions app")
public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Conditions"), @Feature(value = "Search")})
    @DisplayName("Test change screen orientation on search result")
    @Description("We open 'Java' article and change orientation device")
    @Step("Starting test testChangeScreenOrientationOnSearchResult()")
    @Severity(value = SeverityLevel.NORMAL)
    public void testChangeScreenOrientationOnSearchResult() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        String titleBeforeRotation = articlePageObject.waitForTitleElement("Java").getText();

        this.rotateScreenLandscape();

        String titleAfterRotation = articlePageObject.waitForTitleElement("Java").getText();

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                titleBeforeRotation,
                titleAfterRotation
        );

        this.rotateScreenPortrait();

        String titleAfterSecondRotation = articlePageObject.waitForTitleElement("Java").getText();

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    @Features(value = {@Feature(value = "Conditions"), @Feature(value = "Search")})
    @DisplayName("Test turn app into background on search result")
    @Description("We open 'Java' article and turn app into background")
    @Step("Starting test testCheckSearchArticleInBackground()")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckSearchArticleInBackground() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        this.backgroundApp(Duration.ofSeconds(2));

        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}