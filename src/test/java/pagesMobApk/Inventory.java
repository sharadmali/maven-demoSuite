package pagesMobApk;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;

import libraryFramework.Global;
import libraryFramework.TestData;
import libraryProject.MobApkUtility;

public class Inventory {
	
//	@FindBy(xpath = MobileConstant.INVENTORY_ITEM)
//    WebElement elmInventoryItem;     
//	@FindBy(id = MobileConstant.ADD_ITEM)
//    WebElement elmAddItem;	
//	@FindBy(id = MobileConstant.PRODUCT_NAME)
//    WebElement edtProductName;
//	@FindBy(id = MobileConstant.PRODUCT_ID)
//    WebElement edtProductID;
//	@FindBy(id = MobileConstant.CATEGORY)
//    WebElement edtCategory;
//	@FindBy(id = MobileConstant.LOCATION)
//    WebElement edtLocation;
//	@FindBy(id = MobileConstant.QUANTITY)
//    WebElement edtQuantity;
//	@FindBy(id = MobileConstant.AVG_PRICE)
//    WebElement edtAvgPrice;
//	@FindBy(id = MobileConstant.ALERT)
//    WebElement edtAlert;
//	@FindBy(id = MobileConstant.SAVE)
//    WebElement btnSave;
			
	@FindBy(xpath = "//android.widget.TextView[@text='Inventory Items']")
    WebElement elmInventoryItem;     
	@FindBy(id = "in.billionhands.instantinventory:id/itemAdd")
    WebElement elmAddItem;	
	@FindBy(id = "in.billionhands.instantinventory:id/pName")
    WebElement edtProductName;
	@FindBy(id = "in.billionhands.instantinventory:id/pId")
    WebElement edtProductID;
	@FindBy(id = "in.billionhands.instantinventory:id/categoryEdtx")
    WebElement edtCategory;
	@FindBy(id = "in.billionhands.instantinventory:id/locationEdtx")
    WebElement edtLocation;
	@FindBy(id = "in.billionhands.instantinventory:id/pQuantity")
    WebElement edtQuantity;
	@FindBy(id = "in.billionhands.instantinventory:id/avgPriceEdtx")
    WebElement edtAvgPrice;
	@FindBy(id = "in.billionhands.instantinventory:id/pAlert")
    WebElement edtAlert;
	@FindBy(id = "in.billionhands.instantinventory:id/itemSave")
    WebElement btnSaveItem;
	@FindBy(id = "in.billionhands.instantinventory:id/buttonDelete")
    WebElement btnDeleteItem;
	@FindBy(id = "android:id/button1")
    WebElement btnDeleteItemConf;
	
	public Inventory(MobApkUtility util) throws Exception{
        //this.driver = driver;
        PageFactory.initElements(util.ng_returnDriver(), this);  
    }
	
	/*----------------------------------------------------------------------------
	Function Name    	: addInventoryItem
	Description     	: This function add Inventory Item
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/     
    public void addInventoryItem( ) throws Exception {    	
        if(Global.objErr == "11"){
            return;
        }
    	try {    	
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);						
			Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);
			Global.logger.info("Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName); 
			
			MobApkUtility.mb_clickWebElement(elmInventoryItem,"Inventory Item","InventoryItemClick");
			MobApkUtility.mb_clickWebElement(elmAddItem,"Add Item","AddItemClick");
			
			MobApkUtility.mb_enterText(edtProductName,"Product Name","ProductNameSet");
			MobApkUtility.mb_enterText(edtProductID,"Product ID","ProductIDSet");
			MobApkUtility.mb_enterText(edtCategory,"Category","CategorySet");
			MobApkUtility.mb_enterText(edtLocation,"Location","LocationSet");
			MobApkUtility.mb_enterText(edtQuantity,"Quantity","QuantitySet");
			MobApkUtility.mb_enterText(edtAvgPrice,"AvgPrice","AvgPriceSet");
			MobApkUtility.mb_enterText(edtAlert,"Alert","AlertSet");
			MobApkUtility.mb_clickWebElement(btnSaveItem,"Alert","AlertClick");
			
			
		} catch (Exception e) {
			Global.objErr = "11";			
		}		    
    }
    
    /*----------------------------------------------------------------------------
	Function Name    	: deleteInventoryItem
	Description     	: This function delete Inventory Item
	Input Parameters 	: None
	Return Value    	: None
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/     
    public void deleteInventoryItem( ) throws Exception {    	
        if(Global.objErr == "11"){
            return;
        }
    	try {    	
			TestData td = new TestData ();
			Global.objData = (HashMap) td.readTestData (Global.gTCID, Global.gstrClassName, Global.gstrMethodName);						
			Global.test.log(Status.INFO,"Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName);
			Global.logger.info("Class and Method : "+ Global.gstrClassName +" . "+Global.gstrMethodName); 
			
			MobApkUtility.mb_clickWebElement(elmInventoryItem,"Inventory Item","InventoryItemClick");
			MobApkUtility.mb_clickElementByText("Item Name","ItemNameClick");
			MobApkUtility.mb_clickWebElement(btnDeleteItem,"Delete Item","DeleteItemClick");
			MobApkUtility.mb_clickWebElement(btnDeleteItemConf,"Delete Item Conf","DeleteItemConfCLick");
			
		} catch (Exception e) {
			Global.objErr = "11";			
		}		    
    }
}
