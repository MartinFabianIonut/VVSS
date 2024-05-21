package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.example.pages.AuthenticatedPage;
import org.example.pages.HelpPage;
import org.example.pages.LoginPage;
import org.example.pages.ChangePasswordPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class EndUserSteps extends ScenarioSteps {

    LoginPage loginPage;

    ChangePasswordPage changePasswordPage;

    HelpPage helpPage;

    AuthenticatedPage authenticatedPage;

    @Step
    public void is_on_login_page() {
        loginPage.open();
        loginPage.waitForPageToLoad();
    }

    @Step
    public void logs_in_with(String email, String password) {
        loginPage.enter_email(email);
        loginPage.enter_password(password);
        loginPage.click_login_button();
    }

    @Step
    public void should_see_login_error_message() {
        assertThat(loginPage.getErrorMessage(), containsString("Bad credentials"));
    }

    @Step
    public void is_logged_in_successfully() {
        authenticatedPage.is_logged_in_successfully();
    }

    @Step
    public void clicks_disconnect() {
        authenticatedPage.click_disconnect_button();
    }

    @Step
    public void navigates_to_help_page() {
        helpPage.open();
    }

    @Step
    public void submits_help_form(String email, String subject, String description) {
        helpPage.enter_email(email);
        helpPage.enter_subject(subject);
        helpPage.enter_description(description);
        helpPage.click_submit_button();
    }

    @Step
    public void should_see_confirmation_message() {
        helpPage.waitForPageToLoad();
        assertThat(helpPage.is_confirmation_message_displayed(), is(true));
    }

    public void navigates_to_change_password_page() {
        changePasswordPage.open();
    }

    public void changes_old_password(String mail, String oldPassword, String newPassword) {
        changePasswordPage.change_password(mail, oldPassword, newPassword);
        assertThat("User should be on the login page", loginPage.is_on_login_page(), is(true));

    }

    public void is_logged_out_successfully() {
        assertThat("User should be on login page", loginPage.is_on_login_page(), is(true));
        loginPage.waitForPageToLoad();
        assertThat(loginPage.getWarningMessage(), containsString("You have been logged out."));
    }
}
