package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
//import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.SearchResultPage;

//@Listeners(ChainTestListener.class)
public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	
	protected Properties prop;
	
	protected LoginPage loginpage;
	protected HomePage homepage;
	protected SearchResultPage searchresultpage;
	protected ProductInfoPage productInfoPage;
	protected CommonsPage commonsPage;

	@Parameters(value="browser")
	@BeforeTest(description="Setting up browser")
	public void setUp(String value) {
		df=new DriverFactory();
		prop=df.initProp();
		if(value!=null) {
			prop.setProperty("browser", value);
		}
		driver=df.initDriver(prop);
		loginpage=new LoginPage(driver);
		commonsPage=new CommonsPage(driver);
		
		ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
		ChainPluginService.getInstance().addSystemInfo("Owner#", "Girish");
		ChainPluginService.getInstance().addSystemInfo("headless#", prop.getProperty("headless"));
		ChainPluginService.getInstance().addSystemInfo("incognito#", prop.getProperty("incognito"));
	}
	
	@AfterMethod(description="Attaching Screenshot")
	public void attachScreenShot(ITestResult result) {
		if (!result.isSuccess()) {
			ChainTestListener.embed(DriverFactory.getScreenShotByFile(), "image/png");
		}

	}

	@AfterTest(description="Closing the browser")
	public void tearDown() {
		driver.quit();
	}

}
