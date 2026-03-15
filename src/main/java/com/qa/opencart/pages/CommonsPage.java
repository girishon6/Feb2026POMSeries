package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class CommonsPage {
	
	WebDriver driver;
	ElementUtil eleUtil;
	public CommonsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}

	By img=By.className("img-responsive");
	By footerEle=By.xpath("//footer//div[@class='row']//a");
	
	public boolean getImage() {
		return eleUtil.doIsElementDisplayed(img);
	}
	
	public List<String> getFootersList() {
		List<WebElement> footerListEle = eleUtil.waitForElementsVisible(footerEle, AppConstants.SHORT_TIME_OUT);
		List<String> footerList = new ArrayList<String>();
		for (WebElement ele : footerListEle) {
			String eleText = ele.getText();
			footerList.add(eleText);

		}
		return footerList;
	}
	
	public boolean checkFooterList(String footerName) {
		return getFootersList().contains(footerName);
		
	}
}
