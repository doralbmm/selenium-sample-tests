package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import support.ui.ALCLoadableComponent;
import misc.Setup;

import java.time.Duration;

public class GoogleResultPage extends ALCLoadableComponent<GoogleResultPage> {

	protected final String pageUrlFragment = "https://www.google.com/search?";

	private final String queryFieldName = "q";
	@FindBy(name = queryFieldName)
	private WebElement query;

	private final String searchResultsId = "rso";
	@FindBy(id = searchResultsId)
	private WebElement searchResults;

	public GoogleResultPage(Setup testContainer) {
		super(testContainer);

		PageFactory.initElements(driver, this);
	}

	private boolean isloaded = false;

	@Override
	protected void load() {
		// This page is reached by submitting the search form so wait until the search
		// result content is loaded.
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(searchResultsId)));
		isloaded = true;
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(isloaded, "The page is not loaded!");
	}

	public GoogleResultPage assertResultPageQuery(String queryString) {
		Assert.assertEquals(query.getAttribute("value"), queryString);

		return this;
	}

}
