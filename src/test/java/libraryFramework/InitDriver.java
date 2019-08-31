package libraryFramework;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.Status;

//import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class InitDriver
{
    private static InitDriver driver_instance = null;
    
    public static WebDriver wDriver = null;	 
    public static AndroidDriver<AndroidElement> aDriver = null;
    
    private InitDriver(String browser, String testType) throws IOException {	    	
        try {
            if (browser.equalsIgnoreCase("Firefox")) {            	
            	System.setProperty("webdriver.gecko.driver", Global.gstrGeckoDriverDir);
        		WebDriver driver = new FirefoxDriver();
            	driver.manage().window().maximize();
            } else if (browser.equalsIgnoreCase("Chrome") && testType.equalsIgnoreCase("Desktop")){            	          	
                System.setProperty("webdriver.chrome.driver",Global.gstrChromeDriverDir);
                wDriver = new ChromeDriver();
                wDriver.manage().window().maximize();
            } else if (browser.equalsIgnoreCase("Chrome") && testType.equalsIgnoreCase("Mobile")){            	          	
            	DesiredCapabilities capabilities = DesiredCapabilities.android();
        		capabilities.setCapability("noReset", true);
        		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "100000");
        		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.CHROME);
        		capabilities.setCapability(MobileCapabilityType.PLATFORM,Platform.ANDROID);
        		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"my phone"); 
        		capabilities.setCapability(MobileCapabilityType.VERSION,"7.0");         		
        		URL url= new URL("http://127.0.0.1:4723/wd/hub");        		
        		wDriver = new AndroidDriver<AndroidElement>(url, capabilities);        		        		

            } else if (browser.equalsIgnoreCase("Edge")) {            	          	
            	System.setProperty("webdriver.edge.driver",Global.gstrMicrosoftDriverDir);
        		WebDriver driver = new EdgeDriver();
                driver.manage().window().maximize();
            } else if (browser.equalsIgnoreCase("MobileApk")) {            	          	
            	DesiredCapabilities capabilities = new DesiredCapabilities();
        		capabilities.setCapability("deviceName", "My New Phone"); 
        		capabilities.setCapability("platformVersion", "7.0");
        		capabilities.setCapability("platformName", "Android");              
        		capabilities.setCapability("appPackage", "in.billionhands.instantinventory");                  
        		capabilities.setCapability("appActivity", "in.billionhands.instantinventory.MainActivity");  
        		capabilities.setCapability("noReset", true);
        		capabilities.setCapability("unicodeKeyboard", true);
        		capabilities.setCapability("resetKeyboard", true);
        		URL url= new URL("http://127.0.0.1:4723/wd/hub");
        		aDriver = new AndroidDriver<AndroidElement>(url, capabilities);
            } 
//            else if (browser.equalsIgnoreCase("MobileWeb")) {            	          	
//            	DesiredCapabilities capabilities = DesiredCapabilities.android();
//        		capabilities.setCapability("noReset", true);
//        		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "100000");
//        		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.CHROME);
//        		capabilities.setCapability(MobileCapabilityType.PLATFORM,Platform.ANDROID);
//        		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
//        		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"my phone"); 
//        		capabilities.setCapability(MobileCapabilityType.VERSION,"7.0");         		
//        		URL url= new URL("http://127.0.0.1:4723/wd/hub");        		
//        		aDriver = new AndroidDriver<AndroidElement>(url, capabilities);
//            }
            
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
        }        
        Global.test = Global.report.createTest(Global.gTCName,Global.gTCDescription);
        Global.test.log(Status.INFO, "<span style='font-weight:bold;color:#1ff3f5;'>Test Execution Started</span>");
        
    }
    // static method to create instance of Singleton class
    public static InitDriver getInstance(String drivername, String testType) throws IOException {        
		if (aDriver == null){					
			driver_instance = new InitDriver(drivername,testType);
		}
		if (wDriver == null){					
			driver_instance = new InitDriver(drivername,testType);
		}			
        return driver_instance;
    }
}
