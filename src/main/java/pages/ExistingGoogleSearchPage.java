package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ExistingGoogleSearchPage {

	WebDriver driver;
	
	By query = By.name("q");
	By submit = By.name("btnK");
	
	public ExistingGoogleSearchPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setQuery(String queryString) {
		driver.findElement(query).clear();
		driver.findElement(query).sendKeys(queryString);
	}

	public void assertQuery(String queryString) {
		Assert.assertEquals(driver.findElement(query).getAttribute("value"), queryString);
	}
	
	public void clickSearch() {
		driver.findElement(submit).click();
	}
	
	public void searchFor(String queryString) {
		setQuery(queryString);
		assertQuery(queryString);
		clickSearch();
	}

}
