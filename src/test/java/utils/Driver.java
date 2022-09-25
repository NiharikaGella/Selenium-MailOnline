package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver {

    private static WebDriver driver;

    /**
     * Declaring chrome driver
     * @return
     */
    public static WebDriver getDriver() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

    	options.addArguments("start-maximized");

    	options.addArguments("disable-infobars");

    	options.addArguments("--disable-extensions");

    	 driver = new ChromeDriver(options);
       // driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        Runtime.getRuntime().addShutdownHook(new Thread("Driver shutdown thread") {
            public void run() {
                driver.quit();
            }
        });

        return driver;
    }
    
    /**
     * Waiting until the loading of the webpage disappears 
     * @throws Exception
     */
    public static void waitUntilLoadingDisappears() throws Exception{
		try {
			int temp=0;
			int tempCount=0;
			List<WebElement> loadElementList=driver.findElements(By.xpath("//div[text()='Loading...' and contains(@class,'x-mask-msg-text')]"));
			while(loadElementList.size()==0 && tempCount<5) {
				Thread.sleep(1000);
				loadElementList.clear();
				loadElementList=driver.findElements(By.xpath("//div[text()='Loading...' and contains(@class,'x-mask-msg-text')]"));
				temp= (loadElementList.size()!=0)? 1:0;
				if(temp==1) {
					break;
				}
				else {
					tempCount=tempCount+1;
				}
			}

			for(WebElement ele:loadElementList){
				int tempFlag=0;
				if(ele.isDisplayed()) {
					while(tempFlag<1) {
						if(!ele.isDisplayed()) {
							tempFlag=tempFlag+1;
						}
					}
				}

			}
		}
		catch(Exception e) {
			;
		}

	}
    /**
     * Waiting until the required element is visible
     * @param eleXpath
     * @return
     * @throws Exception
     */
    public static WebElement waitTillElementVisible(String eleXpath) throws Exception {
		try {
			waitUntilLoadingDisappears();
			//Wait for 60 seconds and return the element if visible.
			return new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(eleXpath)));	
		}
		catch(Exception e){
		System.out.println("Element still not visible after waiting for 60seconds");
			return null;
		}
	}
    
    /**
     * Waiting until the required element is clickable
     * @param eleXpath
     * @return
     * @throws Exception
     */
    public static WebElement waitTillElementClickable(String eleXpath) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver,60);
			
			waitUntilLoadingDisappears();
			//Wait for 60 seconds and return the element if visible.
			return new WebDriverWait(driver,60).until(ExpectedConditions.elementToBeClickable(By.xpath(eleXpath)));	
		}
		catch(Exception e){
		System.out.println("Element still not clickable after waiting for 60seconds");
			return null;
		}
	}
    
}
