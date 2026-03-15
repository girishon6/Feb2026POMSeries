package com.qa.opencart.factory;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	private static final org.apache.logging.log4j.Logger log= LogManager.getLogger(OptionsManager.class);

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co=new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			co.addArguments("--headless");
			//System.out.println("Running in headless mode");
			log.info("Running in headless mode");
			
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			co.addArguments("--incognito");
			//System.out.println("Running in incognito mode");
			log.info("Running in incognito mode");
		}
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo=new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			fo.addArguments("--headless");
			//System.out.println("Running in headless mode");
			log.info("Running in headless mode");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			fo.addArguments("-private");
			//System.out.println("Running in incognito mode");
			log.info("Running in incognito mode");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo=new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			eo.addArguments("--headless");
			//System.out.println("Running in headless mode");
			log.info("Running in headless mode");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			eo.addArguments("--inprivate");
			//System.out.println("Running in incognito mode");
			log.info("Running in incognito mode");
		}
		return eo;
	}

}
