package mobile_app.trainingpal.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIResponseCallback;
import mobile_app.trainingpal.me.models.User;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.network.UserService;
import mobile_app.trainingpal.me.shared.UserForm;

public class SignUpActivity extends Activity {

    private UserForm userForm;

    private boolean confirmExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        String requiredIndicator = getString(R.string.user_form_required_indicator);

        userForm = new UserForm(this);
        userForm.setUsernameLabel(userForm.getUsernameLabel() + " " + requiredIndicator);
        userForm.setPasswordLabel(userForm.getPasswordLabel() + " " + requiredIndicator);
        userForm.setConfirmPasswordLabel(userForm.getConfirmPasswordLabel() + " " + requiredIndicator);
        userForm.setFirstnameLabel(userForm.getFirstnameLabel() + " " + requiredIndicator);
        userForm.setLastnameLabel(userForm.getLastnameLabel() + " " + requiredIndicator);
        userForm.setEmailLabel(userForm.getEmailLabel() + " " + requiredIndicator);
        userForm.setDateOfBirthLabel(userForm.getDateOfBirthLabel() + " " + requiredIndicator);
        userForm.setLocationLabel(userForm.getLocationLabel() + " " + requiredIndicator);
        userForm.setTypeLabel(userForm.getTypeLabel() + " " + requiredIndicator);
    }

    @Override
    public void onBackPressed() {
        if (!confirmExit) {
            AlertService.LongAlert(this, getString(R.string.app_confirm_exit_message));
            confirmExit = true;
        } else {
            super.onBackPressed();
        }
    }

    public void onSignUpPageSignUpClicked(View view) {
        userForm.clearErrors();

        if (userForm.checkAllFields()) {
            createUser();
        } else {
            AlertService.LongAlert(this, getResources().getString(R.string.app_form_alert_error));
        }
    }

    public void onSignUpPageClearClicked(View view) {
        userForm.clearErrors();
        userForm.clearFields();
    }

    public void onSignUpPageGoToLoginClicked(View view) { goToLoginPage(); }

    private void goToLoginPage() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void createUser() {

        User user = new User();

        user.setId(userForm.getUsernameField());
        user.setPassword(userForm.getPasswordField());
        user.setF_Name(userForm.getFirstnameField());
        user.setL_Name(userForm.getLastnameField());
        user.setE_Mail(userForm.getEmailField());
        user.setDOB(userForm.getDateOfBirthField());
        user.setType(userForm.getTypeField());
        user.setLocation(userForm.getLocationField());
        user.setBio((userForm.getBioField().equals("") ? null : userForm.getBioField()));

        Request request = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header("", ""),
                user
        );

        final Activity currentActivity = this;

        UserService.createUser(request, new IAPIResponseCallback () {
            @Override
            public void OnSuccess(String successMessage) {
                AlertService.ShortAlert(currentActivity, successMessage);
                goToLoginPage();
            }

            @Override
            public void OnError(String errorMessage) {
                AlertService.LongAlert(currentActivity, errorMessage);
            }
        });
    }
}
