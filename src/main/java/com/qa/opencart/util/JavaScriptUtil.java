package com.qa.opencart.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public String getPageTitleUsingJS() {
		return js.executeScript("return document.title").toString();
	}

	public String getPageURL() {
		return js.executeScript("return document.URL").toString();
	}

	public void navigateToForwardPageUsingJS() {
		js.executeScript("history.go(1)");
	}

	public void navigateToBackwardPageUsingJS() {
		js.executeScript("history.go(-1)");
	}

	public void refreshPageUsingJS() {
		js.executeScript("history.go(0)");
	}

	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText").toString();
	}

	public void scrollPageUpUsingJS() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	public void scrollPageDownUsingJS() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public void scrollPageDownUsingJS(String height) {
		js.executeScript("window.scrollTo(0,'" + height + "')");
	}

	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor='" + color + "'", element);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgcolor, element);
		}
	}
	
	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click()",element);
	}
	
	public void sendKeysByJS(String value,String id) {
		js.executeScript("document.getElementById('"+id+"').value='"+value+"'");
	}

}
