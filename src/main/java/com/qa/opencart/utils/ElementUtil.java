package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class ElementUtil {

	private WebDriver driver;
	private Select select;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	private static final String ELEMENT_NOT_FOUND_ERROR = "element is not available on the page : ";

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	public void doSendKeys(By locater, String value) {
		WebElement ele = getElement(locater);
		ele.clear();
		ele.sendKeys(value);
	}

	public WebElement getElement(By locater) {
		WebElement ele = driver.findElement(locater);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);
		}
		return ele;
	}

	public List<WebElement> getElements(By locater) {
		return driver.findElements(locater);
	}

	public void doSearch(String tagName, String text) {
		By suggLocater = By.xpath("//" + tagName + "[text()='" + text + "']");
		getElement(suggLocater).click();
	}
	
	public boolean doEleIsDisplayed(By locater) {
		return getElement(locater).isDisplayed();
	}
	
	public void doClick(By locater) {
		getElement(locater).click();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public boolean isSingleElementPresent(By locater) {
		List<WebElement> list = driver.findElements(locater);
		System.out.println(list.size());
		if (list.size() == 1) {
			System.out.println("single search element present on the page");
			return true;
		} else {
			System.out.println("no search or multiple search present on the page");
			return false;
		}
	}

	public void doSelectValueFromDropdown(By locater, String value) {
		List<WebElement> optionsList = getElements(locater);
		System.out.println(optionsList);

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);

			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void search(By serchLocater, String searchKey, By suggLocater, String value) throws InterruptedException {
		getElement(serchLocater).sendKeys(searchKey);
		Thread.sleep(3000);

		List<WebElement> suggList = getElements(suggLocater);

		System.out.println(suggList.size() - 1);

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value))
				;
			e.click();
			break;
		}

	}

	public void doSelectDropdownByIndex(By locater, int index) {
		select = new Select(getElement(locater));
		select.selectByIndex(index);
	}

	public void doSelectDropdownByVisibleText(By locater, String text) {
		select = new Select(getElement(locater));
		select.deselectByVisibleText(text);
	}

	public void doSelectDropdownByValue(By locater, String value) {
		select = new Select(getElement(locater));
		select.deselectByValue(value);
	}

	public List<WebElement> getDropDownCount(By locater) {
		Select select = new Select(getElement(locater));
		return select.getOptions();
	}

	public int getDropDownOptionsCount(By locater) {
		return getDropDownCount(locater).size();
	}

	public int getElementsCount(By locater) {
		return getElements(locater).size();
	}

	public ArrayList<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		ArrayList<String> eleTextList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				System.out.println(text);
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public void clickWhenReady(By locater, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locater)).click();
	}

	public List<WebElement> waitForElementsToBeVisible(By locater, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locater));
	}

	public WebElement waitForElementToBeVisibleWithFluentWait(By locater, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(ElementNotInteractableException.class)
				.withMessage(ElementUtil.ELEMENT_NOT_FOUND_ERROR + locater);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locater));

	}

	public WebElement retryingElement(By locater, int timeOut, int pollingTime) {

		WebElement element = null;
		int attempts = 0;

		while (attempts > timeOut) {

			try {
				element = getElement(locater);
				System.out.println("element is found in attempt: " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found in attempt : " + attempts + " : " + " for " + locater);

				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			attempts++;
		}

		if (element == null) {
			try {
				throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
			} catch (Exception e) {
				System.out.println("element is not found exception...tried for : " + timeOut + "secs"
						+ "with the interval of : " + pollingTime + "ms");
			}
		}

		return element;
	}

	// *****************wait utils***********************//

	/*
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * Parameters:locator used to find the elementReturns:the WebElement once it is
	 * located
	 */

	public WebElement waitForElementPresence(By locater, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locater));
	}

	public void doSendKeysWithWait(By locater, int timeOut, String value) {
		waitForElementPresence(locater, timeOut).sendKeys(value);
	}

	public void doClickWithWait(By locater, int timeOut) {
		waitForElementPresence(locater, timeOut).click();
	}

	public String getElementTextWithWait(By locater, int timeOut) {
		return waitForElementPresence(locater, timeOut).getText();
	}

	/*
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0. Parameters:locator used to
	 * find the elementReturns:the WebElement once it is located and visible
	 */

	public WebElement waitForElementVisible(By locater, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
	}

	public WebElement waitForElementVisible(By locater, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
	}

	public void doSendKeysWithElementVisible(By locater, int timeOut, String value) {
		waitForElementVisible(locater, timeOut).sendKeys(value);
	}

	public void doClickWithElementVisible(By locater, int timeOut) {
		waitForElementVisible(locater, timeOut).click();
	}

	public String getElementTextWithElementVisiblet(By locater, int timeOut) {
		return waitForElementVisible(locater, timeOut).getText();
	}

	public Alert waitForAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}

	public void acceptAlertt(int timeOut) {
		waitForAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}

	public void alertSendKeys(int timeOut, String value) {
		waitForAlert(timeOut).sendKeys(value);
	}

	public String waitForTitleContains(int timeOut, String titleFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		} else {
			return null;
		}
	}

	public String waitForTitleIs(int timeOut, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleIs(titleValue))) {
			return driver.getTitle();
		} else {
			return null;
		}
	}

	public String waitForUrlContains(int timeOut, String urlFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			return driver.getCurrentUrl();
		} else {
			return null;
		}
	}

	public String waitForUrlIs(int timeOut, String urlValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
			return driver.getCurrentUrl();
		} else {
			return null;
		}
	}

	public void waitForFrame(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrame(int timeOut, String nameOrId) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
	}

	public void waitForFrame(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public void waitForFrame(int timeOut, By frameLocater) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocater));
	}

}
