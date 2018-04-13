package mobile_app.trainingpal.me.services;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.interfaces.models.IAuthObject;
import mobile_app.trainingpal.me.models.AuthObject;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class LocalAuthService {

    private static boolean sessionExpired = false;

    private LocalAuthService() { }

    public static void setAuthObject(Activity activity, IAuthObject authObject) {
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        Gson gson = new Gson();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NetworkConstants.TOKEN_KEY, gson.toJson(authObject));
        editor.apply();
        sessionExpired = false;
    }

    public static IAuthObject getAuthObject(Activity activity) {
        SharedPreferences sharedPreferences = getSharedPreferences(activity);

        Gson gson = new Gson();

        // Check if token exists
        if (sharedPreferences.contains(NetworkConstants.TOKEN_KEY)) {
            return gson.fromJson(sharedPreferences.getString(NetworkConstants.TOKEN_KEY, ""), AuthObject.class);
        } else {
            return null;
        }
    }

    public static void deleteAuthObject(Activity activity) {
        SharedPreferences sharedPreferences = getSharedPreferences(activity);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(NetworkConstants.TOKEN_KEY);
        editor.apply();
    }

    public static boolean isUserAuthenticated(Activity activity) {
        SharedPreferences sharedPreferences = getSharedPreferences(activity);
        Gson gson = new Gson();

        // Check if token exists
        if (sharedPreferences.contains(NetworkConstants.TOKEN_KEY)) {

            AuthObject authObject = gson.fromJson(sharedPreferences.getString(NetworkConstants.TOKEN_KEY, ""), AuthObject.class);

            // Check if object was created
            if (authObject != null) {

                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.getDefault());

                try {
                    Date expireyDate = format.parse(authObject.getExpiryDate());

                    // Check if token is still in date
                    if ( now.before(expireyDate)) {
                        return true;
                    } else {
                        sessionExpired = true;
                        return false;
                    }

                } catch (ParseException e) { return false; }

            } else { return false; }

        } else { return false; }
    }

    private static SharedPreferences getSharedPreferences (Activity activity) {
        return activity.getSharedPreferences(activity.getString(R.string.app_cookie_storage_filename), 0);
    }

    public static boolean isSessionExpired() {
        return sessionExpired;
    }
}
