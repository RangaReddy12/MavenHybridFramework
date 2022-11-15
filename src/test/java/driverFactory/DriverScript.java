package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	public static WebDriver driver;
	String inputpath ="D:\\Ojt7oclock\\Maven_Project\\TestInput\\DataEngine.xlsx";
	String outputpath ="D:\\Ojt7oclock\\Maven_Project\\TestOutput\\HybridResults.xlsx";
	ExtentReports report;
	ExtentTest test;
	public void startTest()throws Throwable
	{
		String ModuleStatus="";
		//create object for excelfiule util
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
		{
			if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule =xl.getCellData("MasterTestCases", i, 1);
				//define path
				report= new ExtentReports("./ExtentReports/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					test=report.startTest(TCModule);
					String Description =xl.getCellData(TCModule, j, 0);
					String ObjectType =xl.getCellData(TCModule, j, 1);
					String LocatorType =xl.getCellData(TCModule, j, 2);
					String LocatorValue= xl.getCellData(TCModule, j, 3);
					String TestData =xl.getCellData(TCModule, j, 4);
					try {
						if(ObjectType.equalsIgnoreCase("startBrowser"))
						{
							driver =FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, LocatorType, LocatorValue, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, LocatorType, LocatorValue, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, LocatorType, LocatorValue);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("captureData"))
						{
							FunctionLibrary.captureData(driver, LocatorType, LocatorValue);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("mouseClick"))
						{
							FunctionLibrary.mouseClick(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}
						//write ass pass into status
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						test.log(LogStatus.PASS, Description);
						ModuleStatus="true";
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						//write as fail into status cell
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						test.log(LogStatus.FAIL, Description);
						ModuleStatus="false";
					}
					if(ModuleStatus.equalsIgnoreCase("True"))
					{
						xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
					}
					if(ModuleStatus.equalsIgnoreCase("False"))
					{
						xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);	
					}
					

				}
				report.endTest(test);
				report.flush();
			}
			else
			{
				//write as blocked into MasterTestCases sheet
				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
			}
		}
	}

}









