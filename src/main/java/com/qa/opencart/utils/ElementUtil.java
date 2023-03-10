package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
		jsUtil.flash(element);
		}
		return element; 	
	}
	public WebElement getElement(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut); 	
	}
	public void doActionSendKeys(By locator , String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();;
	}
	public void doSendKeys(By locator,String value) {
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	public void doClick(By locator) {
		getElement(locator).click();
	}
	public boolean doElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	public String getElementAttribute(By locator, String attribute) {
		return getElement(locator).getAttribute(attribute);
	}
	public List<WebElement> getElements(By locator){
		return driver.findElements(locator);
	}
	public void getElementAttributes(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		for(WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
			System.out.println(attrValue);
		}}
	public int getTotalElementsCount(By locator) {
		int elecount = getElements(locator).size();
		System.out.println("Total Elements for : "+ locator +" ----> "+elecount);
		return elecount;
	}
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}
	public List<String> getElementsText(By locator) {
		List<String> eleTextList = new ArrayList<String>();
		List<WebElement> eleList = getElements(locator);
		for(WebElement e : eleList) {
			String text = e.getText();
			eleTextList.add(text);
		}
		return eleTextList;
	}
	//**********************wait utils *************************************
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 * Presence of element will check inside the DOM only
	 */
	public WebElement waitForElementPresence(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 * It will check height and width are greater than 0 on webpage
	 */
	public WebElement waitForElementVisible(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator,int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	public  Alert waitForAlertPresence(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return  wait.until(ExpectedConditions.alertIsPresent());
	}
	public  String getAlertText(int timeOut) {
		return waitForAlertPresence(timeOut).getText();
	}
	public  void acceptAlert(int timeOut) {
		waitForAlertPresence(timeOut).accept();
	}
	public  void dismissAlert(int timeOut) {
		waitForAlertPresence(timeOut).dismiss();
	}
	public  void alertSendKeys(int timeOut, String Value) {
		waitForAlertPresence(timeOut).sendKeys(Value);
	}
	public  String waitForTitleContainsAndFetch(int timeOut, String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleContains(titleFractionValue));
		return driver.getTitle();
	}
	public  String waitForTitleIsAndFetch(int timeOut, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.titleIs(titleValue));
		return driver.getTitle();
	}
	public  String waitForURLContainsAndFetch(int timeOut, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlContains(titleValue));
		return driver.getCurrentUrl();
	}
	public  boolean waitForURLIsAndFetch(int timeOut, String urlValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.urlToBe(urlValue));
	}

	public  boolean waitForURLContains(int timeOut, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.urlContains(titleValue));
	}
	public  void WaitForFrameAndSwitchToIt(int timeOut, String IdORName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("main"));
	}
	public  void WaitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	public  void WaitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	public  void WaitForFrameAndSwitchToItByFrameElement(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param timeOut
	 * @param locator
	 */
	public void clickWhenReady(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	public WebElement waitForElementToBeClickable(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	public void doClickWithActionsAndWait(int timeOut, By locator) {
		WebElement ele = waitForElementToBeClickable(timeOut, locator);
		Actions act = new Actions(driver);
		act.click(ele).build().perform();
	}
	//********************** Select based dropdown utils ******************
	public void doSelectDropdownByIndex(By locator,int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	public void doSelectDropdownByValue(By locator,String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	public void doSelectDropdownByVisibleText(By locator,String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	public List<WebElement> getDropdownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}
	public List<String> getDropdownOptionsTextList(By locator) {
		List<WebElement> optionsList = getDropdownOptionsList(locator);
		List<String> optionsTextsList = new ArrayList<String>();
		for(WebElement e : optionsList) {
			String text = e.getText();
			optionsTextsList.add(text);
		}
		return optionsTextsList;
	}
	public void selectDropdownValue(By locator,String expValue) {
		List<WebElement>optionsList = getDropdownOptionsList(locator);
		for(WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if(text.equals(expValue)) {
				e.click();
				break;
			}}}
	public int getTotalDropdownOptions(By locator) {
		int optionsCount = getDropdownOptionsList(locator).size();
		System.out.println("Total Options =========> "+optionsCount);
		return optionsCount;
		}
	public void doSearch(By suggList,String suggName) {
		List<WebElement> suggListt = getElements(suggList);
		System.out.println(suggListt.size());
		
		for(WebElement e : suggListt) {
			String text = e.getText();
			System.out.println(text);
			if(text.contains(suggName)) {
				e.click();
				break;
			}
		}
	
	}
}
