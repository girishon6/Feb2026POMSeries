package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}

	// By locators
	private By email = By.id("input-email");
	private By password = By.id("input-password");
	private By forgotPassword = By.linkText("Forgotten Password");
	private By submitBtn = By.xpath("//input[@value='Login']");

	public String getLoginPageTitle() {
		String title=eleUtil.waitForTitleContains(AppConstants.LOGIN_PAGE_TITLE,AppConstants.MEDIUM_TIME_OUT);
		System.out.println("Login Page title:===>"+title);
		return title;
	}

	public String getLoginPageUrl() {
		String url=eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.MEDIUM_TIME_OUT);
		System.out.println("Login Page URL:===>"+url);
		ChainTestListener.log("Login Page URL:===>"+url);
		return url;
	}

	public HomePage doLogin(String username, String pwd) {
		System.out.println("Username: ==>" + username + " Password: ==>" + pwd);
		eleUtil.waitForElementVisible(email, AppConstants.DEFAULT_TIME_OUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(submitBtn);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		return new HomePage(driver);
	}

	public boolean isForgotPasswordLinkExist() {
		boolean result=eleUtil.doIsElementDisplayed(forgotPassword);
		return result;
	}

}
