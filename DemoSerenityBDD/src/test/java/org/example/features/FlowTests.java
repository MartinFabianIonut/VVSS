package org.example.features;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.example.steps.serenity.EndUserSteps;
import org.example.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
public class FlowTests {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps user;

    @Test
    public void user_should_get_through_flow() {
        user.is_on_login_page();

        user_should_navigate_to_help_page_and_send_email();

        user_should_change_password_page_and_change_password();

        user_should_login_with_new_password();

        user_should_disconnect();
    }

    private void user_should_navigate_to_help_page_and_send_email() {
        user.navigates_to_help_page();
        user.submits_help_form(Constants.EMAIL, Constants.SUBJECT, Constants.DESCRIPTION);
        user.should_see_confirmation_message();
    }

    private void user_should_change_password_page_and_change_password() {
        user.navigates_to_change_password_page();
        user.changes_old_password(Constants.EMAIL, Constants.OLD_PASSWORD,
            Constants.NEW_PASSWORD);
    }

    private void user_should_login_with_new_password() {
        user.is_on_login_page();
        user.logs_in_with(Constants.EMAIL, Constants.NEW_PASSWORD);
        user.is_logged_in_successfully();
    }

    private void user_should_disconnect() {
        user.clicks_disconnect();
        user.is_logged_out_successfully();
    }

}
