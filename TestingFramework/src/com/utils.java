package com;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class utils {

	private static WebDriver driver;

	public utils() {
		driver = TestCase.getDriver();
	}

	public void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkIfValueExistInPageSource(String value) {
		boolean valueExists = false;
		if (driver.getPageSource().contains(value))
			valueExists = true;
		return valueExists;
	}

	public boolean isElementPresent(String xpath) {
		try {
			waitingForElementToLoad(xpath, 5000, 1000);
			WebElement element = driver.findElement(By.xpath(xpath));
			scrollObjectIntoView(element);
			return element != null;
		} catch (Exception e) {
			return false;
		}
	}

	public int getElementCount(String xpath) {
		int elementCount = driver.findElements(By.xpath(xpath)).size();
		return elementCount;
	}

	private void cliclOnElementJavascriptClick(WebElement element) {
		String javascript = "argument[0].click();";
		executeJavascript(driver, javascript, element);
	}

	public void clickOnElementJavascript(String xpath) {
		waitingForElementToLoad(xpath, 5000, 1000);
		WebElement element = driver.findElement(By.xpath(xpath));
		cliclOnElementJavascriptClick(element);
	}

	public void scrollObjectIntoView(WebElement element) {
		String jsCode = "arguments[0].scrollIntoView(true);";
		executeJavascript(driver, jsCode, element);
	}

	public void clickOnElement(String value, String searchType) {
		WebElement element = null;
		switch (searchType) {
		case "xpath":
			element = driver.findElement(By.xpath(value));
			break;
		case "linkText":
			element = driver.findElement(By.linkText(value));
			break;
		case "id":
			element = driver.findElement(By.id(value));
			break;
		case "class":
			element = driver.findElement(By.className(value));
			break;
		case "css":
			element = driver.findElement(By.cssSelector(value));
			break;
		case "default":
			break;
		}
		scrollObjectIntoView(element);
		element.click();
	}

	public void typeInValues(String value, String xpath) {
		WebElement element = driver.findElement(By.xpath(xpath));
		scrollObjectIntoView(element);
		element.clear();
		element.sendKeys(value);
	}

	public String getElementText(String xpath) {
		WebElement element = driver.findElement(By.xpath(xpath));
		return element.getText();
	}

	private void executeJavascript(WebDriver driver, String script, WebElement element) {
		((JavascriptExecutor) driver).executeScript(script, element);
	}

	public void selectDropDownOptionByXpath(String option, String xpath) {
		WebElement element = driver.findElement(By.xpath(xpath));
		new Select(element).selectByVisibleText(option);
	}

	public WebElement waitingForElementToLoad(String xpath, int timeout, int pollingTime) {
		WebElement waitForElementToLoad = null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.MILLISECONDS)
				.pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(Exception.class);
		try {
			waitForElementToLoad = wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver webDriver) {
					return driver.findElement(By.xpath(xpath));
				}
			});
		} catch (Exception e) {

		}
		return waitForElementToLoad;
	}

	public void HoverAndClick(String elementToHover, String elementToClick) {
		WebElement ToHover = driver.findElement(By.xpath(elementToHover));
		WebElement ToClick = driver.findElement(By.xpath(elementToClick));
		Actions action = new Actions(driver);
		action.moveToElement(ToHover).click(ToClick).build().perform();
	}

	public void switchFrame(String className) {
		WebElement iframeElement = driver.findElement(By.className(className));
		driver.switchTo().frame(iframeElement);

	}
}
