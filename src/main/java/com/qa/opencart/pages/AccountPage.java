package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	private By accSecHeaders = By.cssSelector("div#content h2");
	private By acc = By.cssSelector("div#content h1");
	
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	@Step("getAccPagetitle...")
	public String getAccPagetitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACCOUNT_PAGE_TITLE);
		System.out.println("account page title is : " + title);
		return title;	
	}
	
	@Step("getAccountPageUrl...")
	public boolean getAccountPageUrl() {
		String url = eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACCOUNT_PAGE_URL_PARAM);
		System.out.println("acccount page url is : " + url);
		if(url.contains(AppConstants.ACCOUNT_PAGE_URL_PARAM)) {
			return true;
		}
		return false;
	}
	
	@Step("isLogoutLinkExist...")
	public boolean isLogoutLinkExist() {
		return eleUtil.doEleIsDisplayed(logoutLink);
	}

	
	@Step("isSearchExist...")
	public boolean isSearchExist() {
		return eleUtil.doEleIsDisplayed(search);
	}
	
	
	@Step("performSearch...{0}")
	public SearchResultsPage performSearch(String productKey) {
		System.out.println("Product key is : " + productKey );
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, productKey);
			eleUtil.doClick(searchIcon);
			return new SearchResultsPage(driver);
		}
		else {
			System.out.println("search field is not present on the page...");
			return null;
		}
	}
	
	@Step("getAccountSecHeadersList...")
	public ArrayList<String> getAccountSecHeadersList() {
		List<WebElement> secList =  eleUtil.waitForElementsToBeVisible(accSecHeaders, AppConstants.DEFAULT_LARGE_TIME_OUT);
		System.out.println("total sections list : " + secList.size());
		ArrayList<String> actSectionTextList = new ArrayList<>();
		for(WebElement e : secList) {
			String text = e.getText();
			actSectionTextList.add(text);
		}
		return actSectionTextList;
	}
}
