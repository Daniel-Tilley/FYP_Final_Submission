package mobile_app.trainingpal.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIAuthCallback;
import mobile_app.trainingpal.me.interfaces.models.IAuthObject;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.AuthService;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class LoginActivity extends Activity {

    private EditText usernameField;
    private EditText passwordField;

    private TextView usernameError;
    private TextView passwordError;

    private boolean confirmExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (LocalAuthService.isUserAuthenticated(this)) {
            startActivity(new Intent(getApplicationContext(), ContainerActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseVariables();
    }

    @Override
    public void onBackPressed() {
        if (LocalAuthService.isSessionExpired()) {
            AlertService.ShortAlert(this, getString(R.string.app_session_has_expired_error));
        } else {
            if (!confirmExit) {
                AlertService.LongAlert(this, getString(R.string.app_confirm_exit_message));
                confirmExit = true;
            } else {
                super.onBackPressed();
            }
        }
    }

    private void initialiseVariables() {
        usernameField = findViewById(R.id.login_page_username_field);
        passwordField = findViewById(R.id.login_page_password_field);

        usernameError = findViewById(R.id.login_page_username_error);
        passwordError = findViewById(R.id.login_page_password_error);
    }

    public void onLoginPageLoginClicked(View view) {
        clearErrors();

        if (checkFields()) {
            loginUser(usernameField.getText().toString(), passwordField.getText().toString());
        } else {
            AlertService.LongAlert(this, getResources().getString(R.string.app_form_alert_error));
        }
    }

    public void onLoginPageClearClicked(View view) {
        clearFields();
        clearErrors();
    }

    public void onLoginPageGoToSignUpClicked(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }

    private boolean checkFields() {
        boolean noErrors = true;

        if (usernameField.getText().toString().equals("")) {
            usernameError.setText(R.string.app_form_required_error);
            noErrors = false;
        }

        if (passwordField.getText().toString().equals("")) {
            passwordError.setText(R.string.app_form_required_error);
            noErrors = false;
        }

        return noErrors;
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void clearErrors() {
        usernameError.setText("");
        passwordError.setText("");
    }

    private Header getBasicAuthHeader(String username, String password) {
        String userPass = username + ":" + password;
        byte[] userPassEncoded = Base64.encode(userPass.getBytes(), Base64.NO_WRAP);
        String headerValue = NetworkConstants.BASIC_AUTH_TYPE + new String(userPassEncoded);

        return new Header(NetworkConstants.AUTHORIZATION_HEADER, headerValue);
    }

    private void loginUser (String username, String password) {
        Request request = new Request(
                getResources().getString(R.string.app_api_base_url),
                getBasicAuthHeader(username, password)
        );

        final Activity currentActivity = this;

        AuthService.LoginUser(request, new IAPIAuthCallback() {
            @Override
            public void OnSuccess(IAuthObject authObject) {
                if (LocalAuthService.isSessionExpired() && !isTaskRoot()) {
                    setAuthObject(authObject);
                    finish();
                } else {
                    setAuthObject(authObject);
                    startActivity(new Intent(getApplicationContext(), ContainerActivity.class));
                }
            }

            @Override
            public void OnError(String errorMessage) {
                AlertService.LongAlert(currentActivity, errorMessage);
            }
        });
    }

    private void setAuthObject(IAuthObject authObject) {
        LocalAuthService.setAuthObject(this, authObject);
        AlertService.ShortAlert(this, "User Authenticated");
    }
}
