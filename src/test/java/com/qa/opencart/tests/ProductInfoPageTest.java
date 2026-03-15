package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void ProductInfoPageSetUp() {
		homepage = loginpage.doLogin("test200@gmail.com", "Test$123");
	}

	@Test
	public void getProductHeaderTest() {
		searchresultpage = homepage.doSearch("macbook");
		productInfoPage = searchresultpage.selectProduct("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");

	}
	
	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"macbook","MacBook Air",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7}
			
			};
	}
	
	@DataProvider
	public Object[][] getProductSheetData() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
	}
	
	@Test(dataProvider="getProductSheetData")
	public void getProductImageCountTest(String searchKey,String productName, String imgCount) {
		ChainTestListener.log(searchKey+" : "+productName+" : "+imgCount);
		searchresultpage = homepage.doSearch(searchKey);
		productInfoPage = searchresultpage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImageCount(),Integer.parseInt(imgCount));
	}
	
	@Test
	public void getProductInfoTest() {
		searchresultpage = homepage.doSearch("macbook");
		productInfoPage = searchresultpage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfo();
		
		productInfoMap.forEach((k,v)->System.out.println(k+":"+v));
		
		SoftAssert softAssert=new SoftAssert();
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
	}
	

}
