package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        SAVE_TO_LIST_BUTTON = "id:Save for later";
    }

    public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}