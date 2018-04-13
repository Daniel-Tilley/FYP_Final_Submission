package mobile_app.trainingpal.me.services;

import android.app.Activity;
import android.widget.Toast;

public final class AlertService {

    private AlertService() { }

    public static void LongAlert(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void ShortAlert(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
