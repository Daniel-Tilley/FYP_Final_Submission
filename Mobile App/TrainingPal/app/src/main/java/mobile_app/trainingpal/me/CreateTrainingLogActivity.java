package mobile_app.trainingpal.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIResponseCallback;
import mobile_app.trainingpal.me.models.TrainingLog;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.TrainingLogService;
import mobile_app.trainingpal.me.shared.TrainingLogForm;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class CreateTrainingLogActivity extends Activity {

    private boolean confirmExit = false;

    private TrainingLogForm trainingLogForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training_log);

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
        trainingLogForm = new TrainingLogForm(this);

        trainingLogForm.setEntryNameLabel(getString(R.string.training_log_form_entry_prefix) + " " + trainingLogForm.getEntryNameLabel().getText().toString());
        trainingLogForm.setEntryDateLabel(getString(R.string.training_log_form_entry_prefix) + " " + trainingLogForm.getEntryDateLabel().getText().toString() + " " + getString(R.string.training_log_form_required_indicator));
        trainingLogForm.setEntryTimeLabel(getString(R.string.training_log_form_entry_prefix) + " " + trainingLogForm.getEntryTimeLabel().getText().toString());
        trainingLogForm.setEntryDescriptionLabel(getString(R.string.training_log_form_entry_prefix) + " " + trainingLogForm.getEntryDescriptionLabel().getText().toString());
        trainingLogForm.setEntryTypeLabel(getString(R.string.training_log_form_entry_prefix) + " " + trainingLogForm.getEntryTypeLabel().getText().toString() +  " " + getString(R.string.training_log_form_required_indicator));

        trainingLogForm.getFinalButton().setText("Create New Entry");
        trainingLogForm.getFinalButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnCreateTrainingLogClicked();
            }
        });
    }

    private void OnCreateTrainingLogClicked() {
        if (trainingLogForm.checkAllFields()) {
            createUser();
        } else {
            AlertService.LongAlert(this, getString(R.string.app_form_alert_error));
        }
    }

    private void createUser() {
        String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(this).getTokenString();
        String username = LocalAuthService.getAuthObject(this).getUserId();

        TrainingLog trainingLog = (TrainingLog) trainingLogForm.getTrainingLogObject();

        Request request = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header(NetworkConstants.AUTHORIZATION_HEADER, token),
                trainingLog
        );

        final Activity activity = this;

        TrainingLogService.createTrainingLog(username, request, new IAPIResponseCallback() {
            @Override
            public void OnSuccess(String successMessage) {
                AlertService.LongAlert(activity, "Entry created!");
                finish();
            }

            @Override
            public void OnError(String errorMessage) {
                AlertService.LongAlert(activity, errorMessage);
            }
        });
    }
}
