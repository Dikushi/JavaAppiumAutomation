package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/fragment_onboarding_skip_button";
        SEARCH_INPUT = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/android.view.ViewGroup";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results']";
        SEARCH_LINE_LOCATOR = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//android.widget.ImageView";
        SEARCH_RESULTS_LIST_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//android.view.ViewGroup";
    }

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}