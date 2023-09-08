package com.carval.steps;

import com.carval.model.Vehicle;
import com.carval.pages.CarValuationHomePage;
import com.carval.pages.CarDetailsPage;
import com.carval.utilities.CsvHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarInformationStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(CarInformationStepDefs.class);

    @Managed
    WebDriver driver;

    private CarDetailsPage carDetailsPage;
    private CarValuationHomePage carValuationHomePage;
    private List<String> registrationNumbers;
    private final Map<String, Vehicle> actualVehicleDetailsMap = new HashMap<>();
    private Map<String, Vehicle> expectedVehicleDetailsMap = new HashMap<>();
    private EnvironmentVariables environmentVariables;

    @Given("User reading input file {string} to extract vehicle registration numbers")
    public void user_reading_input_file_to_extract_vehicle_registration_numbers(String inputFile) {
        registrationNumbers = CsvHelper.getVehicleRegistrationNumbersFromInputFile(inputFile);
        logger.info("Registration Numbers from input file:"+registrationNumbers);
    }

    @When("User Navigate to CarValuation Page {string}")
    public void userNavigateToCarValuationPage(String url) {
        String carValuationHomePageUrl =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("webdriver.base.url");
        driver.get(carValuationHomePageUrl+url);
        driver.manage().window().maximize();
    }

    @And("perform free car valuation check to get the details")
    public void performFreeCarValuationCheckToGetTheDetails(){
        carValuationHomePage.clickFreeConsentAccept();
        for(String registrationNumber: registrationNumbers) {
            carValuationHomePage.enterRegistrationNumber(registrationNumber);
            carValuationHomePage.clickFreeCarCheckButton();
                Vehicle vehicle = Vehicle.builder()
                        .registration(carDetailsPage.getRegistrationNumber())
                        .make(carDetailsPage.getMake())
                        .model(carDetailsPage.getModel())
                        .build();
                actualVehicleDetailsMap.put(registrationNumber, vehicle);
            driver.navigate().back();
        };
    }


    @Then("compare the vehicle details returned from website with output file {string}")
    public void compareTheVehicleDetailsReturnedFromWebsiteWithOutputFile(String outputFile) {
        expectedVehicleDetailsMap = CsvHelper.getVehicleListFromOutputFile(outputFile);
        expectedVehicleDetailsMap.keySet().forEach(registrationNumber ->{
            Vehicle actualVehicle = actualVehicleDetailsMap.get(registrationNumber);
            if(actualVehicle == null){
                logger.info("Vehicle Not found with Registration Number: "+registrationNumber);
            }else {
                Vehicle expectedVehicle = expectedVehicleDetailsMap.get(registrationNumber);
                assertThat("Vehicle Registration Number is coming wrong", getRegistrationNumber(actualVehicle.getRegistration()).trim().replaceAll("\\s+",""), equalTo(expectedVehicle.getRegistration()));
                assertThat("Vehicle Make is coming wrong", getMake(actualVehicle.getMake()).trim(), equalTo(expectedVehicle.getMake()));
                assertThat("Vehicle Model is coming wrong", expectedVehicle.getModel(), containsString(getModel(actualVehicle)));
            }
        });
    }


    private String getRegistrationNumber(String regNumber){
        return regNumber.substring(regNumber.indexOf('|')+1);
    }

    private String getMake(String make){
        return make.substring(0, make.indexOf(' ')+1);
    }

    private String getModel(Vehicle vehicleDetails){
        String make = vehicleDetails.getMake();
        String registration = vehicleDetails.getRegistration();
        String model = vehicleDetails.getModel();
        String series = make.substring(make.indexOf(' ')+1);
        String engine = registration.substring(0, registration.indexOf('|'));
        String[] array = model.split("\\|");
        List<String> engineList = Arrays.asList(array);
        String engineDetails = engineList.get(1).trim()+" "+engineList.get(2).trim()+" "+engineList.get(3).trim();

        return series+" "+engine+engineDetails;
    }


}