# car-valuation-test-automation

     Pre requisites to run the tests
          1. Make sure Maven and Java are installed globally. you can test with the below command
             mvn --version  
             java --version
          2. By default tests will run in chrome, But if you want to run tests in firefox 
             then make sure you have the latest chrome and firefox installed in your machine
             
      Running Tests and check the report 
      
          1) Once you have cloned the repository, Please be on the main folder
          2) a) Run the below command to run the tests in chrome browser
                   mvn clean verify  
              b) Run the below command to run the tests in sit environment
                    mvn clean verify -Denvironment=sit
              c) If you want to run in firfox browser then use the below command
                   mvn clean verify --Dwebdriver.driver=firefox
          3) you can check the report in below folder
              target/site/index.html    
             
             In Reports you can see the below scenarios.And each step is producing a screenshot and attached next to the step.   
              1) Verify Vehicle Information against output file
Note: Some part of the vehicle model information is not available exactly with the output file in the website. I have tried to retrieve all the possible information which are available in the website and compared.