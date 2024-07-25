package com.qa.opencart.tests;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.TestBase;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic - 200: Open cart application Accounts page design")
@Story("US- 101: Design Accounts page features")
public class AccountPageTest extends TestBase{

	
	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("accountPageTitleTest-- Dev Name: @Sanjay Kumar")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)
	public void accountPageTitleTest() {
		String actAccountPageTitle = accPage.getAccPagetitle();
		Assert.assertEquals(actAccountPageTitle, AppConstants.ACCOUNT_PAGE_TITLE);
	}
	
	@Description("accountPageUrlTest-- Dev Name: @Sanjay Kumar")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void accountPageUrlTest() {
		Assert.assertTrue(accPage.getAccountPageUrl());
	}
	
	@Description("Acc page search test -- Dev Name: @Sanjay Kumar")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void searchExistTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	@Description("Acc page logout link exist test -- Dev Name: @Sanjay Kumar")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=4)
	public void logoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Description("Acc page header test -- Dev Name: @Sanjay Kumar")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority=5)
	public void accPageHeadersTest() {
		ArrayList<String> actHeadersList = accPage.getAccountSecHeadersList();
		System.out.println("Actual account page headers : " + actHeadersList);
		Assert.assertEquals(actHeadersList, AppConstants.ACC_PAGE_SECTIONS_HEADERS);
	}
	
	@DataProvider
	public Object[][] getProductKey(){
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Samsung"}
		};
	}
	
	@Description("Acc page serach check test -- Dev Name: @Sanjay Kumar")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "getProductKey", priority = 6 )
	public void searchCheckTest(String productKey) {
	searchResultsPage = accPage.performSearch(productKey);
	Assert.assertTrue(searchResultsPage.isSearchSuccessfull());
	}
	
	@DataProvider
	public Object[][] getProductData(){
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"Macbook","MacBook Air"},
			{"iMac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Description("Acc page product serach test -- Dev Name: @Sanjay Kumar")
	@Severity(SeverityLevel.BLOCKER)
	@Test(dataProvider = "getProductData", priority = 7)
	public void searchTest(String searchKey, String mainProductName) {
		searchResultsPage = accPage.performSearch(searchKey);
		if(searchResultsPage.isSearchSuccessfull()) {
			productInfoPage = searchResultsPage.selectProduct(mainProductName);
			String actualProductHeader = productInfoPage.getProductHeader(mainProductName);
			Assert.assertEquals(actualProductHeader, mainProductName);
		}
	}
	
}
