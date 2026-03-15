package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productData;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private By header = By.tagName("h1");
	private By imageCount=By.xpath("//div[@id='content']//ul[@class='thumbnails']//li");
	private By productMetaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]//li");
	private By priceMetaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]//li");


	public String getProductHeader() {
		String headerText = eleUtil.doElementGetText(header);
		System.out.println("Product Header:==>" + headerText);
		return headerText;
	}
	
	public int getProductImageCount() {
		List<WebElement> imageEle=eleUtil.waitForElementsVisible(imageCount, AppConstants.SHORT_TIME_OUT);
		int imgCount=imageEle.size();
		System.out.println("Image count:==>"+imgCount);
		return imgCount;
	}

	/**
	 * get Full product information: header, images, count, Meta data 
	 * @return
	 */
	public Map<String,String> getProductInfo() {
		productData= new HashMap<String,String>();
		//productData=new LinkedHashMap<String,String>();// we can used LinkedhashMap to store preserve the insertion order
		//productData=new TreeMap<String,String>();//to store the items in order of the sorted keys(Ascending order) then use treemap 
		productData.put("headers", getProductHeader());
		productData.put("Images count",getProductImageCount()+"");
		getProductMetaData();
		getProductPriceMetaData();
		return productData;
	}
	
	public void getProductMetaData() {
		
		List<WebElement> metaData=eleUtil.waitForElementsVisible(productMetaData, AppConstants.SHORT_TIME_OUT);

		for(WebElement ele:metaData) {
			String text=ele.getText();
			String meta[]=text.split(":");
			String key=meta[0].trim();
			String value=meta[1].trim();
			productData.put(key, value);
		}
	}
	
	public void getProductPriceMetaData() {
		List<WebElement> priceData = eleUtil.waitForElementsVisible(priceMetaData, AppConstants.SHORT_TIME_OUT);
		
			String productPrice=priceData.get(0).getText();
			String productExTax=priceData.get(1).getText().split(":")[1].trim();
			productData.put("Product Price:", productPrice);
			productData.put("Product Ex Tax:", productExTax);
		}
	}
	
	

