package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppErrorConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100:Design login page for Opencart")
@Feature("Feature 50: Login page Feature ")
@Story("US 101: Design various features of Opencart login page")
@Owner("Girish")
public class LoginPageTest extends BaseTest {
	
	@Description("Checking Login Page Title")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void getLoginPageTitleTest() {
		String title=loginpage.getLoginPageTitle();
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE,AppErrorConstants.TITLE_NOT_FOUND_ERROR);
	}
	
	@Description("Checking Login page url")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void getLoginPageUrlTest() {
		String url=loginpage.getLoginPageUrl();
		Assert.assertTrue(url.contains(AppConstants.LOGIN_PAGE_URL_FRACTION),AppErrorConstants.URL_NOT_FOUND_ERROR);
	}
	
	@Description("Checking Forgot Password Link Exist or not")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled=false)
	public void isForgotPasswordLinkExistTest() {
		Assert.assertTrue(loginpage.isForgotPasswordLinkExist(),AppErrorConstants.ELEMENT_NOT_FOUND_ERROR);
	}
	
	@Description("Checking user is able to login or not with right credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=Integer.MAX_VALUE)
	public void loginTest() {
		homepage = loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(homepage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppErrorConstants.TITLE_NOT_FOUND_ERROR);
		
	}
	
	@Description("Checking user is able to login or not with right credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void getImageTest() {
		Assert.assertTrue(commonsPage.getImage(),AppErrorConstants.ELEMENT_NOT_FOUND_ERROR);
	}

	@DataProvider
	public Object[][] footerData() {
		return new Object[][] { { "About Us" },
								{ "Contact Us" },
								{ "Gift Certificates" },
								{ "Newsletter" } };

	}

	@Description("Checking Footer List is present or not")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "footerData")
	public void checkFooterListTest(String footerText) {

		Assert.assertTrue(commonsPage.checkFooterList(footerText));
	}
}
