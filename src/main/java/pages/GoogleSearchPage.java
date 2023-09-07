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

public class GoogleSearchPage extends ALCLoadableComponent<GoogleSearchPage> {

	protected final String pageUrl = "https://www.google.com/";

	private final String queryFieldName = "q";
	@FindBy(name = queryFieldName)
	private WebElement query;

	@FindBy(name = "btnK")
	private WebElement submit;

	public GoogleSearchPage(Setup testContainer) {
		super(testContainer);

		PageFactory.initElements(driver, this);
	}

	@Override
	protected void load() {
		driver.get(pageUrl);

		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(queryFieldName)));
	}

	@Override
	protected void isLoaded() throws Error {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.equalsIgnoreCase(pageUrl), "Found unexpected URL: " + currentUrl);
	}

	public GoogleSearchPage setQuery(String queryString) {
		clearAndType(query, queryString);
		
		return this;
	}

	public GoogleSearchPage assertQuery(String queryString) {
		Assert.assertEquals(query.getAttribute("value"), queryString);
		
		return this;
	}
	
	public GoogleResultPage submitQuery() {
		submit.click();
		
		return new GoogleResultPage(testContainer);
	}
	
	public GoogleResultPage searchFor(String queryString) {
		setQuery(queryString);
		assertQuery(queryString);
		return submitQuery();
	}

}
