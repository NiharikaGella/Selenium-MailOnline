package pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.AfterStep;
import utils.Driver;

public class Page {

    public WebDriver driver = Driver.getDriver();


    /**
     * Launching the given browser
     * @param url
     */
    
    
    public void getUrl(final String url) {
    	
        driver.navigate().to(url);
    }
    
  
    /**
     * Click the given button and verifying if it is navigated to correct page
     * @param xpath
     * @param VerificationXpath
     */
public void clickAButton(final String xpath,final String VerificationXpath) {
		
		//Click on the given button
		driver.findElement(By.xpath(xpath)).click();
		
		//wait till page loads - using explicit wait
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(VerificationXpath)));
		
		
		//Verify if a particular button is present		
		boolean flag = driver.findElement(By.xpath(VerificationXpath)).isDisplayed();
		if(flag)
		{
			System.out.println("Navigated to correct page");
		}
		else
		{
			System.out.println("Navigation failed");
		}
		
		
	}

/**
 * Gives colour of an element
 * @param Xpath
 * @return
 */
public String getColour(String Xpath) {
	
	WebElement t = driver.findElement(By.xpath(Xpath));
	String s = t.getCssValue("color");
	String c = Color.fromString(s).asHex();
	System.out.println(s);
	return c;
	
}

/**
 * Gives the required attribute of an element
 * @param Xpath
 * @param Attribute
 * @return
 */
public String getAttribute(String Xpath,String Attribute) {
	
	WebElement t = driver.findElement(By.xpath(Xpath));
	String s = t.getCssValue(Attribute);
	return s;
	
}

/**
 * Gives current date and time in the required format
 * @param dateFormat
 * @return
 * 
 */
public String getCurrentDate(String dateFormat) {	
	Date date = new Date();
    DateFormat df = new SimpleDateFormat(dateFormat);
 String a = df.format(date);	
	String b = (a.substring(0,a.length()-5)+ordinal()+a.substring(a.length()-5,a.length()));	
	 return b;	
	 
	
}

/**
 * Gives for a date
 * @return
 */
public String ordinal() {
	Date date = new Date();
    DateFormat df = new SimpleDateFormat("dd");
    String a = df.format(date);
    int b = Integer.parseInt(a);
if (b > 3 && b < 21) return "th";
switch (b % 10) {
    case 1:  return "st";
    case 2:  return "nd";
    case 3:  return "rd";
    default: return "th";
}
}



}
