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
import mobile_app.trainingpal.me.interfaces.callbacks.IAPITrainingLogCallback;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPITrainingLogsCallback;
import mobile_app.trainingpal.me.interfaces.models.network.IRequest;
import mobile_app.trainingpal.me.models.TrainingLog;
import mobile_app.trainingpal.me.models.network.ErrorResponse;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class TrainingLogService {

    private TrainingLogService () { }

    public static void getDailyTrainingLogs(
            String username,
            String day,
            String month,
            String year,
            final IRequest request,
            final IAPITrainingLogsCallback trainingLogsCallback
            ) {

        AndroidNetworking.get(request.getBaseUrl() + "/training-log/" + username + "/workouts")
                .addQueryParameter("day", day)
                .addQueryParameter("month", month)
                .addQueryParameter("year", year)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.TRAINING_LOG_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.serializeNulls();
                        Gson gson = gsonBuilder.create();
                        TrainingLog[] trainingLogs;

                        try {
                            trainingLogs = gson.fromJson(String.valueOf(
                                    response.getJSONObject(NetworkConstants.DATA_KEY)
                                            .getJSONArray(NetworkConstants.TRAINING_LOGS_KEY)), TrainingLog[].class);

                            trainingLogsCallback.OnSuccess(trainingLogs);
                        } catch (JSONException e) {
                            Log.d(NetworkConstants.AUTH_TAG, e.getMessage());
                            trainingLogsCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        try{
                            ErrorResponse errorResponse = error.getErrorAsObject(ErrorResponse.class);
                            trainingLogsCallback.OnError(errorResponse.getMessage());
                        } catch(Exception e) {
                            trainingLogsCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }
                });
    }

    public static void getMonthlyTrainingLogs(
                                              String username,
                                              String month,
                                              String year,
                                              final IRequest request,
                                              final IAPITrainingLogsCallback trainingLogsCallback
    ) {

        AndroidNetworking.get(request.getBaseUrl() + "/training-log/" + username + "/workouts")
                .addQueryParameter("month", month)
                .addQueryParameter("year", year)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.TRAINING_LOG_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.serializeNulls();
                        Gson gson = gsonBuilder.create();
                        TrainingLog[] trainingLogs;

                        try {
                            trainingLogs = gson.fromJson(String.valueOf(
                                    response.getJSONObject(NetworkConstants.DATA_KEY)
                                            .getJSONArray(NetworkConstants.TRAINING_LOGS_KEY)), TrainingLog[].class);

                            trainingLogsCallback.OnSuccess(trainingLogs);
                        } catch (JSONException e) {
                            Log.d(NetworkConstants.AUTH_TAG, e.getMessage());
                            trainingLogsCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        try{
                            ErrorResponse errorResponse = error.getErrorAsObject(ErrorResponse.class);
                            trainingLogsCallback.OnError(errorResponse.getMessage());
                        } catch(Exception e) {
                            trainingLogsCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }
                });
    }

    public static void getSingleTrainingLog(String username, int id, final IRequest request, final IAPITrainingLogCallback trainingLogCallback) {

        AndroidNetworking.get(request.getBaseUrl() + "/training-log/" + username + "/workout/" + id)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.TRAINING_LOG_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.serializeNulls();
                        Gson gson = gsonBuilder.create();
                        TrainingLog trainingLog;

                        try {
                            trainingLog = gson.fromJson(String.valueOf(
                                    response.getJSONObject(NetworkConstants.DATA_KEY)
                                            .getJSONObject(NetworkConstants.TRAINING_LOG_KEY)), TrainingLog.class);

                            trainingLogCallback.OnSuccess(trainingLog);
                        } catch (JSONException e) {
                            Log.d(NetworkConstants.AUTH_TAG, e.getMessage());
                            trainingLogCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        try{
                            ErrorResponse errorResponse = error.getErrorAsObject(ErrorResponse.class);
                            trainingLogCallback.OnError(errorResponse.getMessage());
                        } catch(Exception e) {
                            trainingLogCallback.OnError(NetworkConstants.DEFAULT_ERROR_MESSAGE);
                        }
                    }
                });
    }

    public static void createTrainingLog(String username, final IRequest request, final IAPIResponseCallback responseCallback) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        JSONObject jsonObj;

        try {
            jsonObj = new JSONObject(gson.toJson(request.getBodyTrainingLog()));
        } catch (JSONException e) {
            jsonObj = new JSONObject();
        }

        AndroidNetworking.post(request.getBaseUrl() + "/training-log/" + username)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .addJSONObjectBody(jsonObj)
                .setTag(NetworkConstants.TRAINING_LOG_TAG)
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

    public static void updateTrainingLog(String username, int id, final IRequest request, final IAPIResponseCallback responseCallback) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        JSONObject jsonObj;

        try {
            jsonObj = new JSONObject(gson.toJson(request.getBodyUpdateObject()));
        } catch (JSONException e) {
            jsonObj = new JSONObject();
        }

        AndroidNetworking.put(request.getBaseUrl() + "/training-log/" + username + "/workout/" + id)
                .addJSONObjectBody(jsonObj)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.TRAINING_LOG_TAG)
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

    public static void deleteTrainingLog(String username, int id, final IRequest request, final IAPIResponseCallback responseCallback) {

        AndroidNetworking.delete(request.getBaseUrl() + "/training-log/" + username + "/workout/" + id)
                .addHeaders(request.getHeader().getHeaderKey(), request.getHeader().getHeaderValue())
                .setTag(NetworkConstants.TRAINING_LOG_TAG)
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
