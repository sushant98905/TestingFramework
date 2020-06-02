package com;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCase {
	private static String hub_url = "http://localhost:4444/wd/hub";
	public static RemoteWebDriver driver;
	public String projectUrl = "http://automationpractice.com/index.php";
	public utils util;
	public String emailId = "Test989051@gmail.com";
	public String firstName = "Sushant";
	public String lastName = "Patil";
	public String password = "Password75079";
	public String address = "Rasayani";
	public String city = "Panvel";
	public String state = "Alabama";
	public String pincode = "00000";
	public String country = "United States";
	public String contact = "7507979631";
	public String alias = "Testing@gmail.com";
	public String expectedSignInTitle = "My account - My Store";
	public String expectedTotalBill = "$36.42";

	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeClass
	public void beforeClass() {
		try {
			driver = new RemoteWebDriver(new URL(hub_url), getChromeCapabilities());
			driver.manage().window().maximize();
			driver.get(projectUrl);
			util = new utils();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	private DesiredCapabilities getChromeCapabilities() {
		DesiredCapabilities chromeCapabilities = new DesiredCapabilities().chrome();
		return chromeCapabilities;
	}

	@Test(priority = 0)
	public void accountCreation() {

		util.clickOnElement("Sign in", "linkText");
		util.sleep(2000);
		util.typeInValues(emailId, "//*[text()='Create an account']//following::div//input[@id='email_create']");
		util.clickOnElement("//div//button[@name='SubmitCreate']", "xpath");
		util.waitingForElementToLoad("//*[text()='Your personal information']", 10000, 1000);
		Assert.assertFalse(util.checkIfValueExistInPageSource("already been registered"),
				"Email Id is already registered");

		util.typeInValues(firstName, "//input[@id='customer_firstname']");
		util.typeInValues(lastName, "//input[@id='customer_lastname']");
		util.typeInValues(password, "//input[@id='passwd']");
		util.typeInValues(address, "//input[@id='address1']");
		util.typeInValues(city, "//input[@id='city']");
		util.selectDropDownOptionByXpath(state, "//select[@id='id_state']");
		util.typeInValues(pincode, "//input[@id='postcode']");
		util.selectDropDownOptionByXpath(country, "//select[@id='id_country']");
		util.typeInValues(contact, "//input[@id='phone_mobile']");
		util.typeInValues(alias, "//input[@id='alias']");
		util.clickOnElement("//button[@id='submitAccount']", "xpath");
		util.sleep(2000);
		Assert.assertFalse(util.checkIfValueExistInPageSource("error"), "There is an error on registration page");

		util.clickOnElement("Sign out", "linkText");
		util.sleep(2000);

	}

	@Test(priority = 1)
	public void loginAccount() {
		util.typeInValues(emailId, "//*[text()='Already registered?']//following::div//input[@id='email']");
		util.typeInValues(password, "//*[text()='Already registered?']//following::div//input[@id='passwd']");
		util.clickOnElement("//div//button[@name='SubmitLogin']", "xpath");
		util.sleep(3000);
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedSignInTitle);

	}

	@Test(priority = 2)
	public void addToCart() {
		util.clickOnElement("Women", "linkText");
		util.sleep(2000);
		util.HoverAndClick("//a[normalize-space(text())='Faded Short Sleeve T-shirts']",
				"//a[@title='Faded Short Sleeve T-shirts']//following::span");
		util.sleep(3000);
		util.switchFrame("fancybox-iframe");
		util.sleep(1000);
		util.clickOnElement("//a//span//i[@class='icon-plus']", "xpath");
		util.clickOnElement("//button[@name='Submit']", "xpath");
		util.sleep(5000);
		util.clickOnElement("//span[@title='Close window']", "xpath");
	}

	@Test(priority = 3)
	public void checkOutProcess() {
		util.clickOnElement("//a[@title='View my shopping cart']", "xpath");
		util.sleep(2000);
		String actualBill = util.getElementText("//span[@id='total_price']");
		Assert.assertEquals(actualBill, expectedTotalBill);

		util.clickOnElement("//a[@class='button btn btn-default standard-checkout button-medium']", "xpath");
		util.sleep(2000);
		util.clickOnElement("//button//span[contains(text(),'Proceed to checkout')]/..", "xpath");
		util.sleep(2000);
		util.clickOnElement("//input[@id='cgv']", "xpath");
		util.clickOnElement("//button//span[contains(text(),'Proceed to checkout')]/..", "xpath");
		util.sleep(2000);
		String actualBillfinal = util.getElementText("//span[@id='total_price']");
		Assert.assertEquals(actualBillfinal, expectedTotalBill);

		util.clickOnElement("//a[@class='bankwire']", "xpath");
		String actualBillbeforeOrder = util.getElementText("//span[@id='amount']");
		Assert.assertEquals(actualBillbeforeOrder, expectedTotalBill);

		util.clickOnElement("//button//span[text()='I confirm my order']//..", "xpath");
		util.sleep(5000);
		String actualBillafterOrder = util.getElementText("//span[@class='price']//strong");
		Assert.assertEquals(actualBillafterOrder, expectedTotalBill);

	}

	@Test(priority = 4)
	public void checkOrderhistory() {
		util.clickOnElement("//a[@class='account']", "xpath");
		util.clickOnElement("//a//span[text()='Order history and details']", "xpath");
		String actualBillafterOrder = util.getElementText("//span[@class='price']");
		Assert.assertEquals(actualBillafterOrder.trim(), expectedTotalBill);
	}

}
