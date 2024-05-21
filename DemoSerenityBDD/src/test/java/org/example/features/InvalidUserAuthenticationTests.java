package org.example.features;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.example.steps.serenity.EndUserSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("test-data/invalid-login-data.csv")
public class InvalidUserAuthenticationTests {

    private String email;

    private String password;

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps user;

    @Test
    public void user_should_receive_error_message_for_invalid_login() {
        user.is_on_login_page();
        user.logs_in_with(email, password);
        user.should_see_login_error_message();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Test
//    public void user_should_be_able_to_change_password() {
//        user.is_on_login_page();
//        user.navigate_to_change_password_page();
//        user.change_password("valid@example.com", "aaaaaaaaaaaaaaaa", "bbbb");
//        user.should_see_change_password_confirmation();
//    }
//
//    @Test
//    public void user_should_be_able_to_login_with_new_password() {
//        user.is_on_login_page();
//        user.logs_in_with("valid@example.com", "newpassword");
//        user.should_see_welcome_message();
//        user.logs_out();
//    }
}
