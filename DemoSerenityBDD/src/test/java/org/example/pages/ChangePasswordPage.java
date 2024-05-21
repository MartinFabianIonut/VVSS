package org.example.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

@DefaultUrl("http://localhost:8180/change/password")
public class ChangePasswordPage extends PageObject {

    @FindBy(name = "email")
    WebElement emailInput;

    @FindBy(name = "oldPassword")
    WebElement oldPasswordInput;

    @FindBy(name = "newPassword")
    WebElement newPasswordInput;

    @FindBy(name = "newPasswordConfirmed")
    WebElement confirmNewPasswordInput;

    @FindBy(id = "submitButton")
    WebElement changeButton;

    public void enter_email(String email) {
        emailInput.sendKeys(email);
    }

    public void enter_old_password(String oldPassword) {
        oldPasswordInput.sendKeys(oldPassword);
    }

    public void enter_new_password(String newPassword) {
        newPasswordInput.sendKeys(newPassword);
    }

    public void enter_confirm_new_password(String confirmNewPassword) {
        confirmNewPasswordInput.sendKeys(confirmNewPassword);
    }

    public void click_change_button() {
        changeButton.click();
    }

    public void change_password(String email, String oldPassword, String newPassword) {
        enter_email(email);
        enter_old_password(oldPassword);
        enter_new_password(newPassword);
        enter_confirm_new_password(newPassword);
        click_change_button();
    }
}
