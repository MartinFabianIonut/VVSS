package org.example.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@DefaultUrl("http://localhost:8180/help")
public class HelpPage extends PageObject {

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "subject")
    WebElement subjectInput;

    @FindBy(id = "description")
    WebElement descriptionInput;

    @FindBy(id = "submitButton")
    WebElement submitButton;

    @FindBy(css = "h2.custom-message.white-text")
    WebElement confirmationMessage;

    public void enter_email(String email) {
        emailInput.sendKeys(email);
    }

    public void enter_subject(String subject) {
        subjectInput.sendKeys(subject);
    }

    public void enter_description(String description) {
        descriptionInput.sendKeys(description);
    }

    public void click_submit_button() {
        submitButton.click();
    }

    public boolean is_confirmation_message_displayed() {
        return confirmationMessage.getText().contains("Thank you for your message.");
    }

    public void waitForPageToLoad() {
        waitFor(ExpectedConditions.visibilityOf(confirmationMessage));
    }
}
