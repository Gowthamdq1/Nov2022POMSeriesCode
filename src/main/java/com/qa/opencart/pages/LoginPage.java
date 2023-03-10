package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	//Declared private : To restrict from changing the details
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//private By locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");	
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	//Page constructor
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//Page actions/methods
	@Step("Getting the login page title....")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login page title : "+title);
		return title;
	}
	@Step("Getting the login page URl....")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("Login page url : "+url);
		return url;
	}
	@Step("Getting the Forgot pwd link....")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	@Step("Login with username : {0} and password{1}")
	public AccountsPage doLogin(String un, String pwd) {
		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, "Test@123");
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	@Step("Navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
		
	}
	
}
