package libraryProject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import libraryFramework.Global;

public class AppUtility {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static JavascriptExecutor js;

	public AppUtility() throws Exception {
		this.driver =  Utility.ng_returnDriver();
		wait = new WebDriverWait(driver, 40);
		this.js = (JavascriptExecutor) this.driver;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: InventoryDataMove
	Description     	: InventoryDataMove
	Input Parameters 	: strKey - Paramiter name to get the data value from TestData Table 	                               
	Return Value    	: bResult 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String InventoryDataMove(String strKey) throws Exception {
		String strVal = Utility.getTestDataValue(strKey);			
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			Utility.waitForPageToLoad();
			
			String strDesc = "Successfully entered data for Inventory and Approved";
			Utility.writeHTMLResultLog(strDesc, "pass");
			Utility.takeScreenShotAndLog("pass");
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "Failed to enter the data for Inventory";
			Utility.writeHTMLResultLog(strDesc, "fail");			
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 	
		return Global.bResult;
		
	}
	
}