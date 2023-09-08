package com.carval.pages;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

public class CarValuationHomePage extends PageObject {

    @FindBy(id="vrm")
    WebElementFacade registrationNumber;

    @FindBy(xpath="//button/span[contains(text(),'Get started')]")
    WebElementFacade freeCarCheckButton;

    @FindBy(xpath="//input[@name='mileage']")
    WebElementFacade mileage;

    @FindBy(xpath="//button/span[contains(text(),'Get my estimate')]")
    WebElementFacade getMyEstimate;

    @FindBy(xpath="//div[contains(@class, 'cazoo-consent-manager')]//span[contains(text(),'Accept All')]")
    WebElementFacade consentAccept;

    public void enterRegistrationNumber(String number){
        registrationNumber.waitUntilVisible();
        registrationNumber.clear();
        registrationNumber.sendKeys(number);
    }

    public void clickFreeCarCheckButton(){
        freeCarCheckButton.waitUntilClickable();
        freeCarCheckButton.click();
    }

    public void clickFreeConsentAccept(){
        consentAccept.waitUntilClickable();
        consentAccept.click();
    }

    public void clickGetMyEstimateButton(){
        getMyEstimate.waitUntilClickable();
        getMyEstimate.click();
    }

    public void enterMileage(String mileageValue){
        mileage.waitUntilVisible();
        mileage.sendKeys(mileageValue);
    }

}
