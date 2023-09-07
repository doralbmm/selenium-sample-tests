package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.ExistingGoogleSearchPage;
import pages.FluentGoogleSearchPage;
import pages.GoogleSearchPage;
import misc.Setup;

public class GoogleSearchNew extends Setup {
    @Test(priority = 0)
    @Parameters({"search_query"})
    public void testGoogleSearchDirect(@Optional("selenium") String search_query) {
        driver.get("https://www.google.com/");
        driver.findElement(By.name("q")).sendKeys(search_query);
        Assert.assertEquals(driver.findElement(By.name("q")).getAttribute("value"), search_query);
        driver.findElement(By.name("btnK")).click();

        testPassed = true;
    }

    @Test(priority = 1)
    public void testGoogleSearchCurrent() {
        driver.get("https://www.google.com/");
        ExistingGoogleSearchPage gsp = new ExistingGoogleSearchPage(driver);
        gsp.setQuery("selenium");
        gsp.assertQuery("selenium");
        gsp.clickSearch();

        testPassed = true;
    }

    @Test(priority = 1)
    public void testGoogleSearchCurrentShortDSL() {
        driver.get("https://www.google.com/");
        ExistingGoogleSearchPage gsp = new ExistingGoogleSearchPage(driver);
        gsp.searchFor("selenium");

        testPassed = true;
    }

    @Test(priority = 2)
    public void testGoogleSearchPageObject() {
        (new GoogleSearchPage(this))
                .get()
                .setQuery("selenium")
                .assertQuery("selenium")
                .submitQuery()
                .get()
                .assertResultPageQuery("selenium");

        testPassed = true;
    }

    @Test(priority = 2)
    public void testGoogleSearchPageDSL() {
        (new GoogleSearchPage(this))
                .get()
                .searchFor("selenium")
                .get()
                .assertResultPageQuery("selenium");

        testPassed = true;
    }

    @Test(priority = 3)
    public void testGoogleSearchPageFluent() {
        (new FluentGoogleSearchPage(this))
                .withFluent()
                .setQuery("selenium")
                .assertQuery("selenium")
                .submitQuery()
                .assertResultPageQuery("selenium");

        testPassed = true;
    }

    @Test(priority = 3)
    public void testGoogleSearchPageFluentDSL() {
        (new FluentGoogleSearchPage(this))
                .withFluent()
                .searchFor("selenium")
                .assertResultPageQuery("selenium");

        testPassed = true;
    }
}
