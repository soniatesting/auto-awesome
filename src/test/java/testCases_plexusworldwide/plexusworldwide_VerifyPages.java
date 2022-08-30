package testCases_plexusworldwide;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects_plexusworldwide.VerifyHomepageAndShopPage;
import testBase.testBase;

public class plexusworldwide_VerifyPages extends testBase {
	

	private static Logger log = LogManager.getLogger(plexusworldwide_VerifyPages.class.getName());
	SoftAssert softAssert = new SoftAssert();
	VerifyHomepageAndShopPage verifyHomepageAndShopPage;

	@BeforeMethod
	public void intial() throws Exception {
		String osName = System.getProperty("os.name");
		initialize("UI", osName);
		initiateExtentReporterWithClassName("");
		getUrl(CONFIG.getProperty("Url"));
		verifyHomepageAndShopPage = new VerifyHomepageAndShopPage(driver);
		}

	@Test(retryAnalyzer = listeners.RetryAnalyzer.class, description = "Plexus World Wide page verification")
	public void CheckHomePageAndShopPage() throws Exception {

	
		log.debug("==== Add Product Test Start ==");
		verifyHomepageAndShopPage.VerifyHomepage(softAssert, log,test);
		verifyHomepageAndShopPage.VerifyShopPage(softAssert, log,test);
		log.debug("==== Add Product Test End ==");
		
	}

	@AfterMethod(dependsOnMethods = { "tearDown"})
	public void closingSession() throws Exception {
		softAssert.assertAll();
		closeBrowser();
	}
}