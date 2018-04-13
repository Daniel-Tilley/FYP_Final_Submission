package mobile_app.trainingpal.me;

import android.content.Intent;
import android.os.Bundle;

import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;

public class ContainerActivity extends NavBar{

    private boolean confirmExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load view first so that we can just call finish on the other activity
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_container, frameLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!LocalAuthService.isUserAuthenticated(this)) {
            AlertService.LongAlert(this, getString(R.string.app_session_has_expired_error));
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
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
}
