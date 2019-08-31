/*============================================================================================
Library File Name    :  Utility
Author               :  
Created date         :  23-Sep-2017
Description          :  It lists the common utility functions that can be used in the scripts.
============================================================================================*/

package libraryProject;

//import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import libraryFramework.Global;
import libraryFramework.InitDriver;
import libraryFramework.ReadObject;
import runManager.HybridExecuteTest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utility  {
	static WebDriver driver;
	static Calendar cc = null;
	public static String homePath = "";
	public static WebDriverWait wait;
	public static JavascriptExecutor js;
    
	
	public Utility(String browser, String testType) throws IOException {
		InitDriver initdriver = InitDriver.getInstance(browser,testType);		
		this.driver = initdriver.wDriver;		
		wait = new WebDriverWait(driver, 40);
		this.js = (JavascriptExecutor) this.driver;
	}
	
	public static WebDriver ng_returnDriver() throws Exception {
		return driver;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: invokeBrowser
	Description     	: This function invokes the application in Browser
	Input Parameters 	: strKey - Paramiter name to get the data value from TestData Table
	                    : objData - Test Data
	Return Value    	: None 
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public static String ng_invokeBrowser(String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}				
		try {			
			driver.get(strVal);						
			//driver.get("https://smali:mar-2018@adfs.elliemae.com/adfs/ls/wia");
			//driver.navigate().to(strVal);
			waitForPageToLoad();	
			//String strDesc = "Browser  '" + strVal + "'  Invoked Successfully."
			String strDesc = "Browser <span style='font-weight:bold;color:#4CAF50;'> '" + strVal + "' </span> Invoked Successfully.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Throwable error) {
			String strDesc = "<span style='color:#ff4d4d;'> Timeout waiting for Page Load Request to complete.</span>";
			writeHTMLResultLog(strDesc, "fail");			
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 
		return Global.bResult;

	}

	/*----------------------------------------------------------------------------
	Function Name    	: verifyPage
	Description     	: This function enters a data into a text box
	Input Parameters 	: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_verifyPage(String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}		
		try {
			waitForPageToLoad();			
			String strDesc = "Page <span style='font-weight:bold;color:#4CAF50;'> '" + strLabel + "' </span> is displayed successfully.";
			writeHTMLResultLog(strDesc, "info");
			takeScreenShotAndLog("pass");
			Global.bResult = "True";
		} catch (Throwable e) {
			String strDesc = "Page <span style='color:#ff4d4d;'> '" + strLabel + "' is not displayed properly.</span> Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
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
	public static String ng_enterText(WebElement element, String strLabel, String strKey) throws Exception {
		
		String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			/*waitForPageToLoad();
			ng_waitUntilElementVisible(element,20);			
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);						
			element.click();*/
			
			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
				clearTextField(element);
		    	ng_waitImplicitly(1);		    	
			}	
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}	
			
			if (strVal.equalsIgnoreCase("RANDOM")) {
				strVal = Utility.ng_RandomAlphaNum("CU_",5);
			}
			element.sendKeys(strVal);
			String strDesc = "Successfully entered <span style='font-weight:bold;color:#4CAF50;'> '" + strVal + "' </span> in '" + strLabel + "' textbox.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "<span style='color:#ff4d4d;'> '" + strLabel + "' textbox does not exist.</span> Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		}
		return Global.bResult;
	}
	/*----------------------------------------------------------------------------
	Function Name    	: getTestDataValue
	Description     	: This function get the TestDataValue
	Input Parameters 	: strKey - Paramiter name to get the data value from TestData Table 
	                    :                        
	Return Value    	: strCellData
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String getTestDataValue(String strKey) throws Exception {
		String strCellData;
		if (Global.gstrReadfromTestData){
			strCellData = (String) Global.objData.get(strKey.toUpperCase());
			if (strCellData!= null) {
				return strCellData;
			}else {
				String strDesc = "DataValue <span style='color:#ff4d4d;'>'" + strKey +  "'</span> not found in Test Data . " ;
				writeHTMLResultLog(strDesc, "fail");
				Global.bResult = "False";
				Global.objErr = "11";				
		        Global.report.flush();
			}
		}
		else{
			strCellData =  strKey;
		}		
		return strCellData;
	}

	/*----------------------------------------------------------------------------
	Function Name    	: ng_DropDown
	Description     	: 
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_DropDown(WebElement element,String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);	
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			ng_enterText(element, strLabel, strKey);			
			ng_waitImplicitly(2);
			element.sendKeys(Keys.ENTER);
			waitForPageToLoad();
			ng_waitImplicitly(2);
						
			WebElement triangleloc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@title,'Search: "+strLabel+"')]")));					
			triangleloc.click();
			waitForPageToLoad();
			ng_waitImplicitly(2);
			
			WebElement dropdownselect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='"+strVal+"']")));
			dropdownselect.click();
			waitForPageToLoad();	
			String strDesc = "Value <span style='font-weight:bold;color:#4CAF50;'>'" + strVal + "'</span> is selected successfully from '" + strLabel + "' list.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "Items in the  List  <span style='color:#ff4d4d;'>"+strLabel + "'</span> are not displayed. Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 	
		return Global.bResult;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: ng_SelectList
	Description     	: 
	Input Parameters 	: strObject - Object Name of select Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_SelectList(WebElement element,String strLabel, String strKey) throws Exception {
		
		String strVal = getTestDataValue(strKey);			
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			/*waitForPageToLoad();
			explicitWait(element, 20);
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);*/
			
			Select objSelect = new Select(element);
			objSelect.selectByVisibleText(strVal);			
			String strDesc = "Value <span style='font-weight:bold;color:#4CAF50;'>'" + strVal + "'</span> is selected successfully from '" + strLabel + "' list.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "Items in the  List  <span style='color:#ff4d4d;'>"+strLabel + "'</span> are not displayed. Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 	
		return Global.bResult;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: ng_SelectListtable
	Description     	: 
	Input Parameters 	: strObject - Object Name of Edit Box
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table                        
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_SelectListtable(WebElement element,String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);		
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}	
		try {
			waitForPageToLoad();				
			if(StringUtils.isNotEmpty(ng_getTextBoxValue(element, strLabel))) {
				clearTextField(element);
		    	ng_waitImplicitly(2);		    	
		    	waitForPageToLoad();
			}					
			ng_enterText(element, strLabel, strKey);
			ng_waitImplicitly(2);
			element.sendKeys(Keys.ENTER);
			ng_waitImplicitly(5);

			String triangleloc="//label[text()='"+strLabel+"']/preceding::td/following::td//a[contains(@title,'Search: "+strLabel+"')][1]";
			
			WebElement trianglebox = driver.findElement(By.xpath(triangleloc));
			
			trianglebox.click();
			ng_waitImplicitly(3);
			WebElement dropdownselect=driver.findElement(By.xpath("//div[contains(@id,'dropdownPopup::dropDownContent')]//span[text()='"+strVal+"']"));
			dropdownselect.click();
			
			String strDesc = "Value <span style='font-weight:bold;color:#4CAF50;'>'" + strVal + "'</span> is selected successfully from '" + strLabel + "' list.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "Items in the  List  <span style='color:#ff4d4d;'>"+strLabel + "'</span> are not displayed. Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 	
		return Global.bResult;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: clickWebElement
	Description     	: This function clicks the WebElement object
	Input Parameters 	: strObject - Object Name of Web Element
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_clickWebElement(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {		
			waitForPageToLoad();
			explicitWait(element,20);
			
			//ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);
			
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			String onClickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click',true, false);arguments[0].dispatchEvent(evObj);} else if(document.createEventObject){ arguments[0].fireEvent('onclick');}";
			if (element.getAttribute("onclick")!=null) {
				element.click();
			}			
			else {
				js.executeScript(onClickScript,element);
			}	
			waitForPageToLoad();
			String strDesc = "Successfully clicked on <span style='font-weight:bold;color:#4CAF50;'> '" + strLabel + "' </span>  WebElement.";			
			writeHTMLResultLog(strDesc, "pass");
			takeScreenShotAndLog("pass");			
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "<span style='font-weight:bold;color:#ff4d4d;'> WebElement '" + strLabel +  "' is not displayed on the screen. </span> Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 								
		return Global.bResult;
	}

	/*----------------------------------------------------------------------------
	Function Name       :writeHTMLResultLog
	Description         :This function will create the test Log File
	Input Parameter    	:strDescription - Description to be printed
	                    :intPassFail
	Return Value        :None
	Author		        :
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void writeHTMLResultLog(String strDescription, String strPassFail) throws Exception {
		if (strPassFail == "pass") {
			Global.test.log(Status.PASS, strDescription);
			Global.logger.info(strDescription);
		} else if (strPassFail == "fail") {
			Global.test.log(Status.FAIL, strDescription);
			Global.logger.error(strDescription);
		} else if (strPassFail == "info") {
			Global.test.log(Status.INFO, strDescription);
			Global.logger.info(strDescription);
		}
		
	}

	/*----------------------------------------------------------------------------
	Function Name       :TakeScreenShot
	Description         :This function takes the screen shot of the application
	Input Parameter    	:strDescription - Description to be printed
	Return Value        :None
	Author		        :
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void takeScreenShotAndLog(String strPassFail) throws Exception {
		if(Global.gstrScreenShotFlag == true) {

			if (strPassFail == "pass") {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String tImage = "Run_" + Utility.getCurrentDatenTime("dd-MM-yy") + "_" + Utility.getCurrentDatenTime("H-mm-ss a");
				FileUtils.copyFile(scrFile,	new File(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg"));
				//String image = Global.test.addScreenCaptureFromPath(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg");
				Global.test.log(Status.PASS, (Markup) Global.test.addScreenCaptureFromPath(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg"));
				
			} else if (strPassFail == "fail") {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String tImage = "Run_" + Utility.getCurrentDatenTime("dd-MM-yy") + "_" + Utility.getCurrentDatenTime("H-mm-ss a");
				FileUtils.copyFile(scrFile,	new File(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg"));
				//String image = Global.test.addScreenCaptureFromPath(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg");
				Global.test.log(Status.FAIL, (Markup) Global.test.addScreenCaptureFromPath(Global.gstrScreenshotsDir + "\\" + Global.gTCName + "\\img_" + tImage + ".jpg"));
			}
		}
	}

	/*----------------------------------------------------------------------------
	Function Name    	: getCurrentDatenTime
	Description     	: Function to get Current Date and Time
	Input Parameters 	: format
	Return Value    	: Current Time
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String getCurrentDatenTime(String format) {
		Calendar cal = Calendar.getInstance();
		cc = cal;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	/*----------------------------------------------------------------------------
	Function Name    	: getObject
	Description     	: Find element BY using object type and value
	Input Parameters 	: key
	Return Value    	: object
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	private static By getObject(String key) throws IOException {
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		String obj = allObjects.getProperty(key);
		String[] arrOfStr = obj.split(",", 2);
		String objectType = arrOfStr[0];
		String objectValue = arrOfStr[1];
		// private By getObject(Properties p,String objectName,String objectType) throws
		// Exception{
		// Find by xpath
		if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(objectValue);
		}
		// find by class
		else if (objectType.equalsIgnoreCase("NAME")) {
			return By.name(objectValue);
		}
		// find by name
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(objectValue);
		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSSSELECTOR")) {
			return By.cssSelector(objectValue);
		}
		// find by link
		else if (objectType.equalsIgnoreCase("LINK")) {
			return By.linkText(objectValue);
		} else if (objectType.equalsIgnoreCase("TAGNAME")) {
			return By.tagName(objectValue);
		} else if (objectType.equalsIgnoreCase("ID")) {
			return By.id(objectValue);
		}
		// find by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {
			return By.partialLinkText(objectValue);
		}
		return null;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: waitForPageToLoad
	Description     	: wait For Page To Load
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	// Wait 
    public static void waitForPageToLoad() {    
    	
    	driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
    	
    	/*ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};   
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(expectation);				
		for (int i=0; i<25; i++){ 
			try {
				Thread.sleep(1000);				
			} catch (InterruptedException e) {} 
			   	if (js.executeScript("return document.readyState").toString().equals("complete")){ 
			   		break; 
			   	}   
		}*/
    }
    
    public static String ng_getTextBoxValue(WebElement element, String elementDescription) throws Exception {
        String attValue = "";
        ng_scrollIntoViewElement(element, elementDescription);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object o = js.executeScript("return arguments[0].value;", element);
        attValue = (o == null) ? "" : o.toString();        
        return attValue;	  
  	}
    
    /*----------------------------------------------------------------------------
	Function Name    	: ng_scrollIntoViewElement
	Description     	: 
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
    public static void ng_scrollIntoViewElement(WebElement element, String elementDescription) throws Exception {
		try {
			if (!element.isDisplayed()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");
				//((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			}			
		} catch (Exception e) {
										
		}
	}
    
    /*----------------------------------------------------------------------------
	Function Name    	: clearTextField
	Description     	: 
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
    public static void clearTextField(WebElement element) {
		//element.sendKeys(Keys.HOME, Keys.SHIFT, Keys.END, Keys.BACK_SPACE);
		element.sendKeys(Keys.END, Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
		clearTextFieldMulLines(element);
	}
    
    /*----------------------------------------------------------------------------
	Function Name    	: clearTextFieldMulLines
	Description     	: 
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
    public static void clearTextFieldMulLines(WebElement element) {
		element.sendKeys(Keys.CONTROL, Keys.HOME);
		element.sendKeys(Keys.CONTROL, Keys.SHIFT, Keys.END);
		element.sendKeys(Keys.BACK_SPACE);
	}
    
    /*----------------------------------------------------------------------------
	Function Name    	: ng_waitImplicitly
	Description     	: Method for waiting a certain amount of time.
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
  
    public static void ng_waitImplicitly(int time) {
		long startTime = 0;
		long endTime = 0;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		endTime = System.currentTimeMillis();		
	}
    
    /*----------------------------------------------------------------------------
	Function Name    	: ng_clickUsingActions
	Description     	: Method clicks on a specific element using actions.
	Input Parameters 	: 
	Return Value    	: 
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
    public static String ng_clickUsingActions(WebElement element, String strLabel, String strKey) throws Exception {
    	String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			waitForPageToLoad();
			explicitWait(element,20);			
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			Actions builder = new Actions(driver);
			builder.moveToElement(element).click().build().perform();	
			waitForPageToLoad();
			
			String strDesc = "Successfully clicked on <span style='font-weight:bold;color:#4CAF50;'>'" + strLabel + "'</span>  WebElement.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";			
		} catch (Exception e) {
			String strDesc = "WebElement <span style='color:#ff4d4d;'>'" + strLabel + "'</span> is not displayed on the screen. Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 	
		return Global.bResult;		
	}
    
    /*----------------------------------------------------------------------------
  	Function Name    	: ng_selectWindow
  	Description     	: Selects a particular Window on the basis of the parameters passed.
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
    public static String ng_selectWindow(String strKey) throws Exception {
    	String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		String windowId = null;
		try {			
			waitForPageToLoad();
			//ng_waitImplicitly(5);
			
			driver.switchTo().activeElement();
			windowId = driver.getWindowHandle();
			driver.switchTo().window(windowId);
			//waitForPageToLoad();
			String strDesc = "Window has switched to: <span style='font-weight:bold;color:#4CAF50;'>" + windowId + "</span>";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
			
		} catch (Exception e) {
			String strDesc = "Failed to switched to: <span style='color:#ff4d4d;'> " + windowId + " </span> Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		}					
		return strVal;
	}
    
    /*----------------------------------------------------------------------------
  	Function Name    	: ng_selectFrame
  	Description     	: Selects a particular Frame on the basis of the parameters passed.
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
    public static String ng_selectFrame(WebElement element,String strKey) throws Exception {
    	String strVal = getTestDataValue(strKey);				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		String windowId = null;
		try {			
			waitForPageToLoad();
			//ng_waitImplicitly(5);
			
			driver.switchTo().frame(element);
						
			//waitForPageToLoad();
			String strDesc = "Switched for Frame";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
			
		} catch (Exception e) {
			String strDesc = "Failed to switched to frame : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		}					
		return strVal;
	}
    
    /*----------------------------------------------------------------------------
  	Function Name    	: ng_typeAndTab
  	Description     	: ng_typeAndTab
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        :
  	Date of creation	:
	Date of modification: 
  	----------------------------------------------------------------------------*/
    public static String ng_typeAndTab(WebElement element, String strLabel, String strKey) throws Exception {
    	String strVal = getTestDataValue(strKey);					 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			//waitForPageToLoad();
		    ng_enterText(element, strLabel, strKey);
		    ng_sendTab(element, strLabel);
		    //waitForPageToLoad();		   
			Global.bResult = "True";
		} catch (Exception e) {			
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return Global.bResult;
	}
    
    /*----------------------------------------------------------------------------
  	Function Name    	: clearTextAndTab
  	Description     	: clearTextAndTab
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
    public static String clearTextAndTab(WebElement element, String strLabel, String strKey) throws Exception {
    	String strVal = getTestDataValue(strKey);   	 
    	if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			//waitForPageToLoad();
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			//ng_waitUntilElementDisplayed(element,20);
			//ng_scrollIntoViewElement(element, strLabel);
			//ng_waitForElementEnabled(element,20);
			element.clear();
		    //clearTextField(element);
		    ng_sendTab(element, strLabel);
		    //waitForPageToLoad();
		    String strDesc = "Cleared text for <span style='font-weight:bold;color:#4CAF50;'>'" + strLabel + "'</span> textbox.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
		} catch (Exception e) {	
			String strDesc = "Failed to clear text for <span style='color:#ff4d4d;'>'" + strLabel + "'</span> textbox. Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 				
		return Global.bResult;
	}
    
    
    /*----------------------------------------------------------------------------
  	Function Name    	: ng_sendTab
  	Description     	: ng_typeAndTab
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
    public static void ng_sendTab(WebElement ele, String description) {
		try {
			ele.sendKeys(Keys.TAB);
	        waitForPageToLoad();	        									
		} catch (Exception e) {
			
		}		
	}
    
    /*----------------------------------------------------------------------------
  	Function Name    	: ng_getElementText
  	Description     	: ng_getElementText
  	Input Parameters 	: 
  	Return Value    	: 
  	Author		        : 
  	Date of creation	:
	Date of modification:
  	----------------------------------------------------------------------------*/
    public static String ng_getElementText(WebElement element, String strLabel, String strKey) throws Exception {
    	String strVal = getTestDataValue(strKey);  				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {			
			waitForPageToLoad();			
			//explicitWait(element,20);
			WebElement objElement = wait.until(ExpectedConditions.visibilityOf(element));
			//ng_waitUntilElementDisplayed(objElement,20);
			//ng_scrollIntoViewElement(objElement, strLabel);			

			if(Global.gstrHighlighter == true) {
				highLighterMethod(objElement);
			}
			String value = objElement.getText();
			String strDesc = "Successfully get the text <span style='font-weight:bold;color:#4CAF50;'>'"+ value +"'</span> for '" + strLabel + "'  WebElement.";
			writeHTMLResultLog(strDesc, "pass");
			takeScreenShotAndLog("pass");
			Global.bResult = "True";					
		} catch (Exception e) {
			String strDesc = "Failed to get the text for  <span style='color:#ff4d4d;'>'" + strLabel + "'</span> Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 		
		return Global.bResult;
	}
    
    /*----------------------------------------------------------------------------
   	Function Name    	: explicitWait
   	Description     	: explicit Wait
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
    public static void explicitWait(WebElement elementID, long timeout) {	
		/*long startTime = 0;
		long endTime = 0;
		By locator = null;
		try {
			locator = getByFromWebElement(elementID);
			
			
			final By locator_Final = locator;

			startTime = System.currentTimeMillis();
			elementID = (new WebDriverWait(driver, timeout))
					.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver wd) {
							return wd.findElement(locator_Final);
						}
					});
			try {
				//driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
				wait.until(ExpectedConditions.visibilityOf(elementID));			
				//wait.until(ExpectedConditions.visibilityOfElementLocated(locator_Final));
				//wait.until(ExpectedConditions.presenceOfElementLocated(locator_Final));
				wait.until(ExpectedConditions.elementToBeClickable(locator_Final));									
			} catch (TimeoutException te) {
				System.out.println("Time out exception for wait visibility: " + te.getMessage());
			}
			endTime = System.currentTimeMillis();			
		} catch (Exception e) {
			endTime = System.currentTimeMillis();			
		}*/
	}
    
    /*----------------------------------------------------------------------------
   	Function Name    	: getByFromWebElement
   	Description     	: get By From WebElement
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
    public static By getByFromWebElement(WebElement elementID) {
		By byLocator = null;
		String allStr = getNameFromObjectID(elementID);
		String elementPath = allStr.split(":")[1].trim();
		String locatorName = allStr.split(":")[0].trim();
		if (elementPath.endsWith("]")) {
			elementPath = elementPath.substring(0, elementPath.length() - 1)
					.trim();
		}
		if (locatorName.indexOf(" ") != -1
				&& !locatorName.equals("partial link text")) {
			String partOne = locatorName.split(" ")[0];
			String partTwo = locatorName.split(" ")[1];
			partTwo = Character.toUpperCase(partTwo.charAt(0))
					+ partTwo.substring(1);
			locatorName = partOne + partTwo;
		} else if (locatorName.equals("partial link text")) {
			locatorName = "partialLinkText";
		}
		try {
			switch (locatorName) {
			case "className":
				byLocator = By.className(elementPath);
				break;
			case "cssSelector":
				byLocator = By.cssSelector(elementPath);
				break;
			case "id":
				byLocator = By.id(elementPath);
				break;
			case "linkText":
				byLocator = By.linkText(elementPath);
				break;
			case "name":
				byLocator = By.name(elementPath);
				break;
			case "partialLinkText":
				byLocator = By.partialLinkText(elementPath);
				break;
			case "tagName":
				byLocator = By.tagName(elementPath);
				break;
			case "xpath":
				byLocator = By.xpath(elementPath);
				break;
			default:
				break;
			}
		} catch (NoSuchElementException elEx) {
			elEx.printStackTrace();
		}
		return byLocator;
	}
    
    public static String getNameFromObjectID(WebElement elementID) {
		return elementID.toString().split("->")[1].trim();
	}
    
    /*----------------------------------------------------------------------------
   	Function Name    	: ng_clickElementUsingJS
   	Description     	: Method clicks on a specific element using JS.
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
    public static String ng_clickElementUsingJS(WebElement element, String strLabel, String strKey) throws Exception {
    	String strVal = getTestDataValue(strKey); 				 
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {
			waitForPageToLoad();
			explicitWait(element,20);
			ng_waitUntilElementDisplayed(element,20);
			ng_scrollIntoViewElement(element, strLabel);			
			ng_waitForElementEnabled(element,20);
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			waitForPageToLoad();
			String strDesc = "Successfully clicked on <span style='font-weight:bold;color:#4CAF50;'>'" + strLabel + "'</span>  WebElement.";
			writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";			
		} catch (Exception e) {
			String strDesc = "WebElement <span style='color:#ff4d4d;'>'" + strLabel + "'</span> is not displayed on the screen. Error Message : " + e.getMessage();
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return Global.bResult;	
	}
    
    /*----------------------------------------------------------------------------
   	Function Name    	: clickItem
   	Description     	: clickItem - Application Utility
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        :
   	Date of creation	:
	Date of modification: 
   	----------------------------------------------------------------------------*/
    public static String clickItem(String strItenVal) throws Exception {		
		try {			
			Utility.waitForPageToLoad();
			WebElement element = driver.findElement(By.xpath("//td//span[text()='"+ strItenVal +"']"));
			explicitWait(element,20);
			ng_scrollIntoViewElement(element, strItenVal);
			if(Global.gstrHighlighter == true) {
				highLighterMethod(element);
			}
			element.click();
			Utility.waitForPageToLoad();

			String strDesc = "Successfully clicked on item : <span style='font-weight:bold;color:#4CAF50;'>" + strItenVal + "</span>";
			Utility.writeHTMLResultLog(strDesc, "pass");
			Global.bResult = "True";
			
		} catch (Exception e) {
			String strDesc = "Failed to click on <span style='color:#ff4d4d;'> " + strItenVal + " </span> Error Message : " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		}
		return Global.bResult;
	}
    
    /*----------------------------------------------------------------------------
   	Function Name    	: logoutFinally
   	Description     	: logoutFinally
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
    public static String logoutFinally() throws Exception {	
    	//WebDriverWait wait = new WebDriverWait(driver, 20);
		try {						
			Utility.waitForPageToLoad();						
						
		    WebElement elmUserName = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a//span[contains(@class,'xiq')]"))));		    
			highLighterMethod(elmUserName);			
			elmUserName.click();
			
			Utility.waitForPageToLoad();
			//ng_waitImplicitly(5);						
			
			WebElement elmSignOut = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Sign Out')]"))));			
			highLighterMethod(elmSignOut);			
			elmSignOut.click();
			Utility.waitForPageToLoad();
			//ng_waitImplicitly(5);
											
		    WebElement eleWarning = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()='Yes']"))));
		    highLighterMethod(eleWarning);
		    if (eleWarning != null) {
		    	eleWarning.click();
		    	Utility.waitForPageToLoad();
				//ng_waitImplicitly(5);
		    }		    						
			
			WebElement elmConfirm = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@id='Confirm']"))));			 			
			highLighterMethod(elmConfirm);			
			elmConfirm.click();
			Utility.waitForPageToLoad();
			driver.quit();
		} catch (Exception e) {
			driver.quit();
		}
		return Global.bResult;	
	}
    
    /*----------------------------------------------------------------------------
   	Function Name    	: highLighterMethod
   	Description     	: highLighterMethod
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
    public static void highLighterMethod(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	}
    
    /*----------------------------------------------------------------------------
   	Function Name    	: clickWarning
   	Description     	: clickWarning
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void clickWarning(WebElement element) throws Exception {
		
		try {
			waitForPageToLoad();
			explicitWait(element,20);
			ng_scrollIntoViewElement(element, "");
			ng_waitImplicitly(2);
			ng_waitForElementEnabled(element,20);
			if (element != null) {
				element.click();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		
	}
	/*----------------------------------------------------------------------------
   	Function Name    	: quitdriver
   	Description     	: quitdriver
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void quitdriver(){
		if(driver != null) {
			driver.quit();
		}
	}
    
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitForElementEnabled
   	Description     	: Wait until element is enabled for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void ng_waitForElementEnabled(WebElement ele, int sTime) {
		//ng_waitImplicitly(1);
		for (int k = 0; k <= sTime; k++) {
			//ng_waitImplicitly(1);
			if (ele.isEnabled()) {
				break;
			}
		}
	}
	
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitUntilElementDisplayed
   	Description     	: Wait until element is displayed for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void ng_waitUntilElementDisplayed(WebElement ele, int sTime) {
		//ng_waitImplicitly(1);
		for (int k = 0; k <= sTime; k++) {
			//ng_waitImplicitly(1);
			if (ele.isDisplayed()) {
				break;
			}
		}
	}
	
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitUntilElementVisible
   	Description     	: Wait until element is Visible for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static void ng_waitUntilElementVisible(WebElement ele, int sTime) {
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	/*----------------------------------------------------------------------------
   	Function Name    	: ng_waitUntilElementVisible
   	Description     	: Wait until element is Visible for particular number of seconds
   	Input Parameters 	: 
   	Return Value    	: 
   	Author		        : 
   	Date of creation	:
	Date of modification:
   	----------------------------------------------------------------------------*/
	public static String ng_RandomAlphaNum (String strChar,int length){
		String alphabet = 
		        new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
		int n = alphabet.length(); //10

		String result = new String(); 
		Random r = new Random(); //11

		for (int i=0; i<length; i++) //12
		    result = result + alphabet.charAt(r.nextInt(n)); //13

		return strChar + result;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: ng_clickSimply
	Description     	: This function clicks the WebElement object
	Input Parameters 	: strObject - Object Name of Web Element
						: strLabel - To be printed on extent report
	                    : strKey - Paramiter name to get the data value from TestData Table
	Return Value    	: bResult
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_clickSimply(WebElement element, String strLabel, String strKey) throws Exception {
		String strVal = Utility.getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		try {		
			Utility.waitForPageToLoad();								
			if(Global.gstrHighlighter == true) {
				Utility.highLighterMethod(element);
			}			
			element.click();	
			Utility.waitForPageToLoad();
			String strDesc = "Successfully clicked on <span style='font-weight:bold;color:#4CAF50;'>'" + strLabel + "'</span>  WebElement.";			
			Utility.writeHTMLResultLog(strDesc, "pass");
			Utility.takeScreenShotAndLog("pass");			
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "WebElement <span style='color:#ff4d4d;'>" + strLabel +  "'</span> is not displayed on the screen. Error Message : " + e.getMessage();
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
		logoutFinally();
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
        quitdriver();					       
		File file1 = new File(Global.filePath);
		File file2 = new File(Global.gstrTestResultLogDir + File.separator + "index.html");
		if(file2.exists()){
			file2.delete();
		}
		FileUtils.copyFile(file1, file2);
//      Path source = Paths.get(Global.filePath);
//		Files.move(source, source.resolveSibling(Global.gTCName + Global.gstrTimesTamp +"_Pass.html"));
		
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: createHTMLFile
	Description     	: create HTML File
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void createHTMLFile() throws IOException {
		//Log4j implementation
    	Global.logger = Logger.getLogger(HybridExecuteTest.class.getName());
    	String log4jConfPath = System.getProperty("user.dir")+ File.separator + "log4J.properties";
		PropertyConfigurator.configure(log4jConfPath);	
		//Create HTML file    	
    	Global.gstrTimesTamp = "_"+ Utility.getCurrentDatenTime("dd-MM-yy")+"_"+ Utility.getCurrentDatenTime("H-mm-ss a");
    	Global.filePath = Global.gstrTestResultLogDir + File.separator + "Batch" + Global.gstrTimesTamp  +".html";        	
        File myFile = new File(Global.filePath);
        if (! myFile.exists() ) {
            myFile.createNewFile();
            Global.report = new ExtentReports();
            Global.htmlReporter = new ExtentHtmlReporter(Global.filePath);                
            Global.report.attachReporter(Global.htmlReporter);
            Global.htmlReporter.loadXMLConfig(new File(Global.gstrExtentConfigDir + File.separator + "extent-config.xml"));                
                            
            Global.report.setSystemInfo("Application/s", "Inventory Management System");
            Global.report.setSystemInfo("Browser", "Chrome Version 64.0.3282");                
            Global.report.setSystemInfo("Version", "R12");
            Global.report.setSystemInfo("Instance Type", "QA1");
            Global.report.setSystemInfo("Java Version", System.getProperty("java.version")); 
            Global.report.setSystemInfo("OS", System.getProperty("os.name"));                
            InetAddress ip;
            ip = InetAddress.getLocalHost();
            String ipAddrs = ip.getHostAddress();
            String hostName = ip.getHostName();
            Global.report.setSystemInfo("Host Name", hostName +"/" + ipAddrs);                
            Global.report.setSystemInfo("User Name", System.getProperty("user.name"));             
        }  			
		
	}
	/*----------------------------------------------------------------------------
	Function Name    	: executionFooter
	Description     	: execution Footer
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static void executionFooter(String testType) throws Exception {
		if(Global.objErr == "11") {	
			if(testType.equalsIgnoreCase("Mobile")) {
				failFooter();
			} else {
				failFooter();
			}												
		} 
		else {						
			if(testType.equalsIgnoreCase("Mobile")) {
				MobApkUtility.passFooter();
			} else {
				Utility.passFooter();
			}						
		}
		
	}
	/*----------------------------------------------------------------------------
	Function Name    	: ng_verifyPageTitle
	Description     	: verify Page Title
	Input Parameters 	: Testdata parameter name
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:
	----------------------------------------------------------------------------*/
	public static String ng_verifyPageTitle(String strKey) throws Exception {
		String strVal = Utility.getTestDataValue(strKey);
		if ((strVal.contains("SKIP")) || (Global.objErr == "11")) {
			return String.valueOf(true);
		}
		String strActualTitle = null;
		String strExpectedTitle = null;
		try {		
			Utility.waitForPageToLoad();								
			strActualTitle = driver.getTitle();
			strExpectedTitle = strVal;
			String strDesc = "Successfully verified page title - Actual : <span style='font-weight:bold;color:#4CAF50;'>'" + strActualTitle + "'</span>  Expected : <span style='font-weight:bold;color:#4CAF50;'>'" + strExpectedTitle + "'</span>.";			
			Utility.writeHTMLResultLog(strDesc, "pass");
			Utility.takeScreenShotAndLog("pass");			
			Global.bResult = "True";
		} catch (Exception e) {
			String strDesc = "Failed to verify page title - Actual : <span style='font-weight:bold;color:#4CAF50;'>'" + strActualTitle + "'</span>  Expected : <span style='font-weight:bold;color:#4CAF50;'>'" + strExpectedTitle + "'</span>.";
			writeHTMLResultLog(strDesc, "fail");
			takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} 								
		return Global.bResult;		
	}
	
}