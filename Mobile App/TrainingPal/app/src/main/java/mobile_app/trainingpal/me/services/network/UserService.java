package mobile_app.trainingpal.me.services.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIResponseCallback;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPIUserCallback;
import mobile_app.trainingpal.me.interfaces.models.network.IRequest;
import mobile_app.trainingpal.me.models.User;
import mobile_app.trainingpal.me.models.network.ErrorResponse;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class UserService {

    private UserService () { }

    public static void createUser(final IRequest request, final IAPIResponseCallback responseCallback) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        JSONObject jsonObj;

        try {
            jsonObj = new JSONObject(gson.toJson(request.getBodyUser()));
        } catch (JSONException e) {
            jsonObj = new JSONObject();
        }

        AndroidNetworking.post(request.getBaseUrl() + "/users")
                .addJSONObjectBody(jsonObj)
                .setTag(NetworkConstants.USER_TAG)
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

    public static void getUser(String username, final IRequest request, final IAPIUserCallback userCallback) {

        AndroidNetworking.get(request.getBaseUrl() + "/users/" + username)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.USER_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.serializeNulls();
                        Gson gson = gsonBuilder.create();
                        User user;

                        try {
                            user = gson.fromJson(
                                    String.valueOf(response.getJSONObject(NetworkConstants.DATA_KEY)
                                            .getJSONObject(NetworkConstants.USER_KEY)), User.class);

                            userCallback.OnSuccess(user);
                        } catch (JSONException e) {
                            Log.d(NetworkConstants.AUTH_TAG, e.getMessage());
                            userCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        try{
                            ErrorResponse errorResponse = error.getErrorAsObject(ErrorResponse.class);
                            userCallback.OnError(errorResponse.getMessage());
                        } catch(Exception e) {
                            userCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }
                });
    }

    public static void updateUser(final IRequest request, String currentUserId, final IAPIResponseCallback responseCallback) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        JSONObject jsonObj;

        try {
            jsonObj = new JSONObject(gson.toJson(request.getBodyUpdateObject()));
        } catch (JSONException e) {
            jsonObj = new JSONObject();
        }

        AndroidNetworking.put(request.getBaseUrl() + "/users/" + currentUserId)
                .addJSONObjectBody(jsonObj)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.USER_TAG)
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
