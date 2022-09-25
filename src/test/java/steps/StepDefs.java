package steps;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.cucumber.java.AfterStep;


import pages.Page;
import utils.Driver;




public class StepDefs extends Page implements En {
	ExtentReports extent = new ExtentReports();
	ExtentReports extent2 = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter("Reports\\MailOnlineReport.html");
	ExtentSparkReporter spark2 = new ExtentSparkReporter("Reports\\Api.html");
	ExtentTest e = extent.createTest("MailOnline test");
	ExtentTest ex = extent2.createTest("Api test");


	public StepDefs() {

		String sport = "(//a[@href='/sport/index.html'])[1]";
		String football = "//a[@href='/sport/football/index.html']";
		String secondaryNavigation = "//div[@class='sport nav-secondary-container']/child::div";
		Actions action = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		Given("I go to url (.*)$", (final String url) ->       
		getUrl(url)
		
				);

		And("Verify the webpage displays current date and time", () -> {
            
			WebElement actualDate = driver.findElement(By.xpath("(//div[@class='weather']//child::strong)[1]"));
			String actualDateonPage = actualDate.getText();
			String currentDate = getCurrentDate("EEEE, MMM dd yyyy");
			if(actualDateonPage == currentDate )
			{			
				e.log(Status.PASS, "Dates are equal");
			}
			else {
				e.log(Status.FAIL, "Dates are not equal");
			}
			Driver.waitTillElementVisible("//button[text()='Got it']");
			driver.findElement(By.xpath("//button[text()='Got it']")).click();
			extent.attachReporter(spark);
			extent.flush();

		});
		And("Navigate to the Sport menu", () -> {
			clickAButton(sport,football);
			

		});
		And("Ensure primary navigation colour and secondary navigation are same", () -> {
			String sportColour = getColour(sport);
			String footballColour = getColour(secondaryNavigation);
			System.out.println("sports=" + sportColour);
			System.out.println("football=" + footballColour);
			if(sportColour.equals(footballColour))
			{
				e.log(Status.PASS, "Colours of primary and secondary navigation are same ");

			}
			else {
				e.log(Status.FAIL, "Colours of primary and secondary navigation are different ");

			}
			extent.attachReporter(spark);
			extent.flush();
		});
		And("Click on the Football sub navigation item", () -> {
			clickAButton(football,"(//a[@class='js-login'])[1]");
			extent.attachReporter(spark);
			extent.flush();
		});
		And("User is on Football page", () -> {
			driver.findElement(By.xpath("(//a[@itemprop='url'])[1]"));
			extent.attachReporter(spark);
			extent.flush();
		});

		And("User clicks facebook icon and open it in new model window", () -> {
			//facebook share

			String parentwindowhandle = driver.getWindowHandle();
			action.moveToElement(driver.findElement(By.xpath("(//li[@class='share-facebook-long'])[1]")));
			Driver.waitTillElementClickable("(//li[@class='share-facebook-long'])[1]");
			driver.findElement(By.xpath("(//li[@class='share-facebook-long'])[1]")).click();
			Thread.sleep(3000);
			Set<String> newWindowHandles = driver.getWindowHandles();
			for (String handle : newWindowHandles) {
				if(!(handle.equals(parentwindowhandle)))
				{
					driver.switchTo().window(handle);
					Driver.waitTillElementVisible("//h2[text()='Facebook']");
					if(driver.findElement(By.xpath("//h2[text()='Facebook']")).isDisplayed())
					{  

						e.log(Status.PASS, "Opened facebook in new window succesfully");

					}
					driver.close();
					driver.switchTo().window(parentwindowhandle);
				}}
			extent.attachReporter(spark);
			extent.flush();
		});

		And("Click the first article displayed and traverse to the gallery image", () -> {
			WebElement link1  = driver.findElement(By.xpath("(//a[@itemprop='url'])[1]"));
			String text1 =  link1.getAttribute("text");	
			System.out.println(text1);
			driver.findElement(By.xpath("(//a[@itemprop='url'])[1]")).click();
			Driver.waitTillElementVisible("//div[@id='js-article-text']/h2");
			boolean check = driver.findElement(By.xpath("//div[@id='js-article-text']/h2")).isDisplayed();	
			System.out.println(check);
			if(check)	
			{
				e.log(Status.PASS, "Navigated to the article succesfully");
			}
			else {
				e.log(Status.FAIL, "Not navigated to the article succesfully");				
			}
			extent.attachReporter(spark);
			extent.flush();
			
		});
		And("Open image in full screen", () -> {
			//In the gallery, click the gallery icon appearing on the right-hand side 
			action.moveToElement(driver.findElement(By.xpath("(//p[@class='imageCaption'])[1]"))).perform();
			Thread.sleep(3000);
			driver.findElement(By.xpath("(//div[@class='image-wrap']//child::img)[1]")).click();
			//gallery loads on full screen
			WebElement i = driver.findElement(By.xpath("(//div[contains(@class,'slide')]//child::img)[1]"));
			Boolean p = (Boolean) ((JavascriptExecutor)driver)
					.executeScript("return arguments[0].complete "
							+ "&& typeof arguments[0].naturalWidth != \"undefined\" "
							+ "&& arguments[0].naturalWidth > 0", i);
		if (p) {
				e.log(Status.PASS, "Image loaded in full screen");

			} else {
				e.log(Status.PASS, "Image not loaded in full screen");
			}
		extent.attachReporter(spark);
		extent.flush();
		});
		And("Click on next and verify button and verify", () -> {
			//Click on Next button and verify if its navigating to Next image 
			driver.findElement(By.xpath("//button[@aria-label='Next']")).click();
			Driver.waitTillElementVisible("(//div[@class='scrollingElement-kUNHH'] //child ::div)[2]");
			boolean checkNextImage = driver.findElement(By.xpath("(//div[@class='scrollingElement-kUNHH'] //child ::div)[2]")).isDisplayed();
			if (checkNextImage) {
				e.log(Status.PASS, "Navigated to second image");
			} else {
				e.log(Status.FAIL, "Not Navigated to second image");
			}

			//Click on previous button and verify if its navigating to previous image
			driver.findElement(By.xpath("//button[@aria-label='Previous']")).click();
			Driver.waitTillElementVisible("(//div[@class='scrollingElement-kUNHH'] //child ::div)[1]");
			boolean checkPrevImage = driver.findElement(By.xpath("(//div[@class='scrollingElement-kUNHH'] //child ::div)[1]")).isDisplayed();
			if (checkPrevImage) {
				e.log(Status.PASS, "Navigated back to first image");
			} else {
				System.out.println("Not navigated  to first image");

			}
			driver.findElement(By.xpath("//button[contains(@class,'closeButton')]")).click();
			Thread.sleep(10000);
			extent.attachReporter(spark);
			extent.flush();
		});

		And("Verify Premier league section", () -> {
			action.moveToElement(driver.findElement(By.xpath("//td[text()='Liverpool' and contains(@class,'team-short')]"))).perform();
			WebElement wScore = driver.findElement(By.xpath(" //td[text()='Liverpool']//following-sibling::td[contains(@class,'score')]"));
			String score =wScore.getText();  
			WebElement wPosition = driver.findElement(By.xpath("( //td[text()='Liverpool']//preceding-sibling::td)[1]"));
			String position =wPosition.getText();		
			e.info("Score of Liver pool is " + score);
			e.info("Position of Liver pool is " + position);
			extent.attachReporter(spark);
			extent.flush();
		});
		And("Open a video embedded within the article and verify it", () -> {
			
			try {
				action.moveToElement(driver.findElement(By.xpath("(//div[@class='vjs-play-control vjs-control '])[1]"))).perform();		
				Driver.waitTillElementClickable("(//div[@class='vjs-play-control vjs-control '])[1]");
				driver.findElement(By.xpath("(//div[@class='vjs-play-control vjs-control '])[1]")).click();	
				driver.findElement(By.xpath("(//div[@class='vjs-play-control vjs-control  vjs-playing'])[1]")).click();	
				e.log(Status.PASS, "Video played successfully");
				//extent.attachReporter(spark);
				//extent.flush();
				
			} catch (Exception e) {
				System.out.println("There is no video in this article");
			}
			
			
		});

		Given("I hit the put API and create a pet", () -> {
			//Positive scenario
			String data = "{\r\n"
					+ "  \"id\": 4,\r\n"
					+ "  \"category\": {\r\n"
					+ "    \"id\": 4,\r\n"
					+ "    \"name\": \"string\"\r\n"
					+ "  },\r\n"
					+ "  \"name\": \"Sandy\",\r\n"
					+ "  \"photoUrls\": [\r\n"
					+ "    \"string\"\r\n"
					+ "  ],\r\n"
					+ "  \"tags\": [\r\n"
					+ "    {\r\n"
					+ "      \"id\": 0,\r\n"
					+ "      \"name\": \"string\"\r\n"
					+ "    }\r\n"
					+ "  ],\r\n"
					+ "  \"status\": \"available\"\r\n"
					+ "}";

			Response r = RestAssured.
					given().
					contentType( "application/json")
					.body(data)
					.when()
					.post("https://petstore.swagger.io/v2/pet")
					.then().statusCode(200).extract().response();

			JsonPath j = new JsonPath(r.asString());
			String h = j.get("name");
			System.out.println(h);

			if(h.equals("Sandy"))
			{
				ex.log(Status.PASS, "Put call is succesfull and created a new pet");
			}
			else
			{
				ex.log(Status.FAIL, "Put call is not succesfull");
			} 


			//Negative scenario
			try {
				Response res = RestAssured.
						given().
						contentType("application/json")
						.body(data)
						.when()
						.post()
						.then().extract().response();
			} catch (Exception e2) {
				ex.log(Status.PASS, "Negative scenario of put tested");
			}

			extent2.attachReporter(spark2);
			extent2.flush();
		});
		Then("Verify that a pet is created using get API", () -> {

			//Positive scenario
			Response r2 = RestAssured.given().pathParam("id", 4).
					get("https://petstore.swagger.io/v2/pet/{id}").
					then().statusCode(200).
					extract().response();

			JsonPath j = new JsonPath(r2.asString());
			String h = j.get("name");
			if(h.equals("Sandy"))
			{
				ex.log(Status.PASS, "get call is successfull");
			}
			else
			{
				ex.log(Status.FAIL, "Get call is not successfull");
			}
			//negative scenario - giving an invalid id
			try {
				Response r3 = RestAssured.given().pathParam("id", 10).
						get().
						then().statusCode(200).
						extract().response();

			} catch (Exception e2) {
				ex.log(Status.PASS, "Negative test for get is succesfull");
			}
			extent2.attachReporter(spark2);
			extent2.flush();
		});






	}


//	@AfterStep
//public void report() {
//		extent.attachReporter(spark);
//		extent.flush();
//		extent2.attachReporter(spark2);
//	extent2.flush();

	//}

}
