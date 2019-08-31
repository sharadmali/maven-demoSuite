package pagesWeb;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;

import libraryFramework.Global;
import libraryFramework.TestData;
import libraryProject.Utility;
import pagesWeb.LoginLogout;
public class HomePage {	    
	// Home page -----------------------------------------------------------------
    @FindBy(xpath="//span[text()='Customer']")
    WebElement lnkCustomer;
    @FindBy(xpath="//span[text()='Vendor']")
    WebElement lnkVendor;        
    //----------------------------------------------------------------------------
    public HomePage() throws Exception{
        PageFactory.initElements(Utility.ng_returnDriver(), this);
    } 
    
    /*----------------------------------------------------------------------------
    Function Name    	: GoToHome
    Description     	: This function used to navigate to home page.     
    Author				:
    Date of creation	:
	Date of modification:
    ----------------------------------------------------------------------------*/ 
    public void goToHome() throws Exception {    	
        if(Global.objErr == "11"){
            return;
        }    
        try {
        	TestData td = new TestData ();
    	    Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	    
    	    Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#3399ff;'>Class and Method : "+ Global.gstrClassName + " -> " + Global.gstrMethodName + "</span>");
    	    Global.logger.info("Class and Method : "+ Global.gstrClassName +" -> "+Global.gstrMethodName); 
    	    
    	    Utility.waitForPageToLoad();            
            Utility.ng_verifyPage("Home","HomeCheck");
            Utility.ng_verifyPageTitle("VerifyTitle");
            Utility.ng_clickSimply(lnkCustomer,"Customer","CustomerClick");
            Utility.ng_clickSimply(lnkVendor,"Vendor","VendorClick");                        
           
		} catch (Exception e) {
			Global.objErr = "11";			
		}       
    }
}
