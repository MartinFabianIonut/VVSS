package org.example.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

@DefaultUrl("http://localhost:8180/?continue")
public class AuthenticatedPage extends PageObject {

    @FindBy(css = "h3.white-text span")
    WebElement welcomeMessage;

    @FindBy(css = "input[type='submit'].btn.btn-sm.btn-light")
    WebElement disconnectButton;

    public void is_logged_in_successfully() {
        shouldContainText("Welcome");
    }

    public void click_disconnect_button() {
        disconnectButton.click();
    }
}