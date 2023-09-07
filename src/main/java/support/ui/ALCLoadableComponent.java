package support.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;

import misc.Setup;

public abstract class ALCLoadableComponent<T extends ALCLoadableComponent<T>> extends LoadableComponent<T> {

	protected final Setup testContainer;
	protected final WebDriver driver;
	
	protected ALCLoadableComponent(Setup testContainer) {
		this.testContainer = testContainer;
		driver = testContainer.driver;
	}

	protected void clearAndType(WebElement field, String text) {
		field.clear();
		field.sendKeys(text);
	}

}
