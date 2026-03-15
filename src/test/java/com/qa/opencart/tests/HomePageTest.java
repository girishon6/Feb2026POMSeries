package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppErrorConstants;

public class HomePageTest extends BaseTest {

	@BeforeClass
	public void homePageSetup() {
		homepage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}

	@Test
	public void getHomePageTitleTest() {
		Assert.assertEquals(homepage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppErrorConstants.TITLE_NOT_FOUND_ERROR);
	}

	@Test
	public void getHomePageUrlTest() {
		Assert.assertTrue(homepage.getHomePageUrl().contains(AppConstants.HOME_PAGE_URL_FRACTION), AppErrorConstants.URL_NOT_FOUND_ERROR);
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(homepage.isLogoutLinkExist(), AppErrorConstants.ELEMENT_NOT_FOUND_ERROR);
	}

	@Test
	public void getHeadersListTest() {
		List<String> actualHeaders = homepage.getHeadersList();
		System.out.println("home page headers:===>" + actualHeaders);
	}

	@DataProvider
	public Object[][] productTestData() {
		return new Object[][] {{"macbook",3},
							{"canon",1},
							{"Samsung",2},
							{"apple",1},
							{"airtel",0}};
		
	}
	
	
	@Test(dataProvider="productTestData")
	public void searchTest(String product,int count) {
		searchresultpage = homepage.doSearch(product);
		Assert.assertEquals(searchresultpage.getProductResultsCount(), count);

	}

}
