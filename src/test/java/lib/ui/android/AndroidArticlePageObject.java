package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "xpath://*[@resource-id='pcs']//android.widget.TextView[@text='{SUBSTRING}']";
        FOOTER_ELEMENT = "xpath://*[@text='View article in browser']";
        SAVE_TO_LIST_BUTTON = "id:org.wikipedia:id/page_save";
        ACTION_BUTTON_AFTER_SAVE = "id:org.wikipedia:id/snackbar_action";
        INPUT_NAME_FOR_FOLDER = "id:org.wikipedia:id/text_input";
        OK_BUTTON_FOR_CREATE_LIST = "xpath://*[@text='OK']";
        TITLE_LOCATOR = "xpath://*[@resource-id='pcs']//*[contains(@resource-id,'title')]";
    }

    public AndroidArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}