package mobile_app.trainingpal.me;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mobile_app.trainingpal.me.interfaces.callbacks.IAPIResponseCallback;
import mobile_app.trainingpal.me.interfaces.callbacks.IAPITrainingLogCallback;
import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.models.TrainingLog;
import mobile_app.trainingpal.me.models.network.Header;
import mobile_app.trainingpal.me.models.network.Request;
import mobile_app.trainingpal.me.models.network.UpdateObject;
import mobile_app.trainingpal.me.services.AlertService;
import mobile_app.trainingpal.me.services.LocalAuthService;
import mobile_app.trainingpal.me.services.network.TrainingLogService;
import mobile_app.trainingpal.me.shared.TrainingLogForm;
import mobile_app.trainingpal.me.shared.network.NetworkConstants;

public class EditTrainingLogActivity extends Activity {

    private boolean confirmExit = false;

    private TrainingLogForm trainingLogForm;

    private String token;
    private int currentWorkoutId;
    private String username;
    private Request request;

    private ITrainingLog currentTrainingLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training_log);

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
        currentWorkoutId = getIntent().getIntExtra("LOG_ID",-1);
        token = NetworkConstants.BEARER_AUTH_TYPE + LocalAuthService.getAuthObject(this).getTokenString();
        username = LocalAuthService.getAuthObject(this).getUserId();

        request = new Request(
                getResources().getString(R.string.app_api_base_url),
                new Header(NetworkConstants.AUTHORIZATION_HEADER, token)
        );

        trainingLogForm = new TrainingLogForm(this);

        trainingLogForm.getFinalButton().setText("Update Entry");
        trainingLogForm.getFinalButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateEntryClicked();
            }
        });

        setFormDetails();
    }

    private void onUpdateEntryClicked() {
        compareTrainingLogs(trainingLogForm.getTrainingLogObject());
    }

    private void compareTrainingLogs(ITrainingLog newTrainingLog) {
        boolean itemsToUpdate = false;
        List<Object> memberKeys = new ArrayList<Object>();
        List<Object> memberValues = new ArrayList<Object>();

        // region Info Values
        if (!Objects.equals(currentTrainingLog.getLog_Name(), newTrainingLog.getLog_Name())) {
            memberKeys.add("Log_Name");
            memberValues.add(newTrainingLog.getLog_Name());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getLog_Date(), newTrainingLog.getLog_Date())) {
            memberKeys.add("Log_Date");
            memberValues.add(newTrainingLog.getLog_Date());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getLog_Time(), newTrainingLog.getLog_Time())) {
            memberKeys.add("Log_Time");
            memberValues.add(newTrainingLog.getLog_Time());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getLog_Description(), newTrainingLog.getLog_Description())) {
            memberKeys.add("Log_Description");
            memberValues.add(newTrainingLog.getLog_Description());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getType_ID(), newTrainingLog.getType_ID())) {
            memberKeys.add("Type_ID");
            memberValues.add(newTrainingLog.getType_ID());
            itemsToUpdate = true;
        }
        // endregion

        // region Data (Planned Values)
        if (!Objects.equals(currentTrainingLog.getDuration_Planned(), newTrainingLog.getDuration_Planned())) {
            memberKeys.add("Duration_Planned");
            memberValues.add(newTrainingLog.getDuration_Planned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getDistance_Planned(), newTrainingLog.getDistance_Planned())) {
            memberKeys.add("Distance_Planned");
            memberValues.add(newTrainingLog.getDistance_Planned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Resting_Planned(), newTrainingLog.getHR_Resting_Planned())) {
            memberKeys.add("HR_Resting_Planned");
            memberValues.add(newTrainingLog.getHR_Resting_Planned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Avg_Planned(), newTrainingLog.getHR_Avg_Planned())) {
            memberKeys.add("HR_Avg_Planned");
            memberValues.add(newTrainingLog.getHR_Avg_Planned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Max_Planned(), newTrainingLog.getHR_Max_Planned())) {
            memberKeys.add("HR_Max_Planned");
            memberValues.add(newTrainingLog.getHR_Max_Planned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getWatts_Avg_Planned(), newTrainingLog.getWatts_Avg_Planned())) {
            memberKeys.add("Watts_Avg_Planned");
            memberValues.add(newTrainingLog.getWatts_Avg_Planned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getWatts_Max_Planned(), newTrainingLog.getWatts_Max_Planned())) {
            memberKeys.add("Watts_Max_Planned");
            memberValues.add(newTrainingLog.getWatts_Max_Planned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getRPE_Planned(), newTrainingLog.getRPE_Planned())) {
            memberKeys.add("RPE_Planned");
            memberValues.add(newTrainingLog.getRPE_Planned());
            itemsToUpdate = true;
        }
        // endregion

        // region Data (Actual Values)
        if (!Objects.equals(currentTrainingLog.getDuration_Actual(), newTrainingLog.getDuration_Actual())) {
            memberKeys.add("Duration_Actual");
            memberValues.add(newTrainingLog.getDuration_Actual());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getDistance_Actual(), newTrainingLog.getDistance_Actual())) {
            memberKeys.add("Distance_Actual");
            memberValues.add(newTrainingLog.getDistance_Actual());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Resting_Actual(), newTrainingLog.getHR_Resting_Actual())) {
            memberKeys.add("HR_Resting_Actual");
            memberValues.add(newTrainingLog.getHR_Resting_Actual());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Avg_Actual(), newTrainingLog.getHR_Avg_Actual())) {
            memberKeys.add("HR_Avg_Actual");
            memberValues.add(newTrainingLog.getHR_Avg_Actual());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Max_Actual(), newTrainingLog.getHR_Max_Actual())) {
            memberKeys.add("HR_Max_Actual");
            memberValues.add(newTrainingLog.getHR_Max_Actual());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getWatts_Avg_Actual(), newTrainingLog.getWatts_Avg_Actual())) {
            memberKeys.add("Watts_Avg_Actual");
            memberValues.add(newTrainingLog.getWatts_Avg_Actual());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getWatts_Max_Actual(), newTrainingLog.getWatts_Max_Actual())) {
            memberKeys.add("Watts_Max_Actual");
            memberValues.add(newTrainingLog.getWatts_Max_Actual());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getRPE_Actual(), newTrainingLog.getRPE_Actual())) {
            memberKeys.add("RPE_Actual");
            memberValues.add(newTrainingLog.getRPE_Actual());
            itemsToUpdate = true;
        }
        // endregion

        // region Data (Additional Values)
        if (!Objects.equals(currentTrainingLog.getCalories_Burned(), newTrainingLog.getCalories_Burned())) {
            memberKeys.add("Calories_Burned");
            memberValues.add(newTrainingLog.getCalories_Burned());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getSleep_Quality(), newTrainingLog.getSleep_Quality())) {
            memberKeys.add("Sleep_Quality");
            memberValues.add(newTrainingLog.getSleep_Quality());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Zone1_Time(), newTrainingLog.getHR_Zone1_Time())) {
            memberKeys.add("HR_Zone1_Time");
            memberValues.add(newTrainingLog.getHR_Zone1_Time());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Zone2_Time(), newTrainingLog.getHR_Zone2_Time())) {
            memberKeys.add("HR_Zone2_Time");
            memberValues.add(newTrainingLog.getHR_Zone2_Time());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Zone3_Time(), newTrainingLog.getHR_Zone3_Time())) {
            memberKeys.add("HR_Zone3_Time");
            memberValues.add(newTrainingLog.getHR_Zone3_Time());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Zone4_Time(), newTrainingLog.getHR_Zone4_Time())) {
            memberKeys.add("HR_Zone4_Time");
            memberValues.add(newTrainingLog.getHR_Zone4_Time());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Zone5_Time(), newTrainingLog.getHR_Zone5_Time())) {
            memberKeys.add("HR_Zone5_Time");
            memberValues.add(newTrainingLog.getHR_Zone5_Time());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getHR_Zone6_Time(), newTrainingLog.getHR_Zone6_Time())) {
            memberKeys.add("HR_Zone6_Time");
            memberValues.add(newTrainingLog.getHR_Zone6_Time());
            itemsToUpdate = true;
        }
        // endregion

        // region Comments
        if (!Objects.equals(currentTrainingLog.getAthletes_Comments(), newTrainingLog.getAthletes_Comments())) {
            memberKeys.add("Athletes_Comments");
            memberValues.add(newTrainingLog.getAthletes_Comments());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getCoaches_Comments(), newTrainingLog.getCoaches_Comments())) {
            memberKeys.add("Coaches_Comments");
            memberValues.add(newTrainingLog.getCoaches_Comments());
            itemsToUpdate = true;
        }

        if (!Objects.equals(currentTrainingLog.getWorkout_Comments(), newTrainingLog.getWorkout_Comments())) {
            memberKeys.add("Workout_Comments");
            memberValues.add(newTrainingLog.getWorkout_Comments());
            itemsToUpdate = true;
        }
        // endregion

        if (itemsToUpdate) {
            UpdateObject updateObject = new UpdateObject();
            updateObject.setMemberKeys(memberKeys);
            updateObject.setMemberValues(memberValues);

            final Activity currentActivity = this;

            Request request = new Request(
                    getResources().getString(R.string.app_api_base_url),
                    new Header(NetworkConstants.AUTHORIZATION_HEADER, token),
                    updateObject
            );


            TrainingLogService.updateTrainingLog(username, currentWorkoutId, request, new IAPIResponseCallback() {
                @Override
                public void OnSuccess(String successMessage) {
                    AlertService.LongAlert(currentActivity, successMessage);
                    currentActivity.recreate();
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

    private void setFormDetails() {
        if (currentWorkoutId == -1) {
            AlertService.LongAlert(this, NetworkConstants.DEFAULT_ERROR_MESSAGE);
        } else {
            final Activity currentActivity = this;
            final Resources resources = getResources();

            TrainingLogService.getSingleTrainingLog(username, currentWorkoutId, request, new IAPITrainingLogCallback() {
                @Override
                public void OnSuccess(ITrainingLog trainingLog) {
                    currentTrainingLog = trainingLog;

                    trainingLogForm.setEntryNameField((trainingLog.getLog_Name() == null ? "" : trainingLog.getLog_Name()));
                    trainingLogForm.setEntryDateField(trainingLog.getLog_Date());
                    trainingLogForm.setEntryTimeField((trainingLog.getLog_Time() == null ? "" : trainingLog.getLog_Time()));
                    trainingLogForm.setEntryDescriptionField((trainingLog.getLog_Description() == null ? "" : trainingLog.getLog_Description()));
                    trainingLogForm.setEntryTypeField(trainingLog.getType_ID());

                    trainingLogForm.setPlannedDurationField((trainingLog.getDuration_Planned() == null ? "" : trainingLog.getDuration_Planned()));
                    trainingLogForm.setPlannedDistanceField((trainingLog.getDistance_Planned() == null ? "" : trainingLog.getDistance_Planned().toString()));
                    trainingLogForm.setPlannedDistanceUnitField(trainingLog.getDistance_Unit());
                    trainingLogForm.setPlannedRestingHrField((trainingLog.getHR_Resting_Planned() == null ? "" : trainingLog.getHR_Resting_Planned().toString()));
                    trainingLogForm.setPlannedAvgHrField((trainingLog.getHR_Avg_Planned() == null ? "" : trainingLog.getHR_Avg_Planned().toString()));
                    trainingLogForm.setPlannedMaxHrField((trainingLog.getHR_Max_Planned() == null ? "" : trainingLog.getHR_Max_Planned().toString()));
                    trainingLogForm.setPlannedAvgWattsField((trainingLog.getWatts_Avg_Planned() == null ? "" : trainingLog.getWatts_Avg_Planned().toString()));
                    trainingLogForm.setPlannedMaxWattsField((trainingLog.getWatts_Max_Planned() == null ? "" : trainingLog.getWatts_Max_Planned().toString()));
                    trainingLogForm.setPlannedRPEField((trainingLog.getRPE_Planned() == null ? "" : trainingLog.getRPE_Planned().toString()));

                    trainingLogForm.setActualDurationField((trainingLog.getDuration_Actual() == null ? "" : trainingLog.getDuration_Actual()));
                    trainingLogForm.setActualDistanceField((trainingLog.getDistance_Actual() == null ? "" : trainingLog.getDistance_Actual().toString()));
                    trainingLogForm.setActualDistanceUnitField(trainingLog.getDistance_Unit());
                    trainingLogForm.setActualRestingHrField((trainingLog.getHR_Resting_Actual() == null ? "" : trainingLog.getHR_Resting_Actual().toString()));
                    trainingLogForm.setActualAvgHrField((trainingLog.getHR_Avg_Actual() == null ? "" : trainingLog.getHR_Avg_Actual().toString()));
                    trainingLogForm.setActualMaxHrField((trainingLog.getHR_Max_Actual() == null ? "" : trainingLog.getHR_Max_Actual().toString()));
                    trainingLogForm.setActualAvgWattsField((trainingLog.getWatts_Avg_Actual() == null ? "" : trainingLog.getWatts_Avg_Actual().toString()));
                    trainingLogForm.setActualMaxWattsField((trainingLog.getWatts_Max_Actual() == null ? "" : trainingLog.getWatts_Max_Actual().toString()));
                    trainingLogForm.setActualRPEField((trainingLog.getRPE_Actual() == null ? "" : trainingLog.getRPE_Actual().toString()));

                    trainingLogForm.setCaloriesBurnedField((trainingLog.getCalories_Burned() == null ? "" : trainingLog.getCalories_Burned().toString()));
                    trainingLogForm.setHrZone1Field((trainingLog.getHR_Zone1_Time() == null ? "" : trainingLog.getHR_Zone1_Time()));
                    trainingLogForm.setHrZone2Field((trainingLog.getHR_Zone2_Time() == null ? "" : trainingLog.getHR_Zone2_Time()));
                    trainingLogForm.setHrZone3Field((trainingLog.getHR_Zone3_Time() == null ? "" : trainingLog.getHR_Zone3_Time()));
                    trainingLogForm.setHrZone4Field((trainingLog.getHR_Zone4_Time() == null ? "" : trainingLog.getHR_Zone5_Time()));
                    trainingLogForm.setHrZone5Field((trainingLog.getHR_Zone5_Time() == null ? "" : trainingLog.getHR_Zone5_Time()));
                    trainingLogForm.setHrZone6Field((trainingLog.getHR_Zone6_Time() == null ? "" : trainingLog.getHR_Zone6_Time()));
                    trainingLogForm.setSleepQualityField((trainingLog.getSleep_Quality() == null ? 0 : trainingLog.getSleep_Quality()));

                    trainingLogForm.setAthletesCommentsField((trainingLog.getAthletes_Comments() == null ? "" : trainingLog.getAthletes_Comments()));
                    trainingLogForm.setCoachesCommentsField((trainingLog.getCoaches_Comments() == null ? "" : trainingLog.getCoaches_Comments()));
                    trainingLogForm.setWorkoutCommentsField((trainingLog.getWorkout_Comments() == null ? "" : trainingLog.getWorkout_Comments()));

                }

                @Override
                public void OnError(String errorMessage) {
                    AlertService.LongAlert(currentActivity, errorMessage);
                }
            });
        }
    }
}
