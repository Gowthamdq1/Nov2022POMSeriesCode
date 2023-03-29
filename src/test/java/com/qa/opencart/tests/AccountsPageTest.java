package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());	
	}
	@Test
	public void accPageTitleTest() {
	String actualTitle = accPage.getAccountsPageTitle();
	Assert.assertEquals(actualTitle, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}
	@Test
	public void accPageURLTest() {
	String actualURL = accPage.getAccountsPageURL();
	Assert.assertTrue(actualURL.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}
	@Test
	public void isLogOutLinkExistTest() {
		Assert.assertTrue(accPage.isLogOutLinkExist());
	}
	@Test
	public void accPageHeadersTest() {
		List<String> actualAccPageHeadersList = accPage.getAccountsPageHeadersList();
		System.out.println("Acc Page Headers List : "+actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList.size(), AppConstants.ACCOUNT_PAGE_HEADERS_COUNT);
	}
	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccPageHeadersList = accPage.getAccountsPageHeadersList();
		System.out.println("Actual Acc Page Headers List : "+actualAccPageHeadersList);
		System.out.println("Expected Acc Page Headers List : "+AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccPageHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
//			{"Samsung"},
//			{"Apple"},
		};
	}
	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductsCount()>0);
	}
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"Macbook", "MacBook Air"},
//			{"iMac","iMac"},
//			{"Samsung","Samsung SyncMaster 941BW"},
//			{"Apple","Apple Cinema 30\""},
		};
	}
	@Test(dataProvider = "getProductTestData")
	public void searchProductTest(String searchKey, String productName) {
		searchPage = accPage.performSearch(searchKey);
		
		if(searchPage.getSearchProductsCount()>0) {
			ProductInfoPage = searchPage.selectProduct(productName);
			String actProductHeader = ProductInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
		}
	}
	
	
	
	
	
	

}
