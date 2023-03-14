package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class OrderPage {
	
	By loc = By.id("order");
	By price = By.id("price");
	
	public void getOrder() {
		System.out.println("Get Order");
	}
	public void getPrice() {
		System.out.println("Get Price");
	}

}
