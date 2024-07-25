package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productInfoMap;
	
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	
	public ProductInfoPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}
	
	public String getProductHeader(String mainProductName) {
		String xpath = "//h1[text()='"+mainProductName+"']";
		String productHeader = eleUtil.doGetText(By.xpath(xpath));
		System.out.println("product header is : " + productHeader);
		return productHeader;
	}
	
	public int getProductImagesCount() {
		return eleUtil.waitForElementsToBeVisible(productImages, AppConstants.DEFAULT_LARGE_TIME_OUT).size();
	}
	
	public String getProductPageTitle(String productTitle) {
		return eleUtil.waitForTitleIs(AppConstants.DEFAULT_TIME_OUT, productTitle);
	}
	
	public String getProductPageURL(String searchKey) {
		return eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, searchKey);
	}
	
	public Map<String,String> getProductMataData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		productInfoMap = new HashMap<String,String>();
		for(WebElement e : metaList) {
			String metaText = e.getText();
			String meta[] = metaText.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productInfoMap.put(metaKey, metaValue);
		}
		productInfoMap.forEach((k,v) -> System.out.println(k+ ":" +v));
		return productInfoMap;
	}
	
	public Map<String,String> getProductPriceData(){
		List<WebElement> priceList  = eleUtil.getElements(productPriceData);
		productInfoMap = new HashMap<String,String>();
		for(WebElement e : priceList) {
			String priceText = e.getText();
			String price[] = priceText.split(":");
			String priceKey = price[0].trim();
			String priceValue = price[1].trim();
			productInfoMap.put(priceKey, priceValue);
		}
		productInfoMap.forEach((k,v) -> System.out.println(k+ ":" +v));
		return productInfoMap;
	}

}
