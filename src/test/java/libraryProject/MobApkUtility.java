package libraryProject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import constantsMobile.MobileConstant;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import libraryFramework.Global;
import libraryFramework.InitDriver;

public class MobApkUtility {
	private static final String WebElement = null;
	static AndroidDriver driver;
	public MobApkUtility(String browser,String testType ) throws IOException {
		InitDriver initdriver = InitDriver.getInstance(browser,testType);		
		this.driver = initdriver.aDriver;		
	}
	
	public static AndroidDriver<AndroidElement> ng_returnDriver() throws Exception {
		return driver;
	}
	/*----------------------------------------------------------------------------
	Function Name    	: mb_clickWebElement
	Description     	: This function click mobile element
	Input Parameters 	: strKey - Paramiter name to get the data value from TestData Table
	                    : objData - Test Data
	Return Value    	: None 
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/     
	public static String mb_clickWebElement(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = Utility.getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {																	
			element.click();			
			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement.";			
			Utility.writeHTMLResultLog(strDesc, "pass");
			Utility.takeScreenShotAndLog("pass");			
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "WebElement " + strLabel +  "' is not displayed on the screen. Error Message : " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 								
		return Global.bResult;		
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: mb_clickWebElement
	Description     	: This function click mobile element
	Input Parameters 	: strKey - Paramiter name to get the data value from TestData Table
	                    : objData - Test Data
	Return Value    	: None 
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/ 
	public static String mb_clickElementByText(String strLabel, String strKey) throws Exception {
		String strVal = Utility.getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {																	
			String strXpath = (MobileConstant.ITEM_NAME).replace("$", strVal);
			driver.findElement(By.xpath(strXpath)).click();
			
			String strDesc = "Successfully clicked on '" + strLabel + "'  WebElement.";			
			Utility.writeHTMLResultLog(strDesc, "pass");
			Utility.takeScreenShotAndLog("pass");			
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "WebElement " + strLabel +  "' is not displayed on the screen. Error Message : " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 								
		return Global.bResult;		
	}
	/*----------------------------------------------------------------------------
	Function Name    	: enterText
	Description     	: This function enters a data into a text box
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String mb_enterText(WebElement element, String strLabel, String strKey) throws Exception {
		
		String strVal = Utility.getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {						
			element.sendKeys(strVal);
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_TAB);
			String strDesc = "Successfully entered '" + strVal + "' in '" + strLabel + "' textbox.";
			Utility.writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "'" + strLabel + "' textbox does not exist. Error Message : " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		}
		return Global.bResult;
	}
	/*----------------------------------------------------------------------------
	Function Name    	: failFooter
	Description     	: execute this function when TCs fails
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void failFooter() throws Exception {	
		if(driver != null) {
			driver.quit();
		}
		Global.test.log(Status.INFO, "<span style='font-weight:bold;color:#1ff3f5;'>Test Execution Finished</span>");		
        Global.report.flush();	
        File file1 = new File(Global.filePath);
		File file2 = new File(Global.gstrTestResultLogDir + File.separator + "index.html");
		if(file2.exists()){
			file2.delete();
		}
		FileUtils.copyFile(file1, file2);
//      Path source = Paths.get(Global.filePath);
//		Files.move(source, source.resolveSibling(Global.gTCName + Global.gstrTimesTamp +"_Fail.html"));												
        Global.objErr = "0";
		Global.bResult = "True";		
	}
	/*----------------------------------------------------------------------------
	Function Name    	: passFooter
	Description     	: execute this function when TCs fails
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void passFooter() throws Exception {
		Global.test.log(Status.INFO, "<span style='font-weight:bold;color:#1ff3f5;'>Test Execution Finished</span>");		
        Global.report.flush();
        if(driver != null) {
			driver.quit();
		}					       
		File file1 = new File(Global.filePath);
		File file2 = new File(Global.gstrTestResultLogDir + File.separator + "index.html");
		if(file2.exists()){
			file2.delete();
		}
		FileUtils.copyFile(file1, file2);
//      Path source = Paths.get(Global.filePath);
//		Files.move(source, source.resolveSibling(Global.gTCName + Global.gstrTimesTamp +"_Pass.html"));
		
	}

}
