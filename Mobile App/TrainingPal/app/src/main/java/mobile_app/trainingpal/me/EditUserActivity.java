package mobile_app.trainingpal.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIResponseCallback;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPIUserCallback;
import mobile_app.trainingpal.me.interfaces.models.IUser;
import mobile_app.trainingpal.me.models.User;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.models.network.UpdateObject;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.AuthService;
import mobile_app.trainingpal.me.services.network.UserService;
import mobile_app.trainingpal.me.shared.UserForm;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class EditUserActivity extends Activity {

    private UserForm userForm;
    private User currentUser;
    private boolean confirmExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        initialiseVariables();
    }

    @Override
    public void onBackPressed() {
        if (!confirmExit) {
            AlertService.LongAlert(this, getString(R.string.page_confirm_exit_message));
            confirmExit = true;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!LocalAuthService.isUserAuthenticated(this)) {
            AlertService.LongAlert(this, getString(R.string.app_session_has_expired_error));
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    private void initialiseVariables() {
        userForm = new UserForm(this);
        userForm.makeUsernameFieldReadOnly();
        userForm.makeTypeFieldReadOnly();

        String username = LocalAuthService.getAuthObject(this).getUserId();
        String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(this).getTokenString();

        Request request = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header(NetworkConstants.AUTHORIZATION_HEADER, token)
        );

        final Activity currentActivity = this;

        UserService.getUser(username, request, new IAPIUserCallback() {
            @Override
            public void OnSuccess(IUser user) {
                currentUser = (User) user;

                userForm.setUsernameField(user.getId());
                userForm.setFirstnameField(user.getF_Name());
                userForm.setLastnameField(user.getL_Name());
                userForm.setEmailField(user.getE_Mail());
                userForm.setDateOfBirthField(user.getDOB());
                userForm.setTypeField(user.getType());
                userForm.setLocationField(user.getLocation());
                userForm.setBioField((user.getBio() == null ? "" : user.getBio()));

                userForm.setUsernameLabel(userForm.getUsernameLabel() + " *");
                userForm.setPasswordLabel("New Password");
                userForm.setConfirmPasswordLabel("Confirm New Password");
                userForm.setFirstnameLabel(userForm.getFirstnameLabel() + " *");
                userForm.setLastnameLabel(userForm.getLastnameLabel() + " *");
                userForm.setEmailLabel(userForm.getEmailLabel() + " *");
                userForm.setDateOfBirthLabel(userForm.getDateOfBirthLabel() + " *");
                userForm.setLocationLabel(userForm.getLocationLabel() + " *");
                userForm.setTypeLabel(userForm.getTypeLabel() + " *");

            }

            @Override
            public void OnError(String errorMessage) {
                AlertService.LongAlert(currentActivity, errorMessage);
            }
        });
    }

    public void onEditUserButtonClicked(View view) {
        if (checkForNewPassword()) {
            if (userForm.checkAllFields()) {
                updateUser(true);
            } else {
                AlertService.LongAlert(this, getResources().getString(R.string.app_form_alert_error));
            }
        } else {
            if (userForm.checkAllFieldsExceptPassword()) {
                updateUser(false);
            } else {
                AlertService.LongAlert(this, getResources().getString(R.string.app_form_alert_error));
            }
        }
    }

    private boolean checkForNewPassword () {
        if (userForm.getPasswordField().equals("") && userForm.getConfirmPasswordField().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void updateUser(boolean hasNewPassword) {
        User newUser = new User();

        if (hasNewPassword) {
            newUser.setPassword(userForm.getPasswordField());
        } else {
            newUser.setPassword(currentUser.getPassword());
        }

        newUser.setF_Name(userForm.getFirstnameField());
        newUser.setL_Name(userForm.getLastnameField());
        newUser.setE_Mail(userForm.getEmailField());
        newUser.setDOB(userForm.getDateOfBirthField());
        newUser.setLocation(userForm.getLocationField());
        newUser.setBio(userForm.getBioField());

        compareUsers(newUser);
    }

    private void compareUsers(final User newUser) {
        String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(this).getTokenString();
        String currentUserName = LocalAuthService.getAuthObject(this).getUserId();
        final Activity currentActivity = this;

        Request passwordCheckRequest = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header(NetworkConstants.AUTHORIZATION_HEADER, token)
        );

        final User updatedUser = newUser;

        if (newUser.getPassword() != null) {
            AuthService.CheckPassword(newUser.getPassword(), passwordCheckRequest, new IAPIResponseCallback() {
                @Override
                public void OnSuccess(String successMessage) {
                    AlertService.LongAlert(currentActivity, "New password cannot be current password!");
                }

                @Override
                public void OnError(String errorMessage) {
                    continueCheck(true, newUser);
                }
            });
        } else {
            continueCheck(false, newUser);
        }
    }

    public void continueCheck(boolean newPassword, User newUser) {
        boolean itemsToUpdate = false;
        List<Object> memberKeys = new ArrayList<Object>();
        List<Object> memberValues = new ArrayList<Object>();

        String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(this).getTokenString();
        String currentUserName = LocalAuthService.getAuthObject(this).getUserId();
        final Activity currentActivity = this;

        if (newPassword) {
            memberKeys.add("Password");
            memberValues.add(newUser.getPassword());
            itemsToUpdate = true;
        }

        if(!currentUser.getF_Name().equals(newUser.getF_Name())) {
            memberKeys.add("F_Name");
            memberValues.add(newUser.getF_Name());
            itemsToUpdate = true;
        }

        if (!currentUser.getL_Name().equals(newUser.getL_Name())) {
            memberKeys.add("L_Name");
            memberValues.add(newUser.getL_Name());
            itemsToUpdate = true;
        }

        if (!currentUser.getE_Mail().equals(newUser.getE_Mail())) {
            memberKeys.add("E_Mail");
            memberValues.add(newUser.getE_Mail());
            itemsToUpdate = true;
        }

        if (!currentUser.getDOB().equals(newUser.getDOB())) {
            memberKeys.add("DOB");
            memberValues.add(newUser.getDOB());
            itemsToUpdate = true;
        }

        if (!currentUser.getLocation().equals(newUser.getLocation())) {
            memberKeys.add("Location");
            memberValues.add(newUser.getLocation());
            itemsToUpdate = true;
        }

        if (!currentUser.getBio().equals(newUser.getBio())) {
            memberKeys.add("Bio");
            memberValues.add((newUser.getBio().equals("") ? null : newUser.getBio()));
            itemsToUpdate = true;
        }

        if (itemsToUpdate) {
            UpdateObject updateObject = new UpdateObject();
            updateObject.setMemberKeys(memberKeys);
            updateObject.setMemberValues(memberValues);

            Request updateUserRequest = new Request(
                    getResources().getString(R.string.app_api_base_url),
                    new Header(NetworkConstants.AUTHORIZATION_HEADER, token),
                    updateObject
            );

            UserService.updateUser(updateUserRequest, currentUserName, new IAPIResponseCallback() {
                @Override
                public void OnSuccess(String successMessage) {
                    AlertService.LongAlert(currentActivity, successMessage);
                    currentActivity.recreate();
                    userForm.setPasswordField("");
                    userForm.setConfirmPasswordField("");
                }

                @Override
                public void OnError(String errorMessage) {
                    AlertService.LongAlert(currentActivity, errorMessage);
                }
            });
        } else {
            AlertService.LongAlert(this, "Nothing to update!");
        }
    }
}
