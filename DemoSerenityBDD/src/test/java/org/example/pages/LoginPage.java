package org.example.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@DefaultUrl("http://localhost:8180/loginpage")
public class LoginPage extends PageObject {

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(css = "button[type='submit'].btn.btn-light")
    WebElement loginButton;

    @FindBy(css = ".text-danger")
    WebElement errorMessage;

    @FindBy(css = ".text-warning")
    WebElement warningMessage;

    public void waitForPageToLoad() {
        waitFor(ExpectedConditions.visibilityOf(emailInput));
        waitFor(ExpectedConditions.visibilityOf(passwordInput));
    }

    public void enter_email(String email) {
        emailInput.sendKeys(email);
    }

    public void enter_password(String password) {
        passwordInput.sendKeys(password);
    }

    public void click_login_button() {
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean is_on_login_page() {
        return emailInput.isDisplayed() && passwordInput.isDisplayed() && loginButton.isDisplayed();
    }

    public String getWarningMessage() {
        return warningMessage.getText();
    }
}
