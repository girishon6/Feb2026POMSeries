package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;

public class HomePage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}

	private By logout = By.linkText("Logout");
	private By search = By.xpath("//input[@name='search']");
	private By headers = By.cssSelector("div#content>h2");
	private By searchButton = By.cssSelector("div#search button");
	
	@Step("Get Home Page Title")
	public String getHomePageTitle() {
		String title = eleUtil.waitForTitleContains(AppConstants.HOME_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Home Page Title is:===>" + title);
		ChainTestListener.log("Home Page Title:===>"+title);
		return title;
	}

	@Step("Print Home Page Url")
	public String getHomePageUrl() {
		String url = eleUtil.waitForUrlContains(AppConstants.HOME_PAGE_URL_FRACTION, AppConstants.SHORT_TIME_OUT);
		System.out.println("Home Page URL is:===>" + url);
		return url;
	}

	@Step("Logout link is displayed")
	public boolean isLogoutLinkExist() {
		return eleUtil.doIsElementDisplayed(logout);

	}

	@Step("clicking on logout")
	public void logOut() {
		if (isLogoutLinkExist()) {
			eleUtil.doClick(logout);
		}
		// WIP
	}

	@Step("Get header List")
	public List<String> getHeadersList() {
		List<WebElement> headersListEle = eleUtil.waitForElementsVisible(headers, AppConstants.DEFAULT_TIME_OUT);
		List<String> headersList = new ArrayList<String>();
		for (WebElement e : headersListEle) {
			String headerText = e.getText();
			headersList.add(headerText);

		}
		return headersList;
	}

	@Step("Search the items with searchkey:{0}")
	public SearchResultPage doSearch(String searchKey) {
		System.out.println("search key:==>" + searchKey);
		eleUtil.doSendKeys(eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_TIME_OUT),searchKey);
		//eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_TIME_OUT).sendKeys(searchKey);
		eleUtil.doClick(searchButton);
		
		return new SearchResultPage(driver);

	}

}
