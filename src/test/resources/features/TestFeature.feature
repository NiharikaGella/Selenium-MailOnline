Feature: Visit a page and click on a link




  @test
  Scenario: Visit a page and check the page title 
  Given I go to url <url>
  And Verify the webpage displays current date and time
  And Navigate to the Sport menu
  And Ensure primary navigation colour and secondary navigation are same
  And Click on the Football sub navigation item  
    And User is on Football page
    And Click the first article displayed and traverse to the gallery image
     And User clicks facebook icon and open it in new model window
    And Open image in full screen
    And Click on next and verify button and verify
    And Verify Premier league section
     And Open a video embedded within the article and verify it
   
   Examples:
   |url| 
   |https://www.dailymail.co.uk/home/index.html|
    
    
 @test
  Scenario: Verify get and put using Api
  Given I hit the put API and create a pet
  Then Verify that a pet is created using get API
	
	
	
	
	


    
    
    



