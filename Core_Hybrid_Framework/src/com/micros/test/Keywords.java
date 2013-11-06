package com.micros.test;
import static com.micros.test.DriverScript.APP_LOGS;
import static com.micros.test.DriverScript.CONFIG;
import static com.micros.test.DriverScript.OR;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class Keywords {
	
	public WebDriver driver;
	public String sHandleBefore = "";
	
	 private enum Mode {
		    xpath, id, linkText, value;
		}
	 private boolean waitUntilExists(String object,String mode) throws InterruptedException
		{
		   boolean result = false;
		  
		   Mode accessBy = Mode.valueOf(mode); 
		   int count = 1;
	       while (count < 10) {
	    	   Thread.sleep(1000);
	    	   switch(accessBy) {
	    	    case xpath:
	    	    	if (driver.findElement(By.xpath(OR.getProperty(object))) != null){
	    	    		result = true;
	    	    		count = 10;
	    	    	}
	    	        break;
	    	    case id:
	    	    	if (driver.findElement(By.id(OR.getProperty(object))) != null){
	    	    		result = true;
	    	    		count = 10;
	    	    	}
	    	        break;
	    	    case linkText:
	    	    	String temp = "";
	    	    	if(object.indexOf("_") > 0){
	    				temp=OR.getProperty(object);
	    			}			
	    			else
	    				temp=object;
	    	    	if (driver.findElement(By.linkText(temp)) != null){
	    	    		result = true;
	    	    		count = 10;
	    	    	}
	    	        break;
	    	   }		    	  
	           count++;
	       }	   
		   return result;
		}
	
	public String openBrowser(String object,String data){		
		APP_LOGS.info("Opening browser");
		if(data.equals("Mozilla"))
			driver=new FirefoxDriver();		
		else if(data.equals("IE"))
			driver=new InternetExplorerDriver();
		else if(data.equals("Chrome")) {
			File file = new File(CONFIG.getProperty("browserpath"));
		   	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		   	driver=new ChromeDriver();
		}			
		driver.manage().window().maximize();
		long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		return Constants.KEYWORD_PASS;

	}
	
	
	public String navigate(String object,String data){		
		APP_LOGS.info("Navigating to URL");
		try{
		driver.navigate().to(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to navigate -- "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public String navigateToBackpage(String object,String data){		
		APP_LOGS.info("Navigating to back page");
		try{
		driver.navigate().back();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to navigate to back page -- "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	
	public String clickLink(String object,String data){
        APP_LOGS.info("Clicking on link ");
        try{
        	if(waitUntilExists(object,"xpath")) {
        		driver.findElement(By.xpath(OR.getProperty(object))).click();
        	}
        	else
        		return Constants.KEYWORD_FAIL+" -- Not able to find the link";
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on link -- "+e.getMessage();
        }
     
		return Constants.KEYWORD_PASS;
	}
	public String clickLink_linkText(String object,String data){
        APP_LOGS.info("Clicking on linkText ");
        try {
        	if(waitUntilExists(object,"xpath")) {
        		driver.findElement(By.linkText(OR.getProperty(object))).click();
        	}
        	else
        		return Constants.KEYWORD_FAIL+" -- Not able to find the linkText";
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on link -- "+e.getMessage();
     	}
		return Constants.KEYWORD_PASS;
	}
		
	
	public  String verifyLinkText(String object,String data){
        APP_LOGS.info("Verifying link Text");
        try{
        	if(waitUntilExists(object,"xpath")) {
	        	String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
	        	String expected=data;
	        	
	        	if(actual.equals(expected))
	        		return Constants.KEYWORD_PASS;
	        	else
	        		return Constants.KEYWORD_FAIL+" -- Link text not verified";
        	}
        	else
        		return Constants.KEYWORD_FAIL+" -- Not able to find the linkText";
        	
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Link text not verified -- "+e.getMessage();
        }        
	}
	
	public  String clickOnAnchor(String object,String data){
        APP_LOGS.info("Clicking on Anchor");
        try{
	          if(waitUntilExists(data,"linkText")) {
	        	  String temp = "";
	    	    	if(object.indexOf("_") > 0){
	    				temp=OR.getProperty(data);
	    			}			
	    			else
	    				temp=data;
	        		driver.findElement(By.linkText(temp)).click();
	          }
	          else
	        		return Constants.KEYWORD_FAIL+" -- Not able to find the Anchor Text";
            }catch(Exception e){
    			return Constants.KEYWORD_FAIL+" -- Not able to click on Anchor Text -- "+e.getMessage();
            }
                
		return Constants.KEYWORD_PASS;
	}
	
	public  String clickButton(String object,String data){
        APP_LOGS.info("Clicking on Button");
        try{
	          if(waitUntilExists(object,"xpath")) {
	        		driver.findElement(By.xpath(OR.getProperty(object))).click();
	          }
	          else
	        		return Constants.KEYWORD_FAIL+" -- Not able to find the Button";
            }catch(Exception e){
    			return Constants.KEYWORD_FAIL+" -- Not able to click on Button -- "+e.getMessage();
            }
                
		return Constants.KEYWORD_PASS;
	}
	
	public  String verifyButtonText(String object,String data){
		APP_LOGS.info("Verifying the button text");
		try{
			 if(waitUntilExists(object,"xpath")) {
				String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
		    	String expected=data;
		
		    	if(actual.equals(expected))
		    		return Constants.KEYWORD_PASS;
		    	else
		    		return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
			 }
			 else
	        	return Constants.KEYWORD_FAIL+" -- Not able to find the Button";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found -- "+e.getMessage();
		}
		
	}
	
	public  String selectList(String object, String data){
		APP_LOGS.info("Selecting List item from list");
		try{
			boolean isExists = false;
			if (waitUntilExists(object,"xpath")){
				
					WebElement select = driver.findElement(By.xpath(OR.getProperty(object)));
					List<WebElement> options = select.findElements(By
							.tagName("option"));
					for (WebElement option : options) {
						if (data.trim().equalsIgnoreCase(option.getText().trim())) {
							option.click();		
							isExists = true;
							break;
						}
					}
					if(!isExists){ 
						return Constants.KEYWORD_FAIL+" -- Not able to find the item in the list";
					}
			}
			else
	        	return Constants.KEYWORD_FAIL+" -- Not able to find the list";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not select from list.-- "+ e.getMessage();	

		}
		
		return Constants.KEYWORD_PASS;	
	}
	
	public String verifyListItems(String object, String data){
		APP_LOGS.info("Verifying all the list elements");		
	try{	
		 if(waitUntilExists(object,"xpath")) {
			boolean isExists = false;
			WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			
			String temp="";

			// extract the expected values from OR. properties
			if(data.indexOf("_") > 0){
				temp=OR.getProperty(data);
			}			
			else
				temp=data;
			
			String allElements[]=temp.split(";");
			// check if size of array == size if list
			if(allElements.length != droplist_cotents.size())
				return Constants.KEYWORD_FAIL +"- size of lists do not match";				
			
			 for (int i=0; i < allElements.length; i++){ 
				 isExists = false;
				for (WebElement option : droplist_cotents) {								
					if (allElements[i].equalsIgnoreCase(option.getText())) {
						isExists = true;
					    break;
					}
				}
				if(!isExists) {									
					return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
				}			
			 }
		 }
		 else
	        	return Constants.KEYWORD_FAIL+" -- Not able to find the droplist";
	}catch(Exception e){
		return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

	}		
		
		return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyListSelection(String object,String data){
		APP_LOGS.info("Verifying the selection of the list");
		try{
			 if(waitUntilExists(object,"xpath")) {
				String expectedVal=data;			
				WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
				List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
				String actualVal=null;
				for(int i=0;i<droplist_cotents.size();i++){
					String selected_status=droplist_cotents.get(i).getAttribute("selected");
					if(selected_status!=null)
						actualVal = droplist_cotents.get(i).getText();			
					}
				
				if(!actualVal.equals(expectedVal))
					return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;
			 }
			 else
		        	return Constants.KEYWORD_FAIL+" -- Not able to find the list";

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return Constants.KEYWORD_PASS;	

	}
	
	public  String selectRadio(String object, String data){
		APP_LOGS.info("Selecting a radio button");
		try{
			 if(waitUntilExists(object,"xpath")) {
				 driver.findElement(By.xpath(OR.getProperty(object))).click();
			 }
			 else
		        	return Constants.KEYWORD_FAIL+" -- Radio button does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyRadioSelected(String object, String data){
		APP_LOGS.info("Verify Radio Selected");
		try{
			if(waitUntilExists(object,"xpath")) {
				String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
				if(checked==null)
					return Constants.KEYWORD_FAIL+"- Radio not selected";	
			}
			else
	        	return Constants.KEYWORD_FAIL+" -- Radio button does not exist";
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	
	public  String checkCheckBox(String object,String data){
		APP_LOGS.info("Checking checkbox");
		try{
			if(waitUntilExists(object,"xpath")) {
				// true or null
				String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
				if(checked==null)// checkbox is unchecked
					driver.findElement(By.xpath(OR.getProperty(object))).click();
			}
			else
	        	return Constants.KEYWORD_FAIL+" -- Checkbox does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public String unCheckCheckBox(String object,String data){
		APP_LOGS.info("Unchecking checkBox");
		try{
			if(waitUntilExists(object,"xpath")) {
				String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
				if(checked!=null)
					driver.findElement(By.xpath(OR.getProperty(object))).click();
			}
			else
	        	return Constants.KEYWORD_FAIL+" -- Checkbox does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	
	public  String verifyCheckBoxSelected(String object,String data){
		APP_LOGS.info("Verifying checkbox selected");
		try{
			if(waitUntilExists(object,"xpath")) {
				String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
				if(checked!=null && data.equalsIgnoreCase("true"))
					return Constants.KEYWORD_PASS;
				else if(checked==null && data.equalsIgnoreCase("false"))
					return Constants.KEYWORD_PASS;
				else
					return Constants.KEYWORD_FAIL + " - Not selected";
				}
			else
	        	return Constants.KEYWORD_FAIL+" -- Checkbox does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";

		}		
		
	}
	
	
	public String verifyText(String object, String data){
		APP_LOGS.info("Verifying the text");
		try{
				if(waitUntilExists(object,"xpath")) {
					String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			    	String expected="";
					if(data.indexOf("_") > 0){
						expected=OR.getProperty(data).trim();
					}
					else
					 expected=data.trim();
		
			    	if((actual.equalsIgnoreCase(expected)) || (actual.contains(expected)))
			    		return Constants.KEYWORD_PASS;
			    	else
			    		return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
				}
				else
		        	return Constants.KEYWORD_FAIL+" -- Text object does not exist";
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
		
	}
	
	public  String writeInInput(String object,String data){
		APP_LOGS.info("Writing in text box");
		
		try{
			if(waitUntilExists(object,"xpath")) {
				
				
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				driver.findElement(By.xpath(OR.getProperty(object))).clear();
				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.BACK_SPACE);
				try {
					//int value = Integer.parseInt(data.trim());  
//					JavascriptExecutor js = (JavascriptExecutor)driver;
//					//js.executeScript("arguments[0].value = '';", driver.findElement(By.xpath(OR.getProperty(object))));
//					js.executeScript("arguments[0].value = '';arguments[0].style.visibility='visible'", driver.findElement(By.xpath(OR.getProperty(object))));
					
					//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.BACK_SPACE);
                	driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);                 
				}
				catch(Exception e) {
					driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
				}
			}
			else
	        	return Constants.KEYWORD_FAIL+" -- Inputbox does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public  String verifyTextinInput(String object,String data){
       APP_LOGS.info("Verifying the text in input box");
       try{
    	   if(waitUntilExists(object,"xpath")) {
				String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
				String expected=data;
	
				if(actual.equals(expected)){
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL+" Not matching ";
				}
    	   }
			else
	        	return Constants.KEYWORD_FAIL+" -- Inputbox does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();
		}		
		
	}		
	
	public  String verifyTitle(String object, String data){
	       APP_LOGS.info("Verifying title");
	       try{
	    	   String actualTitle= driver.getTitle();
	    	   String expectedTitle="";
	    	   if(data.indexOf("_") > 0) {
	    		   expectedTitle = OR.getProperty(data).trim();
	    	   }
	    	   else
	    		   expectedTitle = data.trim();
	    	   
	    	   if(actualTitle.equalsIgnoreCase(expectedTitle))
		    		return Constants.KEYWORD_PASS;
		    	else
		    		return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Error in retrieving title";
			   }		
	}
	
	public String exists(String object,String data){
	       APP_LOGS.info("Checking existance of element");
	       try{
		    	   if(waitUntilExists(object,"xpath")) {
		    		   boolean isExists = driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
			    	   if(data.equalsIgnoreCase("true") && isExists) { // Check for existence of Object 			                                                
			                	return Constants.KEYWORD_PASS;
			    	     }			                                    
			    	   else if(data.equalsIgnoreCase("false") && !(isExists)){ // Check for non-existence of Object 
			    		   return Constants.KEYWORD_PASS;			                                                                                        
			             } 	
			    	   else
			    		   return Constants.KEYWORD_FAIL+" Object visibility is not match";
		    	 }
				else
		        	return Constants.KEYWORD_FAIL+" -- object does not exist";
			   }catch(Exception e){
				   if (data.equalsIgnoreCase("false")){ // Check for non-existence of Object 
		    		   return Constants.KEYWORD_PASS;			                                                                                        
		             } 				 
					return Constants.KEYWORD_FAIL+" Unable toverify the object";		
			   }
	}	
	
	public  String synchronize(String object,String data){
		APP_LOGS.info("Waiting for page to load");
		((JavascriptExecutor) driver).executeScript(
        		"function pageloadingtime()"+
        				"{"+
        				"return 'Page has completely loaded'"+
        				"}"+
        		"return (window.onload=pageloadingtime());");
        
		return Constants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(String object,String data){
		APP_LOGS.info("Waiting for an element to be visible");
		int start=0;
		int time=(int)Double.parseDouble(data);
		try{
		 while(time == start){
			if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
				Thread.sleep(1000L);
				start++;
			}else{
				break;
			}
		 }
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String mouseHover(String object, String data){
		APP_LOGS.info("Switch to normal window");
		Actions action = new Actions(driver);
		try{
			if (waitUntilExists(object,"xpath")){
				
					WebElement devices=driver.findElement(By.xpath(OR.getProperty(object)));					
					action.moveToElement(devices).build().perform();					
					Thread.sleep(20000);										
			}
			else
	        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find the object -- "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	public  String verifyIsClickable(String object, String data){
		APP_LOGS.info("Verify whether the Element is clickable or Not");
		Actions action = new Actions(driver);
		try{
			if (waitUntilExists(object,"xpath")){
				
				String attributeValue = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("href");
				if(attributeValue != null &&  data.toLowerCase().equalsIgnoreCase("true")){
					return Constants.KEYWORD_PASS;
				}
				else if (attributeValue == null &&  data.toLowerCase().equalsIgnoreCase("false")){
					return Constants.KEYWORD_PASS;
				}
				else
					return Constants.KEYWORD_FAIL+" data is not mached";										
			}
			else
	        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find the object -- "+e.getMessage();
		}
		

	}
	
	public  String switchToPopup(String object, String data){
		APP_LOGS.info("Switch to Popup");
		try{
			sHandleBefore = driver.getWindowHandle();				
			for (String Handle : driver.getWindowHandles()) {
				driver.switchTo().window(Handle);
			}	
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to switch to popup -- "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	public  String switchToNormal(String object, String data){
		APP_LOGS.info("Switch to normal window");
		try{
			driver.switchTo().window(sHandleBefore);		
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to switch to popup -- "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	public String pause(String object, String data) throws NumberFormatException, InterruptedException{
		long time = (long)Double.parseDouble(object);
		Thread.sleep(time*1000L);
		return Constants.KEYWORD_PASS;
	}
	
	public  String closeBrowser(String object, String data){
		APP_LOGS.info("Closing the browser");
		try{
			driver.close();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	
	
	/************************APPLICATION SPECIFIC KEYWORDS********************************/
	
	
	public  String verifyLoginFields(String object, String data){
		APP_LOGS.info("Verify login fields");
		boolean result = false;	
		boolean isExists = false;
		try
		{			
				if (driver.findElement(By.xpath(OR.getProperty("username_login_input"))) != null) {							
					isExists = true;
			    }
				else
					isExists = false;
			
			if (isExists){
				if (driver.findElement(By.xpath(OR.getProperty("passwd_login_input"))) != null) {							
					isExists = true;
			    }
				else
					isExists = false;
			}
			if (isExists){
				if (driver.findElement(By.xpath(OR.getProperty("sign_in_button"))) != null) {							
					isExists = true;
			    }
				else
					isExists = false;
			}
			if (isExists) {
				result = true;
			}
			else 
				result = false;
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+"Unable to verify the fields. - "+e.getMessage();
		}
		
		return Constants.KEYWORD_PASS;

	}
	
	public  String verifyBreadCrumbText(String object, String data){
		APP_LOGS.info("Verify Breadcrumb text");
		boolean result = false;	
		boolean isExists = false;
		try
		{	
			if (waitUntilExists(object,"xpath")){
				String breadCrumbValue = driver.findElement(By.xpath(OR.getProperty(object))).getText();
				String[] aArray =  breadCrumbValue.trim().split(">");							 
				  StringBuilder sb = new StringBuilder();
				  for (int i=0; i < aArray.length; i++){
					  if(i > 0){
						  sb.append(" > ");
					  }
					  sb.append(aArray[i].replaceAll("\n", ""));
				  } 
				  
				  String temp="";
				  if(data.indexOf("_") > 0){
						temp=OR.getProperty(data);
					}			
					else
						temp=data.trim();				  
				
				  
				  if(sb.toString().trim().equalsIgnoreCase(temp.trim())){
					  return Constants.KEYWORD_PASS;
				  }
				  else
					  return Constants.KEYWORD_FAIL+" -- Breadcrumb text is not matched";
			}else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to verify the Breadcrumb text. - "+e.getMessage();
		}		

	}
	
	public  String verifyWishlistItemDetails(String object, String data){
		APP_LOGS.info("Verify Wishlist item details");		
		boolean isExists = false;
		try
		{	
			if (waitUntilExists(object,"xpath")){
				 WebElement table_element = driver.findElement(By.xpath(OR.getProperty(object)));
				 List<WebElement> tr_collection=table_element.findElements(By.xpath(OR.getProperty(object) + "/table[2]/tbody/tr/td/table/tbody/tr"));
				 String[] aArray =  data.split(",");	
				 for (int i=0; i < aArray.length; i++){		
			        for(WebElement trElement : tr_collection)
			        {
			            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));			          
			            for(WebElement tdElement : td_collection)
			            {			            										 
			            	 if(tdElement.getText().contains(aArray[i].trim())){
			            		 isExists = true;
			            		 break;
			            	 }			            										 
			            }
			            if(isExists) {
			            	break;
			            }
			        }
				 }	
            	 if(isExists){
            		 return Constants.KEYWORD_PASS;
            	 }
            	 else
            		 return Constants.KEYWORD_FAIL+" -- Item details are not matched"; 
			}else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to verify the Wishlist item details. - "+e.getMessage();
		}			

	}
	
	public  String clickOnZoomButtons(String object, String data){
		APP_LOGS.info("Click on Zoom buttons");
		Actions act = new Actions(driver);
		try
		{	
			if (waitUntilExists(object,"xpath")){
				WebElement devices=driver.findElement(By.xpath(OR.getProperty(object)));
				
				act.moveToElement(devices).build().perform();
				act.click().build().perform();
				if(data != null) {
					if(data.equals("Zoom In") && data.equals("Zoom Out")){
						act.click().build().perform();
					}
				}
			}else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to click on Zoom buttons. - "+e.getMessage();
		}	
		
		return Constants.KEYWORD_PASS;

	}
	
	public  String verifyScrollButton(String object, String data){
		APP_LOGS.info("Verify scroll button position");		
		try
		{	
			if (waitUntilExists(object,"xpath")){
				WebElement scrollButton=driver.findElement(By.xpath(OR.getProperty(object)));
				String style = scrollButton.getAttribute("style");							
				if (Float.parseFloat(style.split(";")[1].split(":")[1].replace("%", "")) > 0) {
					return Constants.KEYWORD_PASS;
				}
				else
					return Constants.KEYWORD_FAIL+" -- scroll button position";
			}else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to verify the scroll button position. - "+e.getMessage();
		}		

	}
	
	public  String mouseHoverNClick(String object, String data){
		APP_LOGS.info("Mousehover and click on object");		
		try
		{				
				Actions act = new Actions(driver);
				WebElement imgButton=driver.findElement(By.xpath(OR.getProperty(object).split("#")[0]));
				act.moveToElement(imgButton).build().perform();
				WebElement quickView = driver.findElement(By.xpath(OR.getProperty(object).split("#")[1]));
				act.moveToElement(quickView).build().perform();
				act.click().build().perform();			
			
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to MouseHover and click on the object. - "+e.getMessage();
		}
		
		return Constants.KEYWORD_PASS;

	}

	
	public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
		// take screen shots
		if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
			// capturescreen
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
			
		}else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
		// capture screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		}
	}
	
	
	
	public String verifySearchResults(String object,String data){
        APP_LOGS.info("Verifying the Search Results");
        try{
        	data=data.toLowerCase();
        	for(int i=3;i<=5;i++){
        		String text=driver.findElement(By.xpath(OR.getProperty("search_result_heading_start")+i+OR.getProperty("search_result_heading_end"))).getText().toLowerCase();
        		if(text.indexOf(data) == -1){
        			return Constants.KEYWORD_FAIL+" Got the text - "+text;
        		}
        	}
        	
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}
        
		return Constants.KEYWORD_PASS;
	
	
	}
	
	public String verifyRecentlyViewedItems(String object,String data){
        APP_LOGS.info("Verifying the recently viewed items");
        try{
        	if (waitUntilExists(object,"xpath")){
        		WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
        		List<WebElement> viewed_collection = element.findElements(By.xpath(OR.getProperty(object) + "/div"));
        		String aArray[] = data.split(";");
        		Boolean isExists = false;
        		 for (int i=0; i < aArray.length; i++){	
        			 isExists = false;
	        		for(WebElement rowElement : viewed_collection)
			        {
	        			String productName = rowElement.findElement(By.className("productName")).getText();	        	      
	        			if(productName.trim().equalsIgnoreCase(aArray[i].trim())) {
	        				isExists = true;
	        				break;
	        			}			           					           
			        }
	        		if(!isExists) {
	        			return Constants.KEYWORD_FAIL+" Item doesnot exist in Viewed items";
	        		}
        		 }        		     		
        	}
            else
        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
        }
        	
        catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}        		
        return Constants.KEYWORD_PASS;	
	}
	
	public String verifyCategoryItems(String object,String data)
		{					
			boolean isExists = false;
			String categories = "";
			try
			{
				if (waitUntilExists(object,"xpath")){
					
					WebElement select = driver.findElement(By.xpath(OR.getProperty(object)));
					List<WebElement> listItems = select.findElements(By.tagName("li"));
					 if(data.indexOf("_") > 0) {
						 categories = OR.getProperty(data).trim();
			    	   }
			    	   else
			    		   categories = data.trim();
					 
					String[] aArray =  categories.split(";");
					for (int i=0; i < aArray.length; i++){ 
						 isExists = false;
						 for (WebElement item : listItems) {								
							if (aArray[i].trim().equalsIgnoreCase(item.getText().trim())) {
								isExists = true;
								break;
								}
							}
							if(!isExists) {
			        			return Constants.KEYWORD_FAIL+" Item doesnot exist in category items";
			        		}					    	
					 }						
				}
				else
		        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
			}
			catch(Exception e)
			{
				return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
			}
			 return Constants.KEYWORD_PASS;	
		}
	
	
	
	public String verifyWriteReviewErrorDetails(String object,String data)
			{					
				boolean exists = false;
				try
				{
					if (waitUntilExists(object,"xpath")){						
						 WebElement table_element = driver.findElement(By.xpath(OR.getProperty(object)));
						 List<WebElement> div_collection=table_element.findElements(By.xpath(OR.getProperty(object) + "/div"));
						 String[] aArray =  data.split(",");
						 for (int i=0; i < aArray.length; i++)
						 {	
							 exists = false;
						    for(WebElement divElement : div_collection)
						    {
						       if(divElement.getText().contains(aArray[i].trim())){
						         	 exists = true;
						           	 break;
						         }						            	 						           					           
						    }
						    if(!exists){
						      	return Constants.KEYWORD_FAIL+" Write review error details does not exist";				            	
				             }
						 }						
					}
					else
			        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
				}
				catch(Exception e)
				{
					return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
				}
				return Constants.KEYWORD_PASS;	
			}
			
	public String ClickButtonUntilGrayedOut(String object,String data)
				{											
					try
					{
						if (waitUntilExists(object,"xpath")){
								
							boolean isExists = driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
							while (isExists) {								
							    driver.findElement(By.xpath(OR.getProperty(object))).click();
								isExists = driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
								Thread.sleep(1000);
							  }														
						   }	
						  else
				        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
						}
					catch(Exception e)
					{
						return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
					}
					return Constants.KEYWORD_PASS;	
				
	  }

	public String clickOnCategory(String object,String data){
        APP_LOGS.info("Click on category");
        String categories = "";
        try{
        	if (waitUntilExists(object,"xpath")){
        		
        		WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
        		List<WebElement> category_collection = element.findElements(By.xpath(OR.getProperty(object) + "/li"));
        		
        		 if(data.indexOf("_") > 0) {
					 categories = OR.getProperty(data).trim();
		    	   }
		    	   else
		    		   categories = data.trim();
        		 
        		Boolean isExists = false;        		
	        		for(WebElement catElement : category_collection)
			        {
	        			String categoryName = catElement.getText();	        	      
	        			if(categoryName.trim().contains(categories)) {
	        				catElement.findElement(By.tagName("a")).click();
	        				isExists = true;
	        				break;
	        			}			           					           
			        }
	        		if(!isExists) {
	        			return Constants.KEYWORD_FAIL+" Category doesnot exist in Category list";
	        		}        		        		     		
        	}
            else
        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
        }
        	
        catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}        		
        return Constants.KEYWORD_PASS;	
	}
	
	public String verifyCategory(String object,String data){
        APP_LOGS.info("Click on category");
        String categories = "";
        try{
        	if (waitUntilExists(object,"xpath")){
        		WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
        		List<WebElement> category_collection = element.findElements(By.xpath(OR.getProperty(object) + "/li"));
        		
        		 if(data.indexOf("_") > 0) {
					 categories = OR.getProperty(data).trim();
		    	   }
		    	   else
		    		   categories = data.trim();
        		
        		Boolean isExists = false;        		
	        		for(WebElement catElement : category_collection)
			        {
	        			String categoryName = catElement.getText();	        	      
	        			if(categoryName.trim().contains(categories)) {	        				
	        				isExists = true;
	        				break;
	        			}			           					           
			        }
	        		if(!isExists) {
	        			return Constants.KEYWORD_FAIL+" Category doesnot exist in Category list";
	        		}        		        		     		
        	}
            else
        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
        }
        	
        catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}        		
        return Constants.KEYWORD_PASS;	
	}
	
	public String clickOnSubCategory(String object,String data){
        APP_LOGS.info("Click on category");        
        try{
        	if (waitUntilExists(object,"xpath")){
        		WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
        		List<WebElement> category_collection = element.findElements(By.xpath(OR.getProperty(object) + "/li"));
        		String[] aArray =  data.split(";");
        		Boolean isExists = false;        		
	        		for(WebElement catElement : category_collection)
			        {
	        			String categoryName = catElement.getText();	        	      
	        			if(categoryName.trim().contains(aArray[0].trim())) {
	        				WebElement subcategory=catElement.findElement(By.tagName("ul"));
	        				List<WebElement> subcategory_collection =  subcategory.findElements(By.tagName("li"));
	        				for(WebElement selement : subcategory_collection)
	    			        {
	        					if(selement.getText().trim().contains(aArray[1].trim())){
	        						selement.findElement(By.tagName("a")).click();
	        						isExists = true;
	    	        				break;
	        					}
	    			        }
	        				isExists = true;
	        				break;
	        			}			           					           
			        }
	        		if(!isExists) {
	        			return Constants.KEYWORD_FAIL+" subCategory doesnot exist in Category list";
	        		}        		        		     		
        	}
            else
        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
        }
        	
        catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}        		
        return Constants.KEYWORD_PASS;	
	}
	
	public String verifySubCategory(String object,String data){
        APP_LOGS.info("Click on category");
        try{
        	if (waitUntilExists(object,"xpath")){
        		WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
        		List<WebElement> category_collection = element.findElements(By.xpath(OR.getProperty(object) + "/li"));
        		String[] aArray =  data.split(";");
        		Boolean isExists = false;        		
	        		for(WebElement catElement : category_collection)
			        {
	        			String categoryName = catElement.getText();	        	      
	        			if(categoryName.trim().contains(aArray[0].trim())) {
	        				WebElement subcategory=catElement.findElement(By.tagName("ul"));
	        				List<WebElement> subcategory_collection =  subcategory.findElements(By.tagName("li"));
	        				for(WebElement selement : subcategory_collection)
	    			        {
	        					if(selement.getText().trim().contains(aArray[1].trim())){	        						
	        						isExists = true;
	    	        				break;
	        					}
	    			        }
	        				isExists = true;
	        				break;
	        			}			           					           
			        }
	        		if(!isExists) {
	        			return Constants.KEYWORD_FAIL+" subCategory doesnot exist in Category list";
	        		}        		        		     		
        	}
            else
        	  return Constants.KEYWORD_FAIL+" -- Object does not exist";
        }
        	
        catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}        		
        return Constants.KEYWORD_PASS;	
	}
	
	public  String clickOnViewOrder(String object, String data){
		APP_LOGS.info("Verify View Order details");		
		try
		{	
			if (waitUntilExists(object,"xpath")){
				 WebElement table_element = driver.findElement(By.xpath(OR.getProperty(object)));
				 List<WebElement> tr_collection=table_element.findElements(By.xpath(OR.getProperty(object) + "/tbody/tr"));
				 String[] aArray =  data.split(";");		 					        
			        for(WebElement trElement : tr_collection)
			        {
			            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
			            if(td_collection.get(2).getText().equalsIgnoreCase(aArray[0])){
			            	
			            	if(aArray[1].equalsIgnoreCase("VIEW ORDER")){
			            		td_collection.get(3).findElement(By.tagName("input")).click();	
			            	}
			            	else
			            		td_collection.get(3).findElement(By.tagName("a")).click();
			            	
			            }
			           						           
			        }				  
			}else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to click on the Order button. - "+e.getMessage();
		}	
		 return Constants.KEYWORD_PASS;

	}
	
	public  String clickOnSelectStore(String object, String data){
		APP_LOGS.info("Click on selectstore product details");		
		try
		{	
			if (waitUntilExists(object,"xpath")){
				 WebElement table_element = driver.findElement(By.xpath(OR.getProperty(object)));
				 List<WebElement> tr_collection=table_element.findElements(By.xpath(OR.getProperty(object) + "/tbody/tr"));					 					        
			        for(WebElement trElement : tr_collection)
			        {
			            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
			            if(td_collection.size() > 0) {
				            if(td_collection.get(2).getText().trim().contains("Available")){
				            	
				            	td_collection.get(3).findElement(By.id("storeSelectButton")).click();	
				            	break;
				            }
			            }
			           						           
			        }				  
			}else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Unable to click on the Selectstore button. - "+e.getMessage();
		}	
		 return Constants.KEYWORD_PASS;
	}
	
	public String clickOnRelatedItems(String object,String data){
		{
			APP_LOGS.info("Click in Related items list in product detail page");
			boolean isExists = false;
			String product = "";
			try
			{
				if (waitUntilExists(object,"xpath")){

					WebElement select = driver.findElement(By.xpath(OR.getProperty(object)));
					List<WebElement> listItems = select.findElements(By.className("productInformation_wrap"));
					if(data.indexOf("_") > 0) {
						product = OR.getProperty(data).trim();
					}
					else
						product = data.trim();					
					
						isExists = false;
						for (WebElement item : listItems) {	
							WebElement aTag = item.findElement(By.tagName("p"));
							System.out.println("locating element");
							if (product.trim().equalsIgnoreCase(aTag.getText().trim())) {
								aTag.findElement(By.tagName("a")).click();
								isExists = true;
								break;
							}
						}
						if(!isExists) {
							return Constants.KEYWORD_FAIL+" Item doesnot exist in category items";
						}					    	
											
				}
				else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
			}
			return Constants.KEYWORD_PASS;	
		}

	}
	public String verifySortByInReviews(String object,String data){
		{					
			APP_LOGS.info("Verify SortBy Date order in Reviews tab");			
			try
			{
				if (waitUntilExists(object,"xpath")){
					WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
					List<WebElement> list_reviews = element.findElements(By.xpath(OR.getProperty(object)+"/div"));									

					ArrayList<Date> dateList = new ArrayList<Date>();
					
					for (WebElement item : list_reviews) {
						String review_date = item.findElement(By.className("BVRRReviewDateContainer")).getText();												
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
						dateList.add(sdf.parse(review_date));					
					}							
					
					for(int i = 0; i < dateList.size()-1; i++) {						 
				        if(dateList.get(i).compareTo(dateList.get(i+1))>0) {				        	
				        	return Constants.KEYWORD_FAIL+"NOT in Order";
				        }
				    }
				}
				else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
			}catch(Exception e){
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
			}
			return Constants.KEYWORD_PASS;	
		}

  }
	
	public String clickOnProductLink(String object,String data){
		{					
			APP_LOGS.info("Click on product link");			
			try
			{
				String productName = "";
				if (waitUntilExists(object,"xpath")){
					WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));					
				   	WebElement container= element.findElement(By.xpath("//div[contains(@class,'items js_items fl')]")); 
				   	List<WebElement> allProducts = container.findElements(By.xpath("//p[contains(@class,'productName')]"));
				    if(data.indexOf("_") > 0) {
				    	productName = OR.getProperty(data).trim();
			    	   }
			    	   else
			    		   productName = data.trim();
										
					int count = 0;
				   	for(WebElement product:allProducts) {
				   		count++;
						if (product.getText().contains(productName)) {							
							product.findElement(By.tagName("a")).click();
							break;
						}
						else {
							if(count == 4) {
								driver.findElement(By.xpath(OR.getProperty(object) + "/div/div/div[3]/a")).click();
								Thread.sleep(2000);
								count=0;
							}
								
						}
				   	}
				}
				else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
			}catch(Exception e){
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
			}
			return Constants.KEYWORD_PASS;	
		}

  }
	
	public String clickOnProductImage(String object,String data){
		{					
			APP_LOGS.info("Click on product image");			
			try
			{
				String productName = "";
				if (waitUntilExists(object,"xpath")){
					WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));					
				   	WebElement container= element.findElement(By.xpath("//div[contains(@class,'items js_items fl')]")); 
				   	List<WebElement> allProducts = container.findElements(By.xpath("//div[contains(@class,'productImage js_productLoading')]"));
				   	
				   	if(data.indexOf("_") > 0) {
				    	productName = OR.getProperty(data).trim();
			    	   }
			    	   else
			    		   productName = data.trim();
				   	
					int count = 0;
				   	for(WebElement product:allProducts) {
				   		count++;
				   		if (product.findElement(By.tagName("div")).getAttribute("ga-label").contains(productName)) {							
							product.findElement(By.tagName("a")).findElement(By.tagName("img")).click();
							break;
						}
						else {
							if(count == 4) {
								driver.findElement(By.xpath(OR.getProperty(object) + "/div/div/div[3]/a")).click();
								Thread.sleep(2000);
								count=0;
							}
								
						}
				   	}
				}
				else
		        	return Constants.KEYWORD_FAIL+" -- Object does not exist";
			}catch(Exception e){
				e.printStackTrace();
				return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
			}
			return Constants.KEYWORD_PASS;	
		}

  }
	
	
}
