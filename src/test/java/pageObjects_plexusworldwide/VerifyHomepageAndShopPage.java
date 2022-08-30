package pageObjects_plexusworldwide;


import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import testUtil.TestUtility;

public class VerifyHomepageAndShopPage extends TestUtility {

	
	public VerifyHomepageAndShopPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//span[text()='Featured Products']")
	protected WebElement FeaturedProducts_Section;
	
	@FindBy(xpath = "//span[text()='Sign In']")
	protected WebElement SignIn;
	
	@FindBy(xpath = "//span[text()='Shop']")
	protected WebElement Shop;
	
	@FindBy(xpath = "//h3[text()='Reset']")
	protected WebElement ResetSection;
	
	@FindBy(xpath = "//a[text()='Reset']")
	protected WebElement ResetMenu;
	
	public void VerifyHomepage(SoftAssert softAssert, Logger log,ExtentTest test) throws Exception  
	{

		test.info("Home Page Opened");
		waitfn(FeaturedProducts_Section);
		Thread.sleep(2000);
		assertTrue(FeaturedProducts_Section.isDisplayed(),"Feature Products are not displayed");
		assertTrue(SignIn.isDisplayed(),"Feature Products are not displayed");
		assertTrue(Shop.isDisplayed(),"Feature Products are not displayed");
		test.pass("Home Page Verified");
		
	}
	public void VerifyShopPage(SoftAssert softAssert, Logger log,ExtentTest test) throws Exception  
	{

		test.info("Click on Shop");
		Shop.click();
		waitfn(ResetSection);
		String strUrl = driver.getCurrentUrl();
		Assert.assertEquals(strUrl, "https://shop.plexusworldwide.com/products");
		assertTrue(ResetSection.isDisplayed(),"Feature Products are not displayed");
		assertTrue(ResetMenu.isDisplayed(),"Feature Products are not displayed");
		test.pass("Shop Page Verified");
	}	
		
}
