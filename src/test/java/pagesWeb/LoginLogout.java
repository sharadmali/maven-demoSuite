package pagesWeb;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import libraryFramework.Global;
import libraryFramework.TestData;
import libraryProject.Utility;

public class LoginLogout {         
	// Login page ----------------------------------------------------------------
    @FindBy(name="username")
    WebElement edtUserName;       
    @FindBy(name="password")
    WebElement edtPassword;     
    @FindBy(id="submit")
    WebElement btnLogIn;        
    // Logout page ---------------------------------------------------------------        
    @FindBy(linkText="Logout")
    WebElement btnSighOut;     
    //----------------------------------------------------------------------------
    
    public LoginLogout() throws Exception{
        PageFactory.initElements(Utility.ng_returnDriver(), this);        
    }
    /*----------------------------------------------------------------------------
    Function Name    	: applicationURL
    Description     	: This function used to launch the application URL.    
    Author				:  
    Date of creation	:
	Date of modification:   
    ----------------------------------------------------------------------------*/        
    public void applicationURL( ) throws Exception {    	
        if(Global.objErr == "11"){
            return;
        }
    	try {    	
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);						
			Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#3399ff;'>Class and Method : "+ Global.gstrClassName + " -> " + Global.gstrMethodName + "</span>");
			Global.logger.info("Class and Method : "+ Global.gstrClassName +" -> "+Global.gstrMethodName); 
			
			Utility.ng_invokeBrowser("URL");
		} catch (Exception e) {
			Global.objErr = "11";			
		}		    
    }
    
    /*----------------------------------------------------------------------------
    Function Name    	: login
    Description     	: This function used to login to the application. 
    Author				:  
    Date of creation	:
	Date of modification:   
    ----------------------------------------------------------------------------*/ 
    public void login() throws Exception {
        if(Global.objErr == "11"){
            return;
        }   
    	try {
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	    			
			Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#3399ff;'>Class and Method : "+ Global.gstrClassName + " -> " + Global.gstrMethodName + "</span>");    
			Global.logger.info("Class and Method : "+ Global.gstrClassName +" -> "+Global.gstrMethodName);   
			
			Utility.ng_verifyPage("Login","LoginCheck");
			Utility.ng_enterText(edtUserName,"User Name","UserNameSet");
			Utility.ng_enterText(edtPassword,"Password","PasswordSet");
			Utility.ng_clickSimply(btnLogIn,"LogIn","LogInClick");	
			
		} catch (Exception e) {
			Global.objErr = "11";			
		}     
    }
       
    /*----------------------------------------------------------------------------
    Function Name    	: logout
    Description     	: This function used to logout the application.  
    Author				:  
    Date of creation	:
	Date of modification:
    ----------------------------------------------------------------------------*/
    public void logout() throws Exception {
		if(Global.objErr == "11"){			
		    return;
		} 
		try {
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	    			
			Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#3399ff;'>Class and Method : "+ Global.gstrClassName + " -> " + Global.gstrMethodName + "</span>");
			Global.logger.info("Class and Method : "+ Global.gstrClassName +" -> "+Global.gstrMethodName); 
						
			Utility.ng_clickSimply(btnSighOut,"Sign Off","SignOffClick");						
			Utility.quitdriver();
		} catch (Exception e) {
			Global.objErr = "11";			
		}		   	    
	}
        
}
