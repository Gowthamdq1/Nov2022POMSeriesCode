package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest {
	
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());	
	}
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro",4},
			{"Macbook", "MacBook Air",4},
			{"iMac","iMac",3},
		};
	}
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey,String productName,int imagesCount) {
		searchPage = accPage.performSearch(searchKey);
		ProductInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = ProductInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	@Test
	public void productInfoTest() {
		searchPage = accPage.performSearch("Macbook");
		ProductInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = ProductInfoPage.getProductInfo();
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("product name"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("product price"), "$2,000.00");
		
		softAssert.assertAll();
	}
	
	//Assert vs verify ( soft assertion )
	
	
	@Test
	public void addToCartTest() {
		searchPage = accPage.performSearch("Macbook");
		ProductInfoPage = searchPage.selectProduct("MacBook Pro");
		ProductInfoPage.enterQuantity(2);
		String actCartMsg = ProductInfoPage.addProductToCart();
		//Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actCartMsg.contains("Success"));
		softAssert.assertTrue(actCartMsg.contains("MacBook Pro"));
		
		softAssert.assertEquals(actCartMsg, "Success: You have added MacBook Pro to your shopping cart!");
		softAssert.assertAll();
	}
	
}


