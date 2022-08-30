package testBase;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class testBase {

	public static Properties CONFIG = null;
	public static Properties OR = null;
	public static Properties VALUES = null;
	public static Properties EP = null;
	public static Properties SP = null;
	public static WebDriver driver = null;
	public static DesiredCapabilities dc = null;
	public static ExtentHtmlReporter reporter;// Look and feel of report
	public static ExtentReports report; // To create test cases inside the report
	public static ExtentTest test; // Update test case pass, fail, log in report
	public SoftAssert softAssert;

	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public static String screenshot(String filename) throws IOException, HeadlessException, AWTException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String updatedfilename = filename.trim() + "_" + dateTime(" MMddhhmmss").trim() + ".JPEG";
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "/test-output/Screenshot/" + updatedfilename.trim()));
			return (System.getProperty("user.dir") + "/test-output/Screenshot/" + updatedfilename.trim());
		}
	// example use: dateTime("MMddhhmmss");
	public static String dateTime(String dateTimeFormat) {
		SimpleDateFormat ft = new SimpleDateFormat(dateTimeFormat);
		Calendar cal = Calendar.getInstance();
		return ft.format(cal.getTime());

	}

	
	public static void getUrl(String url) {
		driver.get(url);
	}

	public static void closeBrowser() {
		driver.close();
		driver.quit();
	}


	public void initiateExtentReporterWithClassName(String dataproviderName) {
		test = report.createTest(this.getClass().getSimpleName() + "_" + dataproviderName);
	}


public void initialize(String testType, String osType) throws IOException {
		String valueENV = System.getenv("ENV");
		String valueMODULE = System.getenv("MODULE");
		System.out.println("Test Start");
		if ((driver == null)) {

			CONFIG = new Properties();
			FileInputStream fn = null;
			if (valueENV == null || valueENV.equalsIgnoreCase("QA")) {
				fn = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//java//Config//config.properties");
			} else {
				System.out.println("Config name passed is incorrect! Please correct the config name!");
			}

			CONFIG.load(fn);
			EP = new Properties();
			if (testType.equalsIgnoreCase("UI")) {
					if (CONFIG.getProperty("Browser").equals("Chrome")) {
					
					if (osType.contains("Mac")) {
						System.setProperty("webdriver.chrome.driver", "Browsers//chromedriver");
					} else {
						System.setProperty("webdriver.chrome.driver", "Browsers//chromedriver.exe");
					}

					ChromeOptions options = new ChromeOptions();
					options.addArguments("test-type");
					options.addArguments("--start-maximized");
					options.addArguments("--js-flags=--expose-gc");
					options.addArguments("--enable-precise-memory-info");
					options.addArguments("--disable-popup-blocking");
					options.addArguments("--disable-default-apps");
					options.addArguments("--enable-automation");
					options.addArguments("test-type=browser");
					options.addArguments("disable-infobars");
					options.addArguments("disable-extensions");
					
					options.addArguments("--profile-directory=Default");
					options.setExperimentalOption("useAutomationExtension", false);
					driver = new ChromeDriver(options);

				} else if (CONFIG.getProperty("browser").equals("Firefox")) {
					File pathToBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
					FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					driver = new FirefoxDriver();
					// dr = new FirefoxDriver();
				} else if (CONFIG.getProperty("Browser").equals("IE")) {
					File file = new File("Browsers//IEDriverServer.exe");
					System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
					// Handles the IE security settings error when all zones don't have the same
					// setting
					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
					driver = new InternetExplorerDriver(caps);
				} else {
					System.out.println("something wrong typed!!");
				}

				driver = new EventFiringWebDriver(driver);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			}
		} else {
			System.out.println("property is not set properly!");
		}
	}
	
	@BeforeSuite
	public void setExtent() {
		System.out.println("Inside Before Suite method!---------------> ");
		reporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReportsTestNG.html");
		reporter.config().setDocumentTitle("Automation Report");
		reporter.config().setReportName("Functional Report");
		reporter.config().setTheme(Theme.DARK);

		report = new ExtentReports();
		report.attachReporter(reporter);
		report.setSystemInfo("Hostname", "LocalHost");
		report.setSystemInfo("OS", "Win");
		report.setSystemInfo("Developer", "Sonia");
		report.setSystemInfo("Browser", "Chrome");

	}

	@AfterSuite
	public void endReport() {
		System.out.println("After suite------------------> ");
		report.flush();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		System.out.println("Inside After method----------------------->");
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Get result name -->" + result.getName());
			test.log(Status.FAIL, "Test case failed is " + result.getName()); // Add test case name in report
			test.log(Status.FAIL, "Test case failed is " + result.getThrowable()); // Add detail about failure of the
																					// script
			if (driver != null) {
				try {
					String screenshotPath = screenshot(result.getName());
					System.out.println("Trying to attach screenshot--------------" + screenshotPath);
					test.log(Status.FAIL, "<a href='" + screenshotPath + "'><img src='" + screenshotPath
							+ "' height='100' width='100'/></a>");

				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test case SKIPPED is " + result.getName());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.PASS, "Test case Passed is " + result.getName());
		}
	}
}
