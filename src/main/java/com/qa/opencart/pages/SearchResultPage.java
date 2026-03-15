package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class SearchResultPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);

	}

	private By productResults = By.cssSelector("div.product-thumb");

	public int getProductResultsCount() {
		List<WebElement> productsEle =eleUtil.waitForElementsVisible(productResults,AppConstants.DEFAULT_TIME_OUT);
		int count=productsEle.size();
		System.out.println("product Result count:==>"+count);
		return count;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Product Name:===>"+productName);
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
		
	}

}
