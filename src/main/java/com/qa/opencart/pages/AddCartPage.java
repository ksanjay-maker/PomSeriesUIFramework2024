package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class AddCartPage {

	String name = "iMAC";
	
	By locater = By.id("product");
	
	public void click() {
		System.out.println("click--" + locater);
	}
}
