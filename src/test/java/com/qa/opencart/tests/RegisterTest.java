package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.TestBase;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterTest extends TestBase {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();		
	}
	
	
	@DataProvider
	public Object[][] getTestData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	public String getRandomEmail() {
		Random random = new Random();
		String email = "automationtest"+random.nextInt(10000)+"@gmail.com";
		return email;
	}
	
	@Test(dataProvider = "getTestData")
	public void registerUserTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		String actSuccessMsg = registerPage.userRegister(firstName, lastName, getRandomEmail(), telephone, password, subscribe);
		Assert.assertEquals(actSuccessMsg, AppConstants.ACC_CREATE_SUCC_MSG);
	}
}
