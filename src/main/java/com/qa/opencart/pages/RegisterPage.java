package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit']");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@type='radio' and @value='1']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@type='radio' and @value='0']");
	
	private By regSuccessMsg = By.cssSelector("div#content h1");
	private By logutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String userRegister(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		eleUtil.doSendKeysWithElementVisible(this.firstName, AppConstants.DEFAULT_LARGE_TIME_OUT, firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(this.subscribeYes);
		}
		else{
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(this.agreeCheckBox);
		eleUtil.doClick(this.continueButton);
		
		String successMsg = eleUtil.waitForElementVisible(regSuccessMsg, AppConstants.DEFAULT_LARGE_TIME_OUT).getText();
		System.out.println("Sucess Massgg=====>" + successMsg);
		
		eleUtil.doClick(logutLink);
		eleUtil.doClick(registerLink);
		
		return successMsg;
	}
}
