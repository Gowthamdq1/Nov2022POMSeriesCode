package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By accsHeaders = By.cssSelector("div h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccountsPageTitle() {
		String title = eleUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
		System.out.println("Acc page title is : "+title);
		return title;
	}
	public String getAccountsPageURL() {
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE);
		System.out.println("Acc page url is : "+url);
		return url;
	}
	public boolean isLogOutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	public boolean isSearchExist() {
		return eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	public List<String> getAccountsPageHeadersList() {
		List<WebElement> accsHeadersList = eleUtil.waitForElementsVisible(accsHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> accsHeadersValList = new ArrayList<String>();
		for(WebElement e : accsHeadersList) {
			String text = e.getText();
			accsHeadersValList.add(text);
		}
		return accsHeadersValList;
	}
	public SearchPage performSearch(String searchKey) {
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
		}
		else {
			System.out.println("Search field is not present on the page....");
			return null;
		}
	}

}
