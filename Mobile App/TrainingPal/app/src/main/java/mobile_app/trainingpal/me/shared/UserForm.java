package mobile_app.trainingpal.me.shared;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

import mobile_app.trainingpal.me.R;

public class UserForm {

    private Activity currentActivity;

    // Input fields
    private EditText usernameField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText firstnameField;
    private EditText lastnameField;
    private EditText emailField;
    private EditText dateOfBirthField;
    private Spinner typeField;
    private Spinner locationField;
    private EditText bioField;

    private String selectedTypeField;
    private String selectedLocataionField;

    // Error Headings
    private TextView usernameError;
    private TextView passwordError;
    private TextView confirmPasswordError;
    private TextView firstnameError;
    private TextView lastnameError;
    private TextView emailError;
    private TextView dateOfBirthError;
    private TextView typeError;
    private TextView locationError;
    private TextView bioError;

    // Labels
    private TextView usernameLabel;
    private TextView passwordLabel;
    private TextView confirmPasswordLabel;
    private TextView firstNameLabel;
    private TextView lastNameLabel;
    private TextView emailLabel;
    private TextView dateOfBirthLabel;
    private TextView typeLabel;
    private TextView locationLabel;
    private TextView bioLabel;

    private Resources res;
    private String[] countries;
    private String[] typeKeys;
    private String[] typeValues;

    public UserForm(Activity currentActivity) {
        this.currentActivity = currentActivity;

        initialiseVariables();
    }

    public boolean checkAllFields() {

        boolean noErrors = true;

        if (!checkAllFieldsExceptPassword()) { noErrors = false; }
        if (!CheckPasswords()) { noErrors = false; }

        return noErrors;
    }

    public boolean checkAllFieldsExceptPassword() {

        boolean noErrors = true;

        if (!CheckUsername()) { noErrors = false; }
        if (!CheckFirstName()) { noErrors = false; }
        if (!CheckLastName()) { noErrors = false; }
        if (!CheckEmailAddress()) { noErrors = false; }
        if (!CheckDateOfBirth()) { noErrors = false; }
        if (!CheckType()) { noErrors = false; }
        if (!CheckLocation()) { noErrors = false; }

        return noErrors;
    }

    public void clearFields() {
        setUsernameField("");
        setPasswordField("");
        setConfirmPasswordField("");
        setFirstnameField("");
        setLastnameField("");
        setEmailField("");
        setDateOfBirthField("");
        setLocationField(res.getString(R.string.app_default_spinner_message));
        setTypeField(res.getString(R.string.app_default_spinner_message));
        setBioField("");
    }

    public void clearErrors() {
        usernameError.setText("");
        passwordError.setText("");
        confirmPasswordError.setText("");
        firstnameError.setText("");
        lastnameError.setText("");
        emailError.setText("");
        dateOfBirthError.setText("");
        typeError.setText("");
        locationError.setText("");
        bioError.setText("");
    }

    private boolean checkEditTextEmpty(EditText editText) {
        if (editText.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    // ---------------------------------------------------------------------------------------------
    // Getters and Setters
    // ---------------------------------------------------------------------------------------------


    // Username
    public String getUsernameField() { return usernameField.getText().toString(); }
    public void setUsernameField(String usernameField) { this.usernameField.setText(usernameField); }

    public String getUsernameLabel() { return this.usernameLabel.getText().toString(); }
    public void setUsernameLabel(String usernameLabel) { this.usernameLabel.setText(usernameLabel); this.usernameLabel.setTypeface(null, Typeface.BOLD); }

    // Password
    public String getPasswordField() { return passwordField.getText().toString(); }
    public void setPasswordField(String passwordField) { this.passwordField.setText(passwordField); }

    public String getPasswordLabel() { return this.passwordLabel.getText().toString(); }
    public void setPasswordLabel(String passwordLabel) { this.passwordLabel.setText(passwordLabel); this.passwordLabel.setTypeface(null, Typeface.BOLD); }

    // Confirm Password
    public String getConfirmPasswordField() { return confirmPasswordField.getText().toString(); }
    public void setConfirmPasswordField(String confirmPasswordField) { this.confirmPasswordField.setText(confirmPasswordField); }

    public String getConfirmPasswordLabel() { return this.confirmPasswordLabel.getText().toString(); }
    public void setConfirmPasswordLabel(String confirmPasswordLabel) { this.confirmPasswordLabel.setText(confirmPasswordLabel); this.confirmPasswordLabel.setTypeface(null, Typeface.BOLD); }

    // First Name
    public String getFirstnameField() { return firstnameField.getText().toString(); }
    public void setFirstnameField(String firstnameField) { this.firstnameField.setText(firstnameField); }

    public String getFirstnameLabel() { return this.firstNameLabel.getText().toString(); }
    public void setFirstnameLabel(String firstNameLabel) { this.firstNameLabel.setText(firstNameLabel); this.firstNameLabel.setTypeface(null, Typeface.BOLD); }

    // Last Name
    public String getLastnameField() { return lastnameField.getText().toString(); }
    public void setLastnameField(String lastnameField) { this.lastnameField.setText(lastnameField); }

    public String getLastnameLabel() { return this.lastNameLabel.getText().toString(); }
    public void setLastnameLabel(String lastNameLabel) { this.lastNameLabel.setText(lastNameLabel); this.lastNameLabel.setTypeface(null, Typeface.BOLD); }

    // Email Address
    public String getEmailField() { return emailField.getText().toString(); }
    public void setEmailField(String emailField) { this.emailField.setText(emailField); }

    public String getEmailLabel() { return this.emailLabel.getText().toString(); }
    public void setEmailLabel(String emailLabel) { this.emailLabel.setText(emailLabel); this.emailLabel.setTypeface(null, Typeface.BOLD); }

    // Date of Birth
    public String getDateOfBirthField() { return dateOfBirthField.getText().toString(); }
    public void setDateOfBirthField(String dateOfBirthField) { this.dateOfBirthField.setText(dateOfBirthField); }

    public String getDateOfBirthLabel() { return this.dateOfBirthLabel.getText().toString(); }
    public void setDateOfBirthLabel(String dateOfBirthLabel) { this.dateOfBirthLabel.setText(dateOfBirthLabel); this.dateOfBirthLabel.setTypeface(null, Typeface.BOLD); }

    // Type
    public String getTypeField() { return selectedTypeField; }
    public void setTypeField(String typeField) { this.typeField.setSelection(Arrays.asList(typeKeys).indexOf(typeField)); }

    public String getTypeLabel() { return this.typeLabel.getText().toString(); }
    public void setTypeLabel(String typeLabel) { this.typeLabel.setText(typeLabel); this.typeLabel.setTypeface(null, Typeface.BOLD); }

    // Location
    public String getLocationField() { return selectedLocataionField; }
    public void setLocationField(String locationField) { this.locationField.setSelection(Arrays.asList(countries).indexOf(locationField)); }

    public String getLocationLabel() { return this.locationLabel.getText().toString(); }
    public void setLocationLabel(String locationLabel) { this.locationLabel.setText(locationLabel); this.locationLabel.setTypeface(null, Typeface.BOLD); }

    // Bio Field
    public String getBioField() { return bioField.getText().toString(); }
    public void setBioField(String bioField) { this.bioField.setText(bioField); }

    public String getBioLabel() { return this.bioLabel.getText().toString(); }
    public void setBioLabel(String bioLabel) { this.bioLabel.setText(bioLabel); this.bioLabel.setTypeface(null, Typeface.BOLD); }


    // ---------------------------------------------------------------------------------------------
    // Modifiers
    // ---------------------------------------------------------------------------------------------

    public void makeUsernameFieldReadOnly() { usernameField.setFocusable(false); }

    public void makeTypeFieldReadOnly() {
        typeField.setEnabled(false);
    }

    // ---------------------------------------------------------------------------------------------
    // Checks
    // ---------------------------------------------------------------------------------------------


    public boolean CheckUsername() {
        if (checkEditTextEmpty(usernameField)) {
            usernameError.setText(R.string.app_form_required_error);
            return false;
        } else { return true; }
    }

    public boolean CheckPasswords() {
        boolean noErrors = true;
        boolean passwordOk = true;

        if (checkEditTextEmpty(passwordField)) {
            passwordError.setText(R.string.app_form_required_error);
            noErrors = false;
            passwordOk = false;
        } else {
            if (getPasswordField().length() < 6) {
                passwordError.setText("* Must be at least 6 characters! *");
                noErrors = false;
                passwordOk = false;
            }
        }

        if (checkEditTextEmpty(confirmPasswordField)) {
            confirmPasswordError.setText(R.string.app_form_required_error);
            noErrors = false;
        } else {
            if (getConfirmPasswordField().length() < 6) {
                confirmPasswordError.setText("* Must be at least 6 characters! *");
                noErrors = false;
            } else {
                if (!passwordOk){
                    String error = "* " + getPasswordLabel().replace(" *", "") + " Has Errors *";
                    confirmPasswordError.setText(error);
                } else {
                    if (!getPasswordField().equals(getConfirmPasswordField())) {
                        String error = "* " + getPasswordLabel().replace(" *", "") + " and " + getConfirmPasswordLabel().replace(" *", "") + " do not match *";
                        confirmPasswordError.setText(error);
                        passwordError.setText(error);
                    }
                }
            }
        }

        return noErrors;
    }

    public boolean CheckFirstName() {
        if (checkEditTextEmpty(firstnameField)) {
            firstnameError.setText(R.string.app_form_required_error);
            return false;
        } else { return true; }
    }

    public boolean CheckLastName() {
        if (checkEditTextEmpty(lastnameField)) {
            lastnameError.setText(R.string.app_form_required_error);
            return false;
        } else { return true; }
    }

    public boolean CheckEmailAddress() {
        boolean noErrors = true;

        if (checkEditTextEmpty(emailField)) {
            emailError.setText(R.string.app_form_required_error);
            noErrors = false;
        } else {
            if (!isEmailValid(getEmailField())) {
                emailError.setText("* Must be a valid email! *");
                noErrors = false;
            }
        }

        return noErrors;
    }

    public boolean CheckDateOfBirth() {
        if (checkEditTextEmpty(dateOfBirthField)) {
            dateOfBirthError.setText(R.string.app_form_required_error);
            return false;
        } else { return true; }
    }

    public boolean CheckType() {
        if (selectedTypeField.equals("")) {
            typeError.setText(R.string.app_form_required_error);
            return false;
        } else { return true; }
    }

    public boolean CheckLocation() {
        if (selectedLocataionField.equals("")) {
            locationError.setText(R.string.app_form_required_error);
            return false;
        } else { return true; }
    }


    // ---------------------------------------------------------------------------------------------
    // Variables
    // ---------------------------------------------------------------------------------------------


    private void initialiseVariables() {
        // Fields
        usernameField = currentActivity.findViewById(R.id.user_form_username_field);
        passwordField = currentActivity.findViewById(R.id.user_form_password_field);
        confirmPasswordField = currentActivity.findViewById(R.id.user_form_confirm_password_field);
        firstnameField = currentActivity.findViewById(R.id.user_form_firstname_field);
        lastnameField = currentActivity.findViewById(R.id.user_form_lastname_field);
        emailField = currentActivity.findViewById(R.id.user_form_email_field);
        bioField = currentActivity.findViewById(R.id.user_form_bio_field);

        dateOfBirthField = currentActivity.findViewById(R.id.user_form_dob_field);

        CustomDatePicker datePicker = new CustomDatePicker(dateOfBirthField, currentActivity, true);

        typeField = currentActivity.findViewById(R.id.user_form_type_field);
        locationField = currentActivity.findViewById(R.id.user_form_location_field);

        res = currentActivity.getResources();

        countries = res.getStringArray(R.array.shared_array_string_countries);
        typeKeys = res.getStringArray(R.array.shared_array_user_type_keys);
        typeValues = res.getStringArray(R.array.shared_array_user_type_values);

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(
                currentActivity,
                R.layout.spinner_item,
                countries
        );

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(
                currentActivity,
                R.layout.spinner_item,
                typeValues
        );

        typeField.setAdapter(typeAdapter);
        locationField.setAdapter(countriesAdapter);

        typeField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (parent.getItemAtPosition(position).equals(res.getString(R.string.app_default_spinner_message))) { selectedTypeField = ""; }
                else { selectedTypeField = typeKeys[position]; }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        locationField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (parent.getItemAtPosition(position).equals(res.getString(R.string.app_default_spinner_message))) { selectedLocataionField = ""; }
                else { selectedLocataionField = (String) parent.getItemAtPosition(position); }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // Error texts
        usernameError = currentActivity.findViewById(R.id.user_form_username_error);
        passwordError = currentActivity.findViewById(R.id.user_form_password_error);
        confirmPasswordError = currentActivity.findViewById(R.id.user_form_confirm_password_error);
        firstnameError = currentActivity.findViewById(R.id.user_form_firstname_error);
        lastnameError = currentActivity.findViewById(R.id.user_form_lastname_error);
        emailError = currentActivity.findViewById(R.id.user_form_email_error);
        dateOfBirthError = currentActivity.findViewById(R.id.user_form_dob_error);
        typeError = currentActivity.findViewById(R.id.user_form_type_error);
        locationError = currentActivity.findViewById(R.id.user_form_location_error);
        bioError = currentActivity.findViewById(R.id.user_form_bio_error);

        // Labels
        usernameLabel = currentActivity.findViewById(R.id.user_form_username_label);
        passwordLabel = currentActivity.findViewById(R.id.user_form_password_label);
        confirmPasswordLabel = currentActivity.findViewById(R.id.user_form_confirm_password_label);
        firstNameLabel = currentActivity.findViewById(R.id.user_form_firstname_label);
        lastNameLabel = currentActivity.findViewById(R.id.user_form_lastname_label);
        emailLabel = currentActivity.findViewById(R.id.user_form_email_label);
        dateOfBirthLabel = currentActivity.findViewById(R.id.user_form_dob_label);
        typeLabel = currentActivity.findViewById(R.id.user_form_type_label);
        locationLabel = currentActivity.findViewById(R.id.user_form_location_label);
        bioLabel = currentActivity.findViewById(R.id.user_form_bio_label);
    }
}
