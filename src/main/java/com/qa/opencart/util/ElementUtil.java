package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);

	}
	
	public void highlightElement(WebElement ele) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);
		}
	}

	@Step("WebElement locator is{0}")
	public WebElement getElement(By locator) {
		WebElement ele = driver.findElement(locator);
		highlightElement(ele);
		return ele;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	private void nullCheck(CharSequence... text) {
		if (text == null) {
			throw new RuntimeException("=======value/attribute/property cannot be null=======");
		}
	}

	public void doSendKeys(By locator, CharSequence... text) {
		nullCheck(text);
		getElement(locator).clear();
		getElement(locator).sendKeys(text);
	}
	
	public void doSendKeys(WebElement element, CharSequence... text) {
		nullCheck(text);
		element.clear();
		element.sendKeys(text);
	}

	public void doSendKeys(String locatorType, String locatorValue, CharSequence... text) {
		nullCheck(text);
		getElement(getLocator(locatorType, locatorValue)).clear();
		getElement(getLocator(locatorType, locatorValue)).sendKeys(text);
	}

	@Step("clicking on locator:{0}")
	public void doClick(By locator) {
		WebElement ele = getElement(locator);
		ele.click();
	}

	public void doClick(String locatorType, String locatorValue) {
		WebElement ele = getElement(getLocator(locatorType, locatorValue));
		ele.click();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public By getLocator(String locatorType, String locatorValue) {
		By locator = null;

		switch (locatorType.toUpperCase().trim()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASSNAME":
			locator = By.className(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;

		default:
			break;
		}
		return locator;
	}

	@Step("Element is displayed having locator:{0}")
	public boolean doIsElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();

		} catch (NoSuchElementException e) {
			System.out.println("Element is not present in WebPage");
			return false;
		}

	}

	public boolean isElementDisplayed(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("Element is present in the webpage");
			return true;
		} else {
			System.out.println("Element is not present in the webpage");
			return false;
		}

	}

	public boolean isElementDisplayed(By locator, int count) {
		if (getElements(locator).size() == count) {
			System.out.println("Element is present in the webpage " + count + " times");
			return true;
		}
		return false;
	}

	public String doGetDomAttribute(WebElement element, String attrName) {
		nullCheck(attrName);
		return element.getDomAttribute(attrName);
	}

	public String doGetDomPropertyValue(WebElement element, String propertyName) {
		nullCheck(propertyName);
		return element.getDomProperty(propertyName);
	}

	/************************ Select dropdown util elements ***********************/

	public Select doSelectClass(By locator) {
		Select select = new Select(getElement(locator));
		return select;
	}

	public void doSelectByIndex(By locator, int index) {
		doSelectClass(locator).selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String visibleText) {
		doSelectClass(locator).selectByVisibleText(visibleText);
	}

	public void doSelectByContainsVisibleText(By locator, String partialText) {
		doSelectClass(locator).selectByContainsVisibleText(partialText);
	}

	public void doSelectByValue(By locator, String value) {
		doSelectClass(locator).selectByValue(value);
	}

	public int getDropDownSize(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> options = select.getOptions();
		return options.size();
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		List<String> optionsValueList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValueList.add(text);
		}

		return optionsValueList;
	}

	public void printDropDownOptionsText(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
		}

	}

	public void selectValueFromDropDown(By locator, String text) {
		Select select = new Select(getElement(locator));
		List<WebElement> options = select.getOptions();
		boolean flag = false;
		for (WebElement ele : options) {
			String countryName = ele.getText();
			if (countryName.equals(text)) {
				ele.click();
				flag = true;
				break;
			}

		}
		if (flag) {
			System.out.println(text + " is available and selected");
		} else {
			System.out.println(text + " is not available");
		}
	}

	public void selectValueFromDropDownUsingXpath(By locator, String value) {
		List<WebElement> country_Ele = driver.findElements(locator);
		boolean flag = false;

		for (WebElement ele : country_Ele) {
			String ctryName = ele.getText();
			if (ctryName.equals(value)) {
				ele.click();
				flag = true;
				break;
			}
		}
		if (flag == true) {

			System.out.println(value + " Option is present and selected");
		} else {
			System.out.println(value + " Option is not present");
		}
	}

	public void doSearch(By searchField, By searchKey, String text, String actualValue) {

		getElement(searchField).sendKeys(text);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		List<WebElement> suggList = getElements(searchKey);

		for (WebElement ele : suggList) {
			String searchResult = ele.getText();
			if (searchResult.equals(actualValue)) {
				ele.click();
				break;
			}
		}
	}

	public void selectChoice(By dropDownLocator, By locator, String... choices) {
		getElement(dropDownLocator).click();
		List<WebElement> jqueryDropDownOptions = getElements(locator);

		if (choices[0].equalsIgnoreCase("all")) {
			for (WebElement e : jqueryDropDownOptions) {
				e.click();
			}
		} else {
			for (WebElement e : jqueryDropDownOptions) {
				for (String str : choices) {
					String text = e.getText();
					if (text.equals(str)) {
						e.click();
					}
				}

			}

		}

	}

	/**************************** Actions *************************/

	public void handleTwoLevelMenuSubHandling(By parentMenuLocator, By childMenuLocator) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(parentMenuLocator)).build().perform();
		getElement(childMenuLocator).click();

	}

	public void dragAndDrop(By sourceLocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();

	}

	public void doActionsSendKeys(By locator, String text) {
		Actions action = new Actions(driver);
		action.sendKeys(getElement(locator), text).build().perform();

	}

	public void doActionsClick(By locator) {
		Actions action = new Actions(driver);
		action.click(getElement(locator)).build().perform();
	}
	
	public void fourLevelMenuHandling(By levelOne, By levelTwo, By levelThree, By levelFour) throws InterruptedException {
		Actions act = new Actions(driver);

		getElement(levelOne).click();
		Thread.sleep(4000);
		act.moveToElement(getElement(levelTwo)).build().perform();
		Thread.sleep(4000);
		act.moveToElement(getElement(levelThree)).build().perform();
		Thread.sleep(4000);
		act.moveToElement(getElement(levelFour)).click().build().perform();
	}
	
	
	public void doSendKeysWithPause(By locator, String input,long pauseTime) {
		Actions action=new Actions(driver);
		
		WebElement ele=getElement(locator);
		
		for(char ch:input.toCharArray()) {
			action.sendKeys(ele, String.valueOf(ch))
				  .pause(pauseTime)
				  .build()
				  .perform();
			
		}

	}
	
	//************Wait time*************//
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that isgreater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement webEle= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(webEle);
		return webEle;

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("Waiting for Element to be visible locator:{0} within time: {1}")
	public List<WebElement> waitForElementsVisible(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (Exception e) {
			System.out.println("Element not found");
		}
		return Collections.emptyList();
		

	}
	
	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring
	 * 
	 * @param title
	 * @param timeout
	 * @return
	 */
	
	@Step("Waiting for Title:{0} within time:{1}")
	public String waitForTitleContains(String title, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleContains(title)))
				return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println("Title is not found after " + timeout + "seconds");
		}
		return null;

	}

	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * 
	 * @param url
	 * @param timeout
	 * @return
	 */

	@Step("Waiting for url:{0} within time:{1}")
	public String waitForUrlContains(String url, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlContains(url)))
				return driver.getCurrentUrl();

		} catch (TimeoutException e) {
			System.out.println("Url is not found after" + timeout + "seconds");
		}
		return null;
	}
}
