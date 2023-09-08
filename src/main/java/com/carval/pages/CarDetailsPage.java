package com.carval.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CarDetailsPage extends PageObject {
    @FindBy(xpath="//h1[contains(@class,'top-headingstyles__MakeAndModel')]//following-sibling::h2")
    WebElementFacade registrationNumber;

    @FindBy(xpath="//h1[contains(@class,'top-headingstyles__MakeAndModel')]")
    WebElementFacade make;

    @FindBy(xpath="//h1[contains(@class,'top-headingstyles__MakeAndModel')]//following-sibling::p/strong")
    WebElementFacade model;

    @FindBy(xpath = "//button/span[contains(text(),'Find my car')]")
    WebElementFacade findMyCar;

    @FindBy(xpath="//div[contains(@class,'quick-estimatestyles__TopBanner')]")
    WebElementFacade topBanner;


    public String getRegistrationNumber(){
        return getFieldValue(registrationNumber);
    }

    public String getMake(){
        return getFieldValue(make);
    }

    public String getModel(){
        return getFieldValue(model);
    }

    public boolean isMyCarExists(){
        WebDriverWait wait = new WebDriverWait(getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(@class,'quick-estimatestyles__TopBanner')]")));
        return findMyCar.isPresent();
    }

    private String getFieldValue(WebElementFacade element){
        if(!isMyCarExists()) {
            element.waitUntilVisible();
            return element.getText();
        }else{
            return null;
        }
    }
}
