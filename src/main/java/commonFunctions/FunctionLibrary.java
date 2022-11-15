package commonFunctions;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;
public class FunctionLibrary {
	public static WebDriver driver;
	public static String expectedValue ="";
//method for browser launching
	public static WebDriver startBrowser()throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D://chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "D://geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else
		{
		System.out.println("Browser value is Not Matching");	
		}
		return driver;
	}
	//method for launching url
	public static void openUrl(WebDriver driver)throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}
	//method for wait for element
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String testData)
	{
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(testData));
		if(LocatorType.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		else
		{
			System.out.println("Unable to execute Wait for element method");
		}
	}
	//method for textbox
	public static void typeAction(WebDriver driver,String Locatortype,String Locatorvalue,String testdata)
	{
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locatorvalue)).clear();
			driver.findElement(By.xpath(Locatorvalue)).sendKeys(testdata);
		}
		else if(Locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locatorvalue)).clear();
			driver.findElement(By.name(Locatorvalue)).sendKeys(testdata);	
		}
		else if(Locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locatorvalue)).clear();
			driver.findElement(By.id(Locatorvalue)).sendKeys(testdata);
		}
		else
		{
			System.out.println("Unable to execute type action method");
		}
	}
	//method for buttons
	public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		else
		{
			System.out.println("Unable to Execute clickaction method");
		}
	}
	//method for title validation
	public static void validateTitle(WebDriver driver,String expectedtitle)
	{
		String actualtitle=driver.getTitle();
		try {
		Assert.assertEquals(expectedtitle, actualtitle,"Title is Not Matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	//close browser
		
	}
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	//method for capture data
	public static void captureData(WebDriver driver,String LocatorType,String LocatorValue)
	{
		
		if(LocatorType.equalsIgnoreCase("name"))
		{
			expectedValue =driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			expectedValue =driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
	}
	//method supplier table
	public static void supplierTable(WebDriver driver)throws Throwable
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			Thread.sleep(3000);
		//if not displayed click search panel
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(expectedValue);
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(3000);
		String actualvalue =driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(expectedValue+"      "+actualvalue);
		Assert.assertEquals(expectedValue, actualvalue,"Supplier Number Not Matching");
		
	}
	//method for mouse click
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock')])[4]"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Ca')])[2]"))).click().perform();
	}
	//method for stock tabale
	public static void stockTable(WebDriver driver,String expecteddata)throws Throwable
	{
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			Thread.sleep(3000);
		//if not displayed click search panel
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(3000);
		String actualdata =driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(expecteddata+"    "+actualdata);
		Assert.assertEquals(actualdata, expecteddata,"Stock categories is not Matching");
		
		
	}
	//generate date
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df= new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
		return df.format(date);
	}
}










