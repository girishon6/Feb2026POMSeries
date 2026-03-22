package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.frameworkExceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(DriverFactory.class);
	boolean remote;

	public WebDriver initDriver(Properties properties) {
		
		String browser=properties.getProperty("browser");
		highlight=properties.getProperty("highlight");
		optionsManager=new OptionsManager(properties);
		log.info("browser name is: "+browser);
		remote=Boolean.parseBoolean(properties.getProperty("remote"));
		log.info("remote value: "+remote);

		switch (browser.trim().toLowerCase()) {

		case "chrome":
			if (remote) {
				//run tcs on remote
				initRemoteDriver("chrome");
			} else {
				//local execution
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				// driver = new ChromeDriver(optionsManager.getChromeOptions());
			}
			break;
		case "edge":
			if (remote) {
				initRemoteDriver("edge");
			} else {
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			}
			break;
		case "firefox":
			if (remote) {
				initRemoteDriver("firefox");
			} else {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			}
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			// driver = new SafariDriver();
			break;
		default:

			log.error("Plz pass valid browser:" + browser);
			throw new FrameworkException("Invalid browser:" + browser + "Entered");
		}
		
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(properties.getProperty("url"));
		
		return getDriver();
	}
	

	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public Properties initProp() {

		FileInputStream fis=null;
		String env = System.getProperty("env");
		System.out.println("running");
		prop = new Properties();
		// mvn clean install -Denv="qa"

		try {
			if (env == null) {
				log.info("no env passed hence running on default envt");
				fis = new FileInputStream(AppConstants.CONFIG_PROP_FILE_PATH);
			} else {
				switch (env.trim().toLowerCase()) {
				case "qa":
					fis = new FileInputStream(AppConstants.CONFIG_PROP_QA_FILE_PATH);
					break;
				case "uat":
					fis = new FileInputStream(AppConstants.CONFIG_PROP_UAT_FILE_PATH);
					break;
				case "stage":
					fis = new FileInputStream(AppConstants.CONFIG_PROP_STAGE_FILE_PATH);
					break;

				default:
					System.out.println("please pass the right env name..."+env);
					throw new FrameworkException("==INVALID ENV==");

				}
				
			}
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}
	
	/**
	 * init the remote driver with Selenium grid
	 * @param browser
	 */
	private void initRemoteDriver(String browser) {
		log.info("Running tests on remote grid: "+browser);
		try {
			switch (browser.toLowerCase().trim()) {

			case "chrome":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;

			default:
				log.info("Browser is not supported on GRID:"+browser);
				break;

			}

		} catch (MalformedURLException e) {
			e.getStackTrace();
		}
	}
	
	public static String getScreenShotByPath() {
		File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		File destination=new File(path);
		
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;		
	}
	
	public static File getScreenShotByFile() {
		File file=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		return file;
	}
	
	public static byte[] getScreenShotByByte() {
		byte[] b=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);
		return b;
	}
	
	public static String getScreenShotByBase() {
		String base = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64);
		return base;
	}
}
