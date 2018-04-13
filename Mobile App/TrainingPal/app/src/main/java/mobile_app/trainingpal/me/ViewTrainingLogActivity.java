package mobile_app.trainingpal.me;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIResponseCallback;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPITrainingLogCallback;
import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.TrainingLogService;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class ViewTrainingLogActivity extends Activity {

    private int currentWorkoutId;

    private TextView entryNameValue;
    private TextView entryDateValue;
    private TextView entryTimeValue;
    private TextView entryDescriptionValue;
    private TextView entryTypeValue;

    private TextView plannedDurationValue;
    private TextView plannedDistanceValue;
    private TextView plannedRestingHrValue;
    private TextView plannedAvgHrValue;
    private TextView plannedMaxHrValue;
    private TextView plannedAvgWattsValue;
    private TextView plannedMaxWattsValue;
    private TextView plannedRPEValue;

    private TextView actualDurationValue;
    private TextView actualDistanceValue;
    private TextView actualRestingHrValue;
    private TextView actualAvgHrValue;
    private TextView actualMaxHrValue;
    private TextView actualAvgWattsValue;
    private TextView actualMaxWattsValue;
    private TextView actualRPEValue;

    private TextView caloriesBurnedValue;
    private TextView hrZone1Value;
    private TextView hrZone2Value;
    private TextView hrZone3Value;
    private TextView hrZone4Value;
    private TextView hrZone5Value;
    private TextView hrZone6Value;
    private TextView sleepQualityValue;

    private TextView athletesCommentsValue;
    private TextView coachesCommentsValue;
    private TextView workoutCommentsValue;

    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_training_log);

        initialiseVariables();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!LocalAuthService.isUserAuthenticated(this)) {
            AlertService.LongAlert(this, getString(R.string.app_session_has_expired_error));
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            getWorkout();
        }
    }

    private void initialiseVariables() {
        currentWorkoutId = getIntent().getIntExtra("LOG_ID",-1);

        entryNameValue = findViewById(R.id.view_training_log_name_value);
        entryDateValue = findViewById(R.id.view_training_log_date_value);
        entryTimeValue = findViewById(R.id.view_training_log_time_value);
        entryDescriptionValue = findViewById(R.id.view_training_log_desc_value);
        entryTypeValue = findViewById(R.id.view_training_log_type_value);

        plannedDurationValue = findViewById(R.id.view_training_log_planned_duration_value);
        plannedDistanceValue = findViewById(R.id.view_training_log_planned_distance_value);
        plannedRestingHrValue = findViewById(R.id.view_training_log_planned_resting_hr_value);
        plannedAvgHrValue = findViewById(R.id.view_training_log_planned_avg_hr_value);
        plannedMaxHrValue = findViewById(R.id.view_training_log_planned_max_hr_value);
        plannedAvgWattsValue = findViewById(R.id.view_training_log_planned_avg_watts_value);
        plannedMaxWattsValue = findViewById(R.id.view_training_log_planned_max_watts_value);
        plannedRPEValue = findViewById(R.id.view_training_log_planned_rpe_value);

        actualDurationValue = findViewById(R.id.view_training_log_actual_duration_value);
        actualDistanceValue = findViewById(R.id.view_training_log_actual_distance_value);
        actualRestingHrValue = findViewById(R.id.view_training_log_actual_resting_hr_value);
        actualAvgHrValue = findViewById(R.id.view_training_log_actual_avg_hr_value);
        actualMaxHrValue = findViewById(R.id.view_training_log_actual_max_hr_value);
        actualAvgWattsValue = findViewById(R.id.view_training_log_actual_avg_watts_value);
        actualMaxWattsValue = findViewById(R.id.view_training_log_actual_max_watts_value);
        actualRPEValue = findViewById(R.id.view_training_log_actual_rpe_value);

        caloriesBurnedValue = findViewById(R.id.view_training_log_calories_value);
        hrZone1Value = findViewById(R.id.view_training_log_zone1_value);
        hrZone2Value = findViewById(R.id.view_training_log_zone2_value);
        hrZone3Value = findViewById(R.id.view_training_log_zone3_value);
        hrZone4Value = findViewById(R.id.view_training_log_zone4_value);
        hrZone5Value = findViewById(R.id.view_training_log_zone5_value);
        hrZone6Value = findViewById(R.id.view_training_log_zone6_value);
        sleepQualityValue = findViewById(R.id.view_training_log_sleep_value);

        athletesCommentsValue = findViewById(R.id.view_training_log_athletes_comments_value);
        coachesCommentsValue = findViewById(R.id.view_training_log_coaches_comments_value);
        workoutCommentsValue = findViewById(R.id.view_training_log_workout_comments_value);

        //show our alert dialog
        dialog = new AlertDialog.Builder(this, R.style.DialogTheme);
        dialog.setTitle("Delete Training Log Entry");
        dialog.setMessage("Are you sure you want to delete this item?");
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_delete);

        //set buttons is dialog
        //positive
        dialog.setPositiveButton(("Yes"),

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete from database
                        deleteTrainingLogEntry();

                    }
                });

        //negative
        dialog.setNegativeButton(("No"),

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close dialog
                        dialog.cancel();
                    }
                });
    }

    private void getWorkout() {
        if (currentWorkoutId == -1) {
            AlertService.LongAlert(this, NetworkConstants.DEFAULT_ERROR_MESSAGE);
        } else {
            String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(this).getTokenString();
            String username = LocalAuthService.getAuthObject(this).getUserId();

            Request request = new Request(
                    getResources().getString(R.string.app_api_base_url),
                    new Header(NetworkConstants.AUTHORIZATION_HEADER, token)
            );

            final Activity currentActivity = this;

            TrainingLogService.getSingleTrainingLog(username, currentWorkoutId, request, new IAPITrainingLogCallback() {
                @Override
                public void OnSuccess(ITrainingLog trainingLog) {
                    setVariables(trainingLog);
                }

                @Override
                public void OnError(String errorMessage) {
                    AlertService.LongAlert(currentActivity, errorMessage);
                }
            });
        }
    }

    private void setVariables(ITrainingLog trainingLog) {
        Resources resources = getResources();

        entryNameValue.setText((trainingLog.getLog_Name() == null ? "LogEntry" : trainingLog.getLog_Name()));
        entryNameValue.setTypeface(null, Typeface.BOLD);
        entryDateValue.setText((trainingLog.getLog_Date() == null ? "" : trainingLog.getLog_Date()));
        entryTimeValue.setText((trainingLog.getLog_Time() == null ? "" : trainingLog.getLog_Time()));
        entryDescriptionValue.setText((trainingLog.getLog_Description() == null ? "" : trainingLog.getLog_Description()));

        entryTypeValue.setText((trainingLog.getType_ID() == null ? "" : resources.getStringArray(R.array.shared_array_training_log_type_values)[trainingLog.getType_ID()]));

        plannedDurationValue.setText((trainingLog.getDuration_Planned() == null ? "" : trainingLog.getDuration_Planned()));
        plannedDistanceValue.setText((trainingLog.getDistance_Planned() == null ? "" : trainingLog.getDistance_Planned().toString() + " " + trainingLog.getDistance_Unit()));
        plannedRestingHrValue.setText((trainingLog.getHR_Resting_Planned() == null ? "" : trainingLog.getHR_Resting_Planned().toString()));
        plannedAvgHrValue.setText((trainingLog.getHR_Avg_Planned() == null ? "" : trainingLog.getHR_Avg_Planned().toString()));
        plannedMaxHrValue.setText((trainingLog.getHR_Max_Planned() == null ? "" : trainingLog.getHR_Max_Planned().toString()));
        plannedAvgWattsValue.setText((trainingLog.getWatts_Avg_Planned() == null ? "" : trainingLog.getWatts_Avg_Planned().toString()));
        plannedMaxWattsValue.setText((trainingLog.getWatts_Max_Planned() == null ? "" : trainingLog.getWatts_Max_Planned().toString()));
        plannedRPEValue.setText((trainingLog.getRPE_Planned() == null ? "" : trainingLog.getRPE_Planned().toString()));

        actualDurationValue.setText((trainingLog.getDuration_Actual() == null ? "" : trainingLog.getDuration_Actual()));
        actualDistanceValue.setText((trainingLog.getDistance_Actual() == null ? "" : trainingLog.getDistance_Actual().toString() + " " + trainingLog.getDistance_Unit()));
        actualRestingHrValue.setText((trainingLog.getHR_Resting_Actual() == null ? "" : trainingLog.getHR_Resting_Actual().toString()));
        actualAvgHrValue.setText((trainingLog.getHR_Avg_Actual() == null ? "" : trainingLog.getHR_Avg_Actual().toString()));
        actualMaxHrValue.setText((trainingLog.getHR_Max_Actual() == null ? "" : trainingLog.getHR_Max_Actual().toString()));
        actualAvgWattsValue.setText((trainingLog.getWatts_Avg_Actual() == null ? "" : trainingLog.getWatts_Avg_Actual().toString()));
        actualMaxWattsValue.setText((trainingLog.getWatts_Max_Actual() == null ? "" : trainingLog.getWatts_Max_Actual().toString()));
        actualRPEValue.setText((trainingLog.getRPE_Actual() == null ? "" : trainingLog.getRPE_Actual().toString()));

        caloriesBurnedValue.setText((trainingLog.getCalories_Burned() == null ? "" : trainingLog.getCalories_Burned().toString()));
        hrZone1Value.setText((trainingLog.getHR_Zone1_Time() == null ? "" : trainingLog.getHR_Zone1_Time()));
        hrZone2Value.setText((trainingLog.getHR_Zone2_Time() == null ? "" : trainingLog.getHR_Zone2_Time()));
        hrZone3Value.setText((trainingLog.getHR_Zone3_Time() == null ? "" : trainingLog.getHR_Zone3_Time()));
        hrZone4Value.setText((trainingLog.getHR_Zone4_Time() == null ? "" : trainingLog.getHR_Zone5_Time()));
        hrZone5Value.setText((trainingLog.getHR_Zone5_Time() == null ? "" : trainingLog.getHR_Zone5_Time()));
        hrZone6Value.setText((trainingLog.getHR_Zone6_Time() == null ? "" : trainingLog.getHR_Zone6_Time()));
        sleepQualityValue.setText((trainingLog.getSleep_Quality() == null ? "" : resources.getStringArray(R.array.shared_array_training_log_sleep_quality_values)[trainingLog.getSleep_Quality()]));

        athletesCommentsValue.setText((trainingLog.getAthletes_Comments() == null ? "" : trainingLog.getAthletes_Comments()));
        coachesCommentsValue.setText((trainingLog.getCoaches_Comments() == null ? "" : trainingLog.getCoaches_Comments()));
        workoutCommentsValue.setText((trainingLog.getWorkout_Comments() == null ? "" : trainingLog.getWorkout_Comments()));
    }

    public void onViewTrainingLogPageEditClicked(View view) {
        if (currentWorkoutId == -1) {
            AlertService.LongAlert(this, NetworkConstants.DEFAULT_ERROR_MESSAGE);
        } else {
            Intent intent = new Intent(getApplicationContext(), EditTrainingLogActivity.class);
            intent.putExtra("LOG_ID", currentWorkoutId);
            startActivity(intent);
        }
    }

    public void onViewTrainingLogPageDeleteClicked(View view) {
        if (currentWorkoutId == -1) {
            AlertService.LongAlert(this, NetworkConstants.DEFAULT_ERROR_MESSAGE);
        } else {
            AlertDialog alertD = dialog.create();
            alertD.show();
        }
    }

    private void deleteTrainingLogEntry() {
        String token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(this).getTokenString();
        String username = LocalAuthService.getAuthObject(this).getUserId();

        Request request = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header(NetworkConstants.AUTHORIZATION_HEADER, token)
        );

        final Activity activity = this;

        TrainingLogService.deleteTrainingLog(username, currentWorkoutId, request, new IAPIResponseCallback() {
            @Override
            public void OnSuccess(String successMessage) {
                AlertService.LongAlert(activity, successMessage);
                finish();
            }

            @Override
            public void OnError(String errorMessage) {
                AlertService.LongAlert(activity, errorMessage);
            }
        });
    }
}
