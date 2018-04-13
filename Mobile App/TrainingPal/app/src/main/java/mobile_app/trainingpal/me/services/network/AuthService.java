package mobile_app.trainingpal.me.services.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIAuthCallback;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPIResponseCallback;
import mobile_app.trainingpal.me.interfaces.models.network.IRequest;
import mobile_app.trainingpal.me.models.AuthObject;
import mobile_app.trainingpal.me.models.network.ErrorResponse;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class AuthService {

    private AuthService() { }

    public static void LoginUser(final IRequest request, final IAPIAuthCallback authCallback) {

        AndroidNetworking.get(request.getBaseUrl() + "/auth/login")
                .setTag(NetworkConstants.AUTH_TAG)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            AuthObject authObject;
                            Gson gson = new Gson();

                            authObject = gson.fromJson(
                                    String.valueOf(response.getJSONObject(NetworkConstants.DATA_KEY)
                                            .getJSONObject(NetworkConstants.TOKEN_KEY)), AuthObject.class);

                            authCallback.OnSuccess(authObject);
                        } catch (JSONException e) {
                            Log.d(NetworkConstants.AUTH_TAG, e.getMessage());
                            authCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        try{
                            ErrorResponse errorResponse = error.getErrorAsObject(ErrorResponse.class);
                            authCallback.OnError(errorResponse.getMessage());
                        } catch(Exception e) {
                            authCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }
                });
    }

    public static void CheckPassword(String password, final IRequest request, final IAPIResponseCallback responseCallback) {

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("Password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(request.getBaseUrl() + "/auth/check-password")
                .addJSONObjectBody(jsonObj)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.AUTH_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String message = "";

                        try {
                            message = response.getString(NetworkConstants.MESSAGE_KEY);
                        } catch (JSONException e) {
                            responseCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }

                        responseCallback.OnSuccess(message);
                    }
                    @Override
                    public void onError(ANError error) {
                        try{
                            ErrorResponse errorResponse = error.getErrorAsObject(ErrorResponse.class);
                            responseCallback.OnError(errorResponse.getMessage());
                        } catch(Exception e) {
                            responseCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }
                });
    }
}



/*


 */
