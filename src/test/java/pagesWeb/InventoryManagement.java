package pagesWeb;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

import com.aventstack.extentreports.Status;
import com.mongodb.util.Util;
import libraryFramework.Global;
import libraryFramework.TestData;
import libraryProject.AppUtility;
import libraryProject.Utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryManagement {    
    
    @FindBy(xpath="//span[text()='Create New Customer']")
    WebElement lnkCreateNewCustomer;    
    @FindBy(name="clientpanel:clientname")
    WebElement edtCustomerName;    
    @FindBy(name="clientpanel:salesTaxId")
    WebElement edtVAT;    
    @FindBy(name="clientpanel:type")
    WebElement lstType;    
    @FindBy(name="clientpanel:country")
    WebElement lstCountry;    
    @FindBy(name="clientpanel:currency")
    WebElement lstCurrency;
    @FindBy(name="clientpanel:language")
    WebElement lstLanguage;
    @FindBy(name="clientpanel:industry")
    WebElement lstIndustry;
    @FindBy(name="clientpanel:compsize")
    WebElement lstCompanySize;
    @FindBy(name="clientpanel:revenue")
    WebElement lstRevenue;
    @FindBy(name="contactpanel:email")
    WebElement edtEmail;
    @FindBy(name="contactpanel:fname")
    WebElement edtFirstName;
    @FindBy(name="contactpanel:lname")
    WebElement edtLastName;    
    @FindBy(name="contactpanel:address1")
    WebElement edtStreet1;
    @FindBy(name="contactpanel:address2")
    WebElement edtStreet2;
    @FindBy(name="contactpanel:city")
    WebElement edtCity;
    @FindBy(name="contactpanel:state")
    WebElement edtState;
    @FindBy(name="contactpanel:zip")
    WebElement edtPincode;
    @FindBy(name="contactpanel:cellphone")
    WebElement edtMobile ;
    @FindBy(linkText="Save")
    WebElement btnSave;
    
    public InventoryManagement() throws Exception{ 	
        PageFactory.initElements(Utility.ng_returnDriver(), this);
    }       
    /*----------------------------------------------------------------------------
    Function Name    	: createCustomer
    Description     	: This function used to create Customer 
    Author				:     
    Date of creation	:
	Date of modification:
    ----------------------------------------------------------------------------*/ 
    
    public void createCustomer() throws Exception {    	
        if(Global.objErr == "11"){
            return;
        }    
        try {
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);	        	    
			Global.test.log(Status.INFO,"<span style='font-weight:bold;color:#3399ff;'>Class and Method : "+ Global.gstrClassName + " -> " + Global.gstrMethodName + "</span>");
			Global.logger.info("Class and Method : "+ Global.gstrClassName +" -> "+Global.gstrMethodName); 
			
			Utility.waitForPageToLoad();
    	    Utility.ng_clickSimply(lnkCreateNewCustomer, "Create New Customer", "CreateNewCustomerClick");
    	    Utility.ng_verifyPage("Create New Customer", "CreateNewCustomerCheck");
    	    Utility.ng_enterText(edtCustomerName, "Customer Name", "CustomerNameSet");
    	    Utility.ng_enterText(edtVAT, "VAT/Tax ID", "VATSet");
    	    Utility.ng_SelectList(lstType, "Type", "TypeSelect");
    	    Utility.ng_SelectList(lstCountry, "Country", "CountrySelect");
    	    Utility.ng_SelectList(lstCurrency, "Currency", "CurrencySelect");
    	    Utility.ng_SelectList(lstLanguage, "Language", "LanguageSelect");
    	    Utility.ng_SelectList(lstIndustry, "Industry", "IndustrySelect");
    	    Utility.ng_SelectList(lstCompanySize, "Company Size", "CompanySizeSelect");
    	    Utility.ng_SelectList(lstRevenue, "Revenue", "RevenueSelect");
    	    Utility.ng_enterText(edtEmail, "Email", "EmailSet");
    	    Utility.ng_enterText(edtFirstName, "First Name", "FirstNameSet");
    	    Utility.ng_enterText(edtLastName, "Last Name", "LastNameSet");
    	    Utility.ng_enterText(edtStreet1,"Street 1","Street1Set");
    	    Utility.ng_enterText(edtStreet2,"Street 2","Street2Set");
    	    Utility.ng_enterText(edtCity,"City","CitySet");
    	    Utility.ng_enterText(edtState,"State","StateSet");
    	    Utility.ng_enterText(edtPincode,"Pincode","PincodeSet");
    	    Utility.ng_enterText(edtMobile,"Mobile","MobileSet");
    	    Utility.ng_clickSimply(btnSave, "Save", "SaveClick");
    	    
		} catch (Exception e) {
			Global.objErr = "11";			
		}      
    }
    

    

    

    
}


