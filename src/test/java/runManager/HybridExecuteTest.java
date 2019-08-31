package runManager;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
//import com.relevantcodes.extentreports.ExtentReports;
import libraryFramework.Global;
import libraryFramework.InitScript;
import libraryFramework.SendReportInEmail;
import libraryProject.MobApkUtility;
import libraryProject.Utility;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

import org.apache.commons.exec.OS;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Parameters;
import java.net.InetAddress;
/*----------------------------------------------------------------------------
Function Name    	: HybridExecuteTest - Run Manager
Description     	: This will control the execution.
Input Parameters 	: None
Return Value    	: None
Author		        : Sharad Mali
Date of creation	: 
Date of modification: 	
----------------------------------------------------------------------------*/
public class HybridExecuteTest  {   	 
    @Test
    @Parameters({ "browser", "testType" })
    public void runManager(String browser, String testType) throws Exception {      		
        try {        	
        	//Create HTML file
        	Utility.createHTMLFile();        	    
            //Read Groups
            String strGroupQuery = "Select * from Groups where Run='Y'";
            List<String> arrGroupList = InitScript.readGroupData(Global.gstrControlFilesDir + "GroupControlFile.xlsx",strGroupQuery);            
            for(String strGroupName : arrGroupList) {               	
            	String strGroupSheetQuery = "Select * from "+ strGroupName +" where Run='Y'";             	
                List<Integer> arrTCSequence = InitScript.readTCInSequence(Global.gstrControlFilesDir + "GroupControlFile.xlsx",strGroupSheetQuery);                  
                for(Integer intTCSequence : arrTCSequence) { 
                	if(Global.gstrSequenceFlag.equalsIgnoreCase("true")){
                		strGroupSheetQuery = "Select * from "+ strGroupName +" where Execution_Sequence = '"+ intTCSequence +"'"; 
					} else {
						strGroupSheetQuery = "Select * from "+ strGroupName +" where ID = '"+ intTCSequence +"'"; 
					}                	                	           
                	InitScript.readGroupSheetData(Global.gstrControlFilesDir + "GroupControlFile.xlsx",strGroupSheetQuery);                	
                	for(int iFreq=1;iFreq<=Global.gstrFrequency;iFreq++) {                		                	
	                	//Driver initialization
	                	if(browser.equals("MobileApk")) {
	                		new MobApkUtility(browser,testType);
	                	} else {
	                		new Utility(browser,testType);
	                	}
	                	Global.test.assignCategory(strGroupName);
	                	//Read Batch
	                	String strBatchQuery = "Select * from BatchSheet where BatchTestFile='"+ Global.gstrBatchName  +"'";
	                	List<String> arrBatchList = InitScript.readBatchData(Global.gstrBatchFilesDir + "BatchSheet.xlsx",strBatchQuery);   
	                	for(String strComponentName : arrBatchList) {
	                		Global.gstrComponentName = strComponentName;
	                		if (Global.gstrComponentName != "") {
								Global.gstrClassName = Global.gstrComponentName.split("\\.")[0];
								Global.gstrMethodName = Global.gstrComponentName.split("\\.")[1]; 
								//Reflection call
								if(browser.equals("MobileApk")) {
									Class<?> cls = Class.forName("pagesMobApk." + Global.gstrClassName);		                		
									Object obj = cls.newInstance();	
			                		Method m = cls.getMethod(Global.gstrMethodName);
			                		m.invoke(obj);		//Call components
			                	} else {
			                		Class<?> cls = Class.forName("pagesWeb." + Global.gstrClassName);		                		
									Object obj = cls.newInstance();	
			                		Method m = cls.getMethod(Global.gstrMethodName);	
									m.invoke(obj);		//Call components								
			                	}													
							}                		
	                	}
	                	Utility.executionFooter(testType);							
                	}
				}                
			}							 
		} catch (Exception e) {
			String strDesc = "There is an issue in Runmanager. Please check... " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");
			Utility.takeScreenShotAndLog("fail");
			Global.bResult = "False";
			Global.objErr = "11";
		} finally {
	        if (Global.gstrSendEmail == true) {
	        	if ((Global.gstrEmailMode).equalsIgnoreCase("gmail")){
	        		SendReportInEmail.sendGmailReport();
	        	}else {
	        		SendReportInEmail.sendOutlookReport();
	        	}	        	
	        }	        
		}        
    }

}
