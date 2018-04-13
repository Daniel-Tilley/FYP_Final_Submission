package mobile_app.trainingpal.me.shared;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

import mobile_app.trainingpal.me.R;
import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.models.TrainingLog;

public class TrainingLogForm {

    private Activity currentActivity;

    private TextView pageTitle;

    private ScrollView infoContainer;
    private ScrollView dataContainer;
    private ScrollView extrasContainer;

    private BottomNavigationView bottomNavigationView;

    private String selectedType;
    private String[] entryTypeKeys;
    private String[] entryTypeValues;

    private String selectedSleepQuality;
    private String[] sleepQualityKeys;
    private String[] sleepQualityValues;

    private String selectedDistanceUnit;
    private String[] distanceUnits;

    private final String distanceRegex = "[0-9]{0,4}+([.][0-9]{1,2})?";
    private final String rpeRegex = "[1-9]|10";

    private Button finalButton;

    private boolean formHasNoErrors;

    // region Label variables
    private TextView entryNameLabel;
    private TextView entryDateLabel;
    private TextView entryTimeLabel;
    private TextView entryDescriptionLabel;
    private TextView entryTypeLabel;

    private TextView plannedDurationLabel;
    private TextView plannedDistanceLabel;
    private TextView plannedRestingHrLabel;
    private TextView plannedAvgHrLabel;
    private TextView plannedMaxHrLabel;
    private TextView plannedAvgWattsLabel;
    private TextView plannedMaxWattsLabel;
    private TextView plannedRPELabel;

    private TextView actualDurationLabel;
    private TextView actualDistanceLabel;
    private TextView actualRestingHrLabel;
    private TextView actualAvgHrLabel;
    private TextView actualMaxHrLabel;
    private TextView actualAvgWattsLabel;
    private TextView actualMaxWattsLabel;
    private TextView actualRPELabel;

    private TextView caloriesBurnedLabel;
    private TextView hrZone1Label;
    private TextView hrZone2Label;
    private TextView hrZone3Label;
    private TextView hrZone4Label;
    private TextView hrZone5Label;
    private TextView hrZone6Label;
    private TextView sleepQualityLabel;

    private TextView athletesCommentsLabel;
    private TextView coachesCommentsLabel;
    private TextView workoutCommentsLabel;
    // endregion

    // region Error variables
    private TextView entryNameError;
    private TextView entryDateError;
    private TextView entryTimeError;
    private TextView entryDescriptionError;
    private TextView entryTypeError;

    private TextView plannedDurationError;
    private TextView plannedDistanceError;
    private TextView plannedRestingHrError;
    private TextView plannedAvgHrError;
    private TextView plannedMaxHrError;
    private TextView plannedAvgWattsError;
    private TextView plannedMaxWattsError;
    private TextView plannedRPEError;

    private TextView actualDurationError;
    private TextView actualDistanceError;
    private TextView actualRestingHrError;
    private TextView actualAvgHrError;
    private TextView actualMaxHrError;
    private TextView actualAvgWattsError;
    private TextView actualMaxWattsError;
    private TextView actualRPEError;

    private TextView caloriesBurnedError;
    private TextView hrZone1Error;
    private TextView hrZone2Error;
    private TextView hrZone3Error;
    private TextView hrZone4Error;
    private TextView hrZone5Error;
    private TextView hrZone6Error;
    private TextView sleepQualityError;

    private TextView athletesCommentsError;
    private TextView coachesCommentsError;
    private TextView workoutCommentsError;
    // endregion

    // region Fields variables
    private EditText entryNameField;
    private EditText entryDateField;
    private EditText entryTimeField;
    private EditText entryDescriptionField;
    private Spinner entryTypeField;

    private EditText plannedDurationField;
    private EditText plannedDistanceField;
    private Spinner  plannedDistanceUnitField;
    private EditText plannedRestingHrField;
    private EditText plannedAvgHrField;
    private EditText plannedMaxHrField;
    private EditText plannedAvgWattsField;
    private EditText plannedMaxWattsField;
    private EditText plannedRPEField;

    private EditText actualDurationField;
    private EditText actualDistanceField;
    private Spinner  actualDistanceUnitField;
    private EditText actualRestingHrField;
    private EditText actualAvgHrField;
    private EditText actualMaxHrField;
    private EditText actualAvgWattsField;
    private EditText actualMaxWattsField;
    private EditText actualRPEField;

    private EditText caloriesBurnedField;
    private EditText hrZone1Field;
    private EditText hrZone2Field;
    private EditText hrZone3Field;
    private EditText hrZone4Field;
    private EditText hrZone5Field;
    private EditText hrZone6Field;
    private Spinner sleepQualityField;

    private EditText athletesCommentsField;
    private EditText coachesCommentsField;
    private EditText workoutCommentsField;
    //endregion

    public TrainingLogForm(Activity currentActivity) {
        this.currentActivity = currentActivity;

        initialiseVariables();
    }

    // region Getters and Setters

    public Button getFinalButton() { return finalButton; }

    public void resetErrorMessages() {
        formHasNoErrors = true;

        setEntryNameError("");
        setEntryDateError("");
        setEntryTimeError("");
        setEntryDescriptionError("");
        setEntryTypeError("");

        setPlannedDurationError("");
        setPlannedDistanceError("");
        setPlannedRestingHrError("");
        setPlannedAvgHrError("");
        setPlannedMaxHrError("");
        setPlannedAvgWattsError("");
        setPlannedMaxWattsError("");
        setPlannedRPEError("");

        setActualDurationError("");
        setActualDistanceError("");
        setActualRestingHrError("");
        setActualAvgHrError("");
        setActualMaxHrError("");
        setActualAvgWattsError("");
        setActualMaxWattsError("");
        setActualRPEError("");

        setCaloriesBurnedError("");
        setHrZone1Error("");
        setHrZone2Error("");
        setHrZone3Error("");
        setHrZone4Error("");
        setHrZone5Error("");
        setHrZone6Error("");
        setSleepQualityError("");

        setAthletesCommentsError("");
        setCoachesCommentsError("");
        setWorkoutCommentsError("");
    }

    public ITrainingLog getTrainingLogObject() {
        TrainingLog trainingLog = new TrainingLog();

        trainingLog.setLog_Name(getEditTextString(getEntryNameField()));
        trainingLog.setLog_Date(getEditTextString(getEntryDateField()));
        trainingLog.setLog_Time(getEditTextString(getEntryTimeField()));
        trainingLog.setLog_Description(getEditTextString(getEntryDescriptionField()));
        trainingLog.setType_ID(Integer.parseInt(getEntryTypeField()));

        trainingLog.setDuration_Planned(getEditTextString(getPlannedDurationField()));
        trainingLog.setDistance_Planned(getEditTextFloat(getPlannedDistanceField()));
        trainingLog.setHR_Resting_Planned(getEditTextInt(getPlannedRestingHrField()));
        trainingLog.setHR_Avg_Planned(getEditTextInt(getPlannedAvgHrField()));
        trainingLog.setHR_Max_Planned(getEditTextInt(getPlannedMaxHrField()));
        trainingLog.setWatts_Avg_Planned(getEditTextInt(getPlannedAvgWattsField()));
        trainingLog.setWatts_Max_Planned(getEditTextInt(getPlannedMaxWattsField()));
        trainingLog.setRPE_Planned(getEditTextInt(getPlannedRPEField()));

        trainingLog.setDuration_Actual(getEditTextString(getActualDurationField()));
        trainingLog.setDistance_Actual(getEditTextFloat(getActualDistanceField()));
        trainingLog.setDistance_Unit(getActualDistanceUnitField());
        trainingLog.setHR_Resting_Actual(getEditTextInt(getActualRestingHrField()));
        trainingLog.setHR_Avg_Actual(getEditTextInt(getActualAvgHrField()));
        trainingLog.setHR_Max_Actual(getEditTextInt(getActualMaxHrField()));
        trainingLog.setWatts_Avg_Actual(getEditTextInt(getActualAvgWattsField()));
        trainingLog.setWatts_Max_Actual(getEditTextInt(getActualMaxWattsField()));
        trainingLog.setRPE_Actual(getEditTextInt(getActualRPEField()));

        trainingLog.setCalories_Burned(getEditTextInt(getCaloriesBurnedField()));
        trainingLog.setHR_Zone1_Time(getEditTextString(getHrZone1Field()));
        trainingLog.setHR_Zone2_Time(getEditTextString(getHrZone2Field()));
        trainingLog.setHR_Zone3_Time(getEditTextString(getHrZone3Field()));
        trainingLog.setHR_Zone4_Time(getEditTextString(getHrZone4Field()));
        trainingLog.setHR_Zone5_Time(getEditTextString(getHrZone5Field()));
        trainingLog.setHR_Zone6_Time(getEditTextString(getHrZone6Field()));
        trainingLog.setSleep_Quality(getIntFromString(getSleepQualityField()));

        trainingLog.setAthletes_Comments(getEditTextString(getAthletesCommentsField()));
        trainingLog.setCoaches_Comments(getEditTextString(getCoachesCommentsField()));
        trainingLog.setWorkout_Comments(getEditTextString(getWorkoutCommentsField()));

        return trainingLog;
    }

    // region Info Part
        // Entry Name
        public TextView getEntryNameLabel() { return entryNameLabel; }
        public void setEntryNameLabel(String entryNameLabel) { this.entryNameLabel.setText(entryNameLabel); this.entryNameLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getEntryNameError() { return entryNameError; }
        public void setEntryNameError(String entryNameError) { this.entryNameError.setText(entryNameError); }

        public EditText getEntryNameField() { return entryNameField; }
        public void setEntryNameField(String entryNameField) { this.entryNameField.setText(entryNameField); }


        // Entry Date
        public TextView getEntryDateLabel() { return entryDateLabel; }
        public void setEntryDateLabel(String entryDateLabel) { this.entryDateLabel.setText(entryDateLabel); this.entryDateLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getEntryDateError() { return entryDateError; }
        public void setEntryDateError(String entryDateError) { this.entryDateError.setText(entryDateError); }

        public EditText getEntryDateField() { return entryDateField; }
        public void setEntryDateField(String entryDateField) { this.entryDateField.setText(entryDateField); }


        // Entry Time
        public TextView getEntryTimeLabel() { return entryTimeLabel; }
        public void setEntryTimeLabel(String entryTimeLabel) { this.entryTimeLabel.setText(entryTimeLabel); this.entryTimeLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getEntryTimeError() { return entryTimeError; }
        public void setEntryTimeError(String entryTimeError) { this.entryTimeError.setText(entryTimeError); }

        public EditText getEntryTimeField() { return entryTimeField; }
        public void setEntryTimeField(String entryTimeField) { this.entryTimeField.setText(entryTimeField); }

        // Entry Description
        public TextView getEntryDescriptionLabel() { return entryDescriptionLabel; }
        public void setEntryDescriptionLabel(String entryDescriptionLabel) { this.entryDescriptionLabel.setText(entryDescriptionLabel); this.entryDescriptionLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getEntryDescriptionError() { return entryDescriptionError; }
        public void setEntryDescriptionError(String entryDescriptionError) { this.entryDescriptionError.setText(entryDescriptionError); }

        public EditText getEntryDescriptionField() { return entryDescriptionField; }
        public void setEntryDescriptionField(String entryDescriptionField) { this.entryDescriptionField.setText(entryDescriptionField); }

        // Entry Type
        public TextView getEntryTypeLabel() { return entryTypeLabel; }
        public void setEntryTypeLabel(String entryTypeLabel) { this.entryTypeLabel.setText(entryTypeLabel); this.entryTypeLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getEntryTypeError() { return entryTypeError; }
        public void setEntryTypeError(String entryTypeError) { this.entryTypeError.setText(entryTypeError); }

        public String getEntryTypeField() { return selectedType; }
        public void setEntryTypeField(Integer entryTypeField) { this.entryTypeField.setSelection(entryTypeField); }
    //endregion


    // region Data Part (Planned Values)
        // Duration
        public TextView getPlannedDurationLabel() { return plannedDurationLabel; }
        public void setPlannedDurationLabel(String plannedDurationLabel) { this.plannedDurationLabel.setText(plannedDurationLabel); this.plannedDurationLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedDurationError() { return plannedDurationError; }
        public void setPlannedDurationError(String plannedDurationError) { this.plannedDurationError.setText(plannedDurationError); }

        public EditText getPlannedDurationField() { return plannedDurationField; }
        public void setPlannedDurationField(String plannedDurationField) { this.plannedDurationField.setText(plannedDurationField); }


        // Distance
        public TextView getPlannedDistanceLabel() { return plannedDistanceLabel; }
        public void setPlannedDistanceLabel(String plannedDistanceLabel) { this.plannedDistanceLabel.setText(plannedDistanceLabel); this.plannedDistanceLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedDistanceError() { return plannedDistanceError; }
        public void setPlannedDistanceError(String plannedDistanceError) { this.plannedDistanceError.setText(plannedDistanceError); }

        public EditText getPlannedDistanceField() { return plannedDistanceField; }
        public void setPlannedDistanceField(String plannedDistanceField) { this.plannedDistanceField.setText(plannedDistanceField); }

        public String getPlannedDistanceUnitField() { return selectedDistanceUnit; }
        public void setPlannedDistanceUnitField(String plannedDistanceUnitField) { this.plannedDistanceUnitField.setSelection(Arrays.asList(distanceUnits).indexOf(plannedDistanceUnitField)); }

        // Resting Hr
        public TextView getPlannedRestingHrLabel() { return plannedRestingHrLabel; }
        public void setPlannedRestingHrLabel(String plannedRestingHrLabel) { this.plannedRestingHrLabel.setText(plannedRestingHrLabel); this.plannedRestingHrLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedRestingHrError() { return plannedRestingHrError; }
        public void setPlannedRestingHrError(String plannedRestingHrError) { this.plannedRestingHrError.setText(plannedRestingHrError); }

        public EditText getPlannedRestingHrField() { return plannedRestingHrField; }
        public void setPlannedRestingHrField(String plannedRestingHrField) { this.plannedRestingHrField.setText(plannedRestingHrField); }


        // Average Hr
        public TextView getPlannedAvgHrLabel() { return plannedAvgHrLabel; }
        public void setPlannedAvgHrLabel(String plannedAvgHrLabel) { this.plannedAvgHrLabel.setText(plannedAvgHrLabel); this.plannedAvgHrLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedAvgHrError() { return plannedAvgHrError;  }
        public void setPlannedAvgHrError(String plannedAvgHrError) { this.plannedAvgHrError.setText(plannedAvgHrError); }

        public EditText getPlannedAvgHrField() { return plannedAvgHrField;  }
        public void setPlannedAvgHrField(String plannedAvgHrField) { this.plannedAvgHrField.setText(plannedAvgHrField); }


        // Max Hr
        public TextView getPlannedMaxHrLabel() { return plannedMaxHrLabel; }
        public void setPlannedMaxHrLabel(String plannedMaxHrLabel) { this.plannedMaxHrLabel.setText(plannedMaxHrLabel); this.plannedMaxHrLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedMaxHrError() { return plannedMaxHrError; }
        public void setPlannedMaxHrError(String plannedMaxHrError) { this.plannedMaxHrError.setText(plannedMaxHrError); }

        public EditText getPlannedMaxHrField() { return plannedMaxHrField; }
        public void setPlannedMaxHrField(String plannedMaxHrField) { this.plannedMaxHrField.setText(plannedMaxHrField); }


        // Avg Watts
        public TextView getPlannedAvgWattsLabel() { return plannedAvgWattsLabel; }
        public void setPlannedAvgWattsLabel(String plannedAvgWattsLabel) { this.plannedAvgWattsLabel.setText(plannedAvgWattsLabel); this.plannedAvgWattsLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedAvgWattsError() { return plannedAvgWattsError; }
        public void setPlannedAvgWattsError(String plannedAvgWattsError) { this.plannedAvgWattsError.setText(plannedAvgWattsError); }

        public EditText getPlannedAvgWattsField() { return plannedAvgWattsField; }
        public void setPlannedAvgWattsField(String plannedAvgWattsField) { this.plannedAvgWattsField.setText(plannedAvgWattsField); }


        // Max Watts
        public TextView getPlannedMaxWattsLabel() { return plannedMaxWattsLabel; }
        public void setPlannedMaxWattsLabel(String plannedMaxWattsLabel) { this.plannedMaxWattsLabel.setText(plannedMaxWattsLabel); this.plannedMaxWattsLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedMaxWattsError() { return plannedMaxWattsError; }
        public void setPlannedMaxWattsError(String plannedMaxWattsError) { this.plannedMaxWattsError.setText(plannedMaxWattsError); }

        public EditText getPlannedMaxWattsField() { return plannedMaxWattsField; }
        public void setPlannedMaxWattsField(String plannedMaxWattsField) { this.plannedMaxWattsField.setText(plannedMaxWattsField); }


        // RPE
        public TextView getPlannedRPELabel() { return plannedRPELabel; }
        public void setPlannedRPELabel(String plannedRPELabel) { this.plannedRPELabel.setText(plannedRPELabel); this.plannedRPELabel.setTypeface(null, Typeface.BOLD); }

        public TextView getPlannedRPEError() { return plannedRPEError; }
        public void setPlannedRPEError(String plannedRPEError) { this.plannedRPEError.setText(plannedRPEError); }

        public EditText getPlannedRPEField() { return plannedRPEField; }
        public void setPlannedRPEField(String plannedRPEField) { this.plannedRPEField.setText(plannedRPEField); }
    //endregion


    // region Data Part (Actual Values)
        // Duration
        public TextView getActualDurationLabel() { return actualDurationLabel; }
        public void setActualDurationLabel(String actualDurationLabel) { this.actualDurationLabel.setText(actualDurationLabel); this.actualDurationLabel.setTypeface(null, Typeface.BOLD);}

        public TextView getActualDurationError() { return actualDurationError; }
        public void setActualDurationError(String actualDurationError) { this.actualDurationError.setText(actualDurationError); }

        public EditText getActualDurationField() { return actualDurationField; }
        public void setActualDurationField(String actualDurationField) { this.actualDurationField.setText(actualDurationField); }


        // Distance
        public TextView getActualDistanceLabel() { return actualDistanceLabel; }
        public void setActualDistanceLabel(String actualDistanceLabel) { this.actualDistanceLabel.setText(actualDistanceLabel); this.actualDistanceLabel.setTypeface(null, Typeface.BOLD);}

        public TextView getActualDistanceError() { return actualDistanceError; }
        public void setActualDistanceError(String actualDistanceError) { this.actualDistanceError.setText(actualDistanceError); }

        public EditText getActualDistanceField() { return actualDistanceField; }
        public void setActualDistanceField(String actualDistanceField) { this.actualDistanceField.setText(actualDistanceField); }

        public String getActualDistanceUnitField() { return selectedDistanceUnit; }
        public void setActualDistanceUnitField(String actualDistanceUnitField) { this.actualDistanceUnitField.setSelection(Arrays.asList(distanceUnits).indexOf(actualDistanceUnitField)); }

        // Resting Hr
        public TextView getActualRestingHrLabel() { return actualRestingHrLabel; }
        public void setActualRestingHrLabel(String actualRestingHrLabel) { this.actualRestingHrLabel.setText(actualRestingHrLabel); this.actualRestingHrLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getActualRestingHrError() { return actualRestingHrError; }
        public void setActualRestingHrError(String actualRestingHrError) { this.actualRestingHrError.setText(actualRestingHrError); }

        public EditText getActualRestingHrField() { return actualRestingHrField; }
        public void setActualRestingHrField(String actualRestingHrField) { this.actualRestingHrField.setText(actualRestingHrField); }


        // Average Hr
        public TextView getActualAvgHrLabel() { return actualAvgHrLabel; }
        public void setActualAvgHrLabel(String actualAvgHrLabel) { this.actualAvgHrLabel.setText(actualAvgHrLabel); this.actualAvgHrLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getActualAvgHrError() { return actualAvgHrError;  }
        public void setActualAvgHrError(String actualAvgHrError) { this.actualAvgHrError.setText(actualAvgHrError); }

        public EditText getActualAvgHrField() { return actualAvgHrField;  }
        public void setActualAvgHrField(String actualAvgHrField) { this.actualAvgHrField.setText(actualAvgHrField); }


        // Max Hr
        public TextView getActualMaxHrLabel() { return actualMaxHrLabel; }
        public void setActualMaxHrLabel(String actualMaxHrLabel) { this.actualMaxHrLabel.setText(actualMaxHrLabel); this.actualMaxHrLabel.setTypeface(null, Typeface.BOLD);}

        public TextView getActualMaxHrError() { return actualMaxHrError; }
        public void setActualMaxHrError(String actualMaxHrError) { this.actualMaxHrError.setText(actualMaxHrError); }

        public EditText getActualMaxHrField() { return actualMaxHrField; }
        public void setActualMaxHrField(String actualMaxHrField) { this.actualMaxHrField.setText(actualMaxHrField); }


        // Avg Watts
        public TextView getActualAvgWattsLabel() { return actualAvgWattsLabel; }
        public void setActualAvgWattsLabel(String actualAvgWattsLabel) { this.actualAvgWattsLabel.setText(actualAvgWattsLabel); this.actualAvgWattsLabel.setTypeface(null, Typeface.BOLD);}

        public TextView getActualAvgWattsError() { return actualAvgWattsError; }
        public void setActualAvgWattsError(String actualAvgWattsError) { this.actualAvgWattsError.setText(actualAvgWattsError); }

        public EditText getActualAvgWattsField() { return actualAvgWattsField; }
        public void setActualAvgWattsField(String actualAvgWattsField) { this.actualAvgWattsField.setText(actualAvgWattsField); }


        // Max Watts
        public TextView getActualMaxWattsLabel() { return actualMaxWattsLabel; }
        public void setActualMaxWattsLabel(String actualMaxWattsLabel) { this.actualMaxWattsLabel.setText(actualMaxWattsLabel); this.actualMaxWattsLabel.setTypeface(null, Typeface.BOLD);}

        public TextView getActualMaxWattsError() { return actualMaxWattsError; }
        public void setActualMaxWattsError(String actualMaxWattsError) { this.actualMaxWattsError.setText(actualMaxWattsError); }

        public EditText getActualMaxWattsField() { return actualMaxWattsField; }
        public void setActualMaxWattsField(String actualMaxWattsField) { this.actualMaxWattsField.setText(actualMaxWattsField); }


        // RPE
        public TextView getActualRPELabel() { return actualRPELabel; }
        public void setActualRPELabel(String actualRPELabel) { this.actualRPELabel.setText(actualRPELabel); this.actualRPELabel.setTypeface(null, Typeface.BOLD);}

        public TextView getActualRPEError() { return actualRPEError; }
        public void setActualRPEError(String actualRPEError) { this.actualRPEError.setText(actualRPEError); }

        public EditText getActualRPEField() { return actualRPEField; }
        public void setActualRPEField(String actualRPEField) { this.actualRPEField.setText(actualRPEField); }
    //endregion


    // region Data Part (Additional Values)

        // Calories
        public TextView getCaloriesBurnedLabel() { return caloriesBurnedLabel; }
        public void setCaloriesBurnedLabel(String caloriesBurnedLabel) { this.caloriesBurnedLabel.setText(caloriesBurnedLabel); this.caloriesBurnedLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getCaloriesBurnedError() { return caloriesBurnedError; }
        public void setCaloriesBurnedError(String caloriesBurnedError) { this.caloriesBurnedError.setText(caloriesBurnedError); }

        public EditText getCaloriesBurnedField() { return caloriesBurnedField; }
        public void setCaloriesBurnedField(String caloriesBurnedField) { this.caloriesBurnedField.setText(caloriesBurnedField); }


        // Hr Zone 1
        public TextView getHrZone1Label() { return hrZone1Label; }
        public void setHrZone1Label(String hrZone1Label) { this.hrZone1Label.setText(hrZone1Label); this.hrZone1Label.setTypeface(null, Typeface.BOLD); }

        public TextView getHrZone1Error() { return hrZone1Error; }
        public void setHrZone1Error(String hrZone1Error) { this.hrZone1Error.setText(hrZone1Error); }

        public EditText getHrZone1Field() { return hrZone1Field; }
        public void setHrZone1Field(String hrZone1Field) { this.hrZone1Field.setText(hrZone1Field); }


        // Hr Zone 2
        public TextView getHrZone2Label() { return hrZone2Label; }
        public void setHrZone2Label(String hrZone2Label) { this.hrZone2Label.setText(hrZone2Label); this.hrZone2Label.setTypeface(null, Typeface.BOLD); }

        public TextView getHrZone2Error() { return hrZone2Error; }
        public void setHrZone2Error(String hrZone2Error) { this.hrZone2Error.setText(hrZone2Error); }

        public EditText getHrZone2Field() { return hrZone2Field; }
        public void setHrZone2Field(String hrZone2Field) { this.hrZone2Field.setText(hrZone2Field); }


        // Hr Zone 3
        public TextView getHrZone3Label() { return hrZone3Label; }
        public void setHrZone3Label(String hrZone3Label) { this.hrZone3Label.setText(hrZone3Label); this.hrZone3Label.setTypeface(null, Typeface.BOLD); }

        public TextView getHrZone3Error() { return hrZone3Error; }
        public void setHrZone3Error(String hrZone3Error) { this.hrZone3Error.setText(hrZone3Error); }

        public EditText getHrZone3Field() { return hrZone3Field; }
        public void setHrZone3Field(String hrZone3Field) { this.hrZone3Field.setText(hrZone3Field); }


        // Hr Zone 4
        public TextView getHrZone4Label() { return hrZone4Label; }
        public void setHrZone4Label(String hrZone4Label) { this.hrZone4Label.setText(hrZone4Label); this.hrZone4Label.setTypeface(null, Typeface.BOLD); }

        public TextView getHrZone4Error() { return hrZone4Error; }
        public void setHrZone4Error(String hrZone4Error) { this.hrZone4Error.setText(hrZone4Error); }

        public EditText getHrZone4Field() { return hrZone4Field; }
        public void setHrZone4Field(String hrZone4Field) { this.hrZone4Field.setText(hrZone4Field); }


        // Hr Zone 5
        public TextView getHrZone5Label() { return hrZone5Label; }
        public void setHrZone5Label(String hrZone5Label) { this.hrZone5Label.setText(hrZone5Label); this.hrZone5Label.setTypeface(null, Typeface.BOLD); }

        public TextView getHrZone5Error() { return hrZone5Error; }
        public void setHrZone5Error(String hrZone5Error) { this.hrZone5Error.setText(hrZone5Error); }

        public EditText getHrZone5Field() { return hrZone5Field; }
        public void setHrZone5Field(String hrZone5Field) { this.hrZone5Field.setText(hrZone5Field); }


        // Hr Zone 6
        public TextView getHrZone6Label() { return hrZone6Label; }
        public void setHrZone6Label(String hrZone6Label) { this.hrZone6Label.setText(hrZone6Label); this.hrZone6Label.setTypeface(null, Typeface.BOLD); }

        public TextView getHrZone6Error() { return hrZone6Error; }
        public void setHrZone6Error(String hrZone6Error) { this.hrZone6Error.setText(hrZone6Error); }

        public EditText getHrZone6Field() { return hrZone6Field; }
        public void setHrZone6Field(String hrZone6Field) { this.hrZone6Field.setText(hrZone6Field); }


        // Sleep Quality
        public TextView getSleepQualityLabel() { return sleepQualityLabel; }
        public void setSleepQualityLabel(String sleepQualityLabel) { this.sleepQualityLabel.setText(sleepQualityLabel); this.sleepQualityLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getSleepQualityError() { return sleepQualityError; }
        public void setSleepQualityError(String sleepQualityError) { this.sleepQualityError.setText(sleepQualityError); }

        public String getSleepQualityField() { return selectedSleepQuality; }
        public void setSleepQualityField(Integer sleepQualityField) { this.sleepQualityField.setSelection(sleepQualityField); }
    // endregion


    // region Comments Part

        // Athletes Comments
        public TextView getAthletesCommentsLabel() { return athletesCommentsLabel; }
        public void setAthletesCommentsLabel(String athletesCommentsLabel) { this.athletesCommentsLabel.setText(athletesCommentsLabel); this.athletesCommentsLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getAthletesCommentsError() { return athletesCommentsError; }
        public void setAthletesCommentsError(String athletesCommentsError) { this.athletesCommentsError.setText(athletesCommentsError); }

        public EditText getAthletesCommentsField() { return athletesCommentsField; }
        public void setAthletesCommentsField(String athletesCommentsField) { this.athletesCommentsField.setText(athletesCommentsField); }

        // Coaches Comments
        public TextView getCoachesCommentsLabel() { return coachesCommentsLabel; }
        public void setCoachesCommentsLabel(String coachesCommentsLabel) { this.coachesCommentsLabel.setText(coachesCommentsLabel); this.coachesCommentsLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getCoachesCommentsError() { return coachesCommentsError; }
        public void setCoachesCommentsError(String coachesCommentsError) { this.coachesCommentsError.setText(coachesCommentsError); }

        public EditText getCoachesCommentsField() { return coachesCommentsField; }
        public void setCoachesCommentsField(String coachesCommentsField) { this.coachesCommentsField.setText(coachesCommentsField); }


        // Workout Comments
        public TextView getWorkoutCommentsLabel() { return workoutCommentsLabel; }
        public void setWorkoutCommentsLabel(String workoutCommentsLabel) { this.workoutCommentsLabel.setText(workoutCommentsLabel); this.workoutCommentsLabel.setTypeface(null, Typeface.BOLD); }

        public TextView getWorkoutCommentsError() { return workoutCommentsError; }
        public void setWorkoutCommentsError(String workoutCommentsError) { this.workoutCommentsError.setText(workoutCommentsError); }

        public EditText getWorkoutCommentsField() { return workoutCommentsField; }
        public void setWorkoutCommentsField(String workoutCommentsField) { this.workoutCommentsField.setText(workoutCommentsField); }
    // endregion

    //endregion

    // region Error Validation
        public boolean checkAllFields() {

            resetErrorMessages();

            if( !checkEntryName()) { formHasNoErrors = false; }
            if( !checkEntryDate()) { formHasNoErrors = false; }
            if( !checkEntryTime()) { formHasNoErrors = false; }
            if( !checkEntryType()) { formHasNoErrors = false; }

            if( !checkPlannedDuration()) { formHasNoErrors = false; }
            if( !checkPlannedDistance()) { formHasNoErrors = false; }
            if( !checkPlannedRestingHr()) { formHasNoErrors = false; }
            if( !checkPlannedAvgHr()) { formHasNoErrors = false; }
            if( !checkPlannedMaxHr()) { formHasNoErrors = false; }
            if( !checkPlannedAvgWatts()) { formHasNoErrors = false; }
            if( !checkPlannedMaxWatts()) { formHasNoErrors = false; }
            if( !checkPlannedRPE()) { formHasNoErrors = false; }

            if( !checkActualDuration()) { formHasNoErrors = false; }
            if( !checkActualDistance()) { formHasNoErrors = false; }
            if( !checkActualRestingHr()) { formHasNoErrors = false; }
            if( !checkActualAvgHr()) { formHasNoErrors = false; }
            if( !checkActualMaxHr()) { formHasNoErrors = false; }
            if( !checkActualAvgWatts()) { formHasNoErrors = false; }
            if( !checkActualMaxWatts()) { formHasNoErrors = false; }
            if( !checkActualRPE()) { formHasNoErrors = false; }

            if( !checkCaloriesBurned()) { formHasNoErrors = false; }
            if( !checkHRZone1()) { formHasNoErrors = false; }
            if( !checkHRZone2()) { formHasNoErrors = false; }
            if( !checkHRZone3()) { formHasNoErrors = false; }
            if( !checkHRZone4()) { formHasNoErrors = false; }
            if( !checkHRZone5()) { formHasNoErrors = false; }
            if( !checkHRZone6()) { formHasNoErrors = false; }
            if( !checkSleepQuality()) { formHasNoErrors = false; }

            if( !checkAthletesComments()) { formHasNoErrors = false; }
            if( !checkCoachesComments()) { formHasNoErrors = false; }
            if( !checkWorkoutComments()) { formHasNoErrors = false; }

            return formHasNoErrors;
        }

        // region The Info
        public boolean checkEntryName() {
            return true;
        }

        public boolean checkEntryDate() {
            if (entryDateField.getText().toString().equals("")) {
                entryDateError.setText(R.string.app_form_required_error);
                return false;
            } else {
                return true;
            }
        }

        public boolean checkEntryTime() {
            return true;
        }

        public boolean checkEntryDescription() {
            return true;
        }

        public boolean checkEntryType() {
            if (selectedType.equals("") || selectedType == null) {
                entryTypeError.setText(R.string.app_form_required_error);
                return false;
            } else {
                return true;
            }
        }
        // endregion

        // region The Data (Planned Values)
        public boolean checkPlannedDuration() {
            return true;
        }

        public boolean checkPlannedDistance() {
            if (!plannedDistanceField.getText().toString().equals("")) {
                if (plannedDistanceField.getText().toString().matches(distanceRegex)) {
                    return true;
                } else {
                    setPlannedDistanceError("* Must be two decimal places! *");
                    return false;
                }
            } else {
                return true;
            }
        }

        public boolean checkPlannedRestingHr() {
            return true;
        }

        public boolean checkPlannedAvgHr() {
            return true;
        }

        public boolean checkPlannedMaxHr() {
            return true;
        }

        public boolean checkPlannedAvgWatts() {
            return true;
        }

        public boolean checkPlannedMaxWatts() {
            return true;
        }

        public boolean checkPlannedRPE() {
            if (!plannedRPEField.getText().toString().equals("")) {
                if (plannedRPEField.getText().toString().matches(rpeRegex)) {
                    return true;
                } else {
                    setPlannedRPEError("* Must be between 1 and 10! *");
                    return false;
                }
            } else {
                return true;
            }
        }
        // endregion

        // region The Data (Actual Values)
        public boolean checkActualDuration() {
            return true;
        }

        public boolean checkActualDistance() {
            if (!actualDistanceField.getText().toString().equals("")) {
                if (actualDistanceField.getText().toString().matches(distanceRegex)) {
                    return true;
                } else {
                    setActualDistanceError("* Must be two decimal places! *");
                    return false;
                }
            } else {
                return true;
            }
        }

        public boolean checkActualRestingHr() {
            return true;
        }

        public boolean checkActualAvgHr() {
            return true;
        }

        public boolean checkActualMaxHr() {
            return true;
        }

        public boolean checkActualAvgWatts() {
            return true;
        }

        public boolean checkActualMaxWatts() {
            return true;
        }

        public boolean checkActualRPE() {
            if (!actualRPEField.getText().toString().equals("")) {
                if (actualRPEField.getText().toString().matches(rpeRegex)) {
                    return true;
                } else {
                    setActualRPEError("* Must be between 1 and 10! *");
                    return false;
                }
            } else {
                return true;
            }
        }
        // endregion

        // region The Data (Additional Values)
        public boolean checkCaloriesBurned() {
            return true;
        }

        public boolean checkHRZone1() {
            return true;
        }

        public boolean checkHRZone2() {
            return true;
        }

        public boolean checkHRZone3() {
            return true;
        }

        public boolean checkHRZone4() {
            return true;
        }

        public boolean checkHRZone5() {
            return true;
        }

        public boolean checkHRZone6() {
            return true;
        }

        public boolean checkSleepQuality() {
            return true;
        }
        // endregion

        // region The Comments
        public boolean checkAthletesComments() {
            return true;
        }

        public boolean checkCoachesComments() {
            return true;
        }

        public boolean checkWorkoutComments() {
            return true;
        }
        // endregion
    // endregion

    // variable initialisation
    private void initialiseVariables() {
        formHasNoErrors = true;
        pageTitle = currentActivity.findViewById(R.id.training_log_form_page_name);

        infoContainer = currentActivity.findViewById(R.id.training_log_form_info_container);
        dataContainer = currentActivity.findViewById(R.id.training_log_form_data_container);
        extrasContainer = currentActivity.findViewById(R.id.training_log_form_extras_container);

        bottomNavigationView = currentActivity.findViewById(R.id.training_log_form_bottom_navigation);

        finalButton = currentActivity.findViewById(R.id.training_log_form_final_button);

        final Resources resources = currentActivity.getResources();
        entryTypeKeys = resources.getStringArray(R.array.shared_array_training_log_type_keys);
        entryTypeValues = resources.getStringArray(R.array.shared_array_training_log_type_values);
        sleepQualityKeys = resources.getStringArray(R.array.shared_array_training_log_sleep_quality_keys);
        sleepQualityValues = resources.getStringArray(R.array.shared_array_training_log_sleep_quality_values);
        distanceUnits = resources.getStringArray(R.array.shared_array_training_log_distance_units);

        selectedType = "";
        selectedSleepQuality = "";
        selectedDistanceUnit = distanceUnits[0];

        // = currentActivity.findViewById(R.id.)
        initialiseFormLabels();
        initialiseFormErrors();
        initialiseFormFields();
        initialiseListenersAndAdapters();
    }

    private void initialiseFormLabels() {
        entryNameLabel = currentActivity.findViewById(R.id.training_log_form_name_label);
        entryDateLabel = currentActivity.findViewById(R.id.training_log_form_date_label);
        entryTimeLabel = currentActivity.findViewById(R.id.training_log_form_time_label);
        entryDescriptionError = currentActivity.findViewById(R.id.training_log_form_description_error);
        entryTypeLabel = currentActivity.findViewById(R.id.training_log_form_type_label);

        plannedDurationLabel = currentActivity.findViewById(R.id.training_log_form_planned_duration_label);
        plannedDistanceLabel = currentActivity.findViewById(R.id.training_log_form_planned_distance_label);
        plannedRestingHrLabel = currentActivity.findViewById(R.id.training_log_form_planned_resting_hr_label);
        plannedAvgHrLabel = currentActivity.findViewById(R.id.training_log_form_planned_avg_hr_label);
        plannedMaxHrLabel = currentActivity.findViewById(R.id.training_log_form_planned_max_hr_label);
        plannedAvgWattsLabel = currentActivity.findViewById(R.id.training_log_form_planned_avg_watts_label);
        plannedMaxWattsLabel = currentActivity.findViewById(R.id.training_log_form_planned_max_watts_label);
        plannedRPELabel = currentActivity.findViewById(R.id.training_log_form_planned_rpe_label);

        actualDurationLabel = currentActivity.findViewById(R.id.training_log_form_actual_duration_label);
        actualDistanceLabel = currentActivity.findViewById(R.id.training_log_form_actual_distance_label);
        actualRestingHrLabel = currentActivity.findViewById(R.id.training_log_form_actual_resting_hr_label);
        actualAvgHrLabel = currentActivity.findViewById(R.id.training_log_form_actual_avg_hr_label);
        actualMaxHrLabel = currentActivity.findViewById(R.id.training_log_form_actual_max_hr_label);
        actualAvgWattsLabel = currentActivity.findViewById(R.id.training_log_form_actual_avg_watts_label);
        actualMaxWattsLabel = currentActivity.findViewById(R.id.training_log_form_actual_max_watts_label);
        actualRPELabel = currentActivity.findViewById(R.id.training_log_form_actual_rpe_label);

        caloriesBurnedLabel = currentActivity.findViewById(R.id.training_log_form_calories_burned_label);
        hrZone1Label = currentActivity.findViewById(R.id.training_log_form_hr_zone_1_label);
        hrZone2Label = currentActivity.findViewById(R.id.training_log_form_hr_zone_2_label);
        hrZone3Label = currentActivity.findViewById(R.id.training_log_form_hr_zone_3_label);
        hrZone4Label = currentActivity.findViewById(R.id.training_log_form_hr_zone_4_label);
        hrZone5Label = currentActivity.findViewById(R.id.training_log_form_hr_zone_5_label);
        hrZone6Label = currentActivity.findViewById(R.id.training_log_form_hr_zone_6_label);
        sleepQualityLabel = currentActivity.findViewById(R.id.training_log_form_sleep_quality_label);

        athletesCommentsLabel = currentActivity.findViewById(R.id.training_log_form_athletes_comments_label);
        coachesCommentsLabel = currentActivity.findViewById(R.id.training_log_form_coachs_comments_label);
        workoutCommentsLabel = currentActivity.findViewById(R.id.training_log_form_workout_comments_label);
    }

    private void initialiseFormErrors() {
        entryNameError = currentActivity.findViewById(R.id.training_log_form_name_error);
        entryDateError = currentActivity.findViewById(R.id.training_log_form_date_error);
        entryTimeError = currentActivity.findViewById(R.id.training_log_form_time_error);
        entryDescriptionLabel = currentActivity.findViewById(R.id.training_log_form_description_label);
        entryTypeError = currentActivity.findViewById(R.id.training_log_form_type_error);

        plannedDurationError = currentActivity.findViewById(R.id.training_log_form_planned_duration_error);
        plannedDistanceError = currentActivity.findViewById(R.id.training_log_form_planned_distance_error);
        plannedRestingHrError = currentActivity.findViewById(R.id.training_log_form_planned_resting_hr_error);
        plannedAvgHrError = currentActivity.findViewById(R.id.training_log_form_planned_avg_hr_error);
        plannedMaxHrError = currentActivity.findViewById(R.id.training_log_form_planned_max_hr_error);
        plannedAvgWattsError = currentActivity.findViewById(R.id.training_log_form_planned_avg_watts_error);
        plannedMaxWattsError = currentActivity.findViewById(R.id.training_log_form_planned_max_watts_error);
        plannedRPEError = currentActivity.findViewById(R.id.training_log_form_planned_rpe_error);

        actualDurationError = currentActivity.findViewById(R.id.training_log_form_actual_duration_error);
        actualDistanceError = currentActivity.findViewById(R.id.training_log_form_actual_distance_error);
        actualRestingHrError = currentActivity.findViewById(R.id.training_log_form_actual_resting_hr_error);
        actualAvgHrError = currentActivity.findViewById(R.id.training_log_form_actual_avg_hr_error);
        actualMaxHrError = currentActivity.findViewById(R.id.training_log_form_actual_max_hr_error);
        actualAvgWattsError = currentActivity.findViewById(R.id.training_log_form_actual_avg_watts_error);
        actualMaxWattsError = currentActivity.findViewById(R.id.training_log_form_actual_max_watts_error);
        actualRPEError = currentActivity.findViewById(R.id.training_log_form_actual_rpe_error);

        caloriesBurnedError = currentActivity.findViewById(R.id.training_log_form_calories_burned_error);
        hrZone1Error = currentActivity.findViewById(R.id.training_log_form_hr_zone_1_error);
        hrZone2Error = currentActivity.findViewById(R.id.training_log_form_hr_zone_2_error);
        hrZone3Error = currentActivity.findViewById(R.id.training_log_form_hr_zone_3_error);
        hrZone4Error = currentActivity.findViewById(R.id.training_log_form_hr_zone_4_error);
        hrZone5Error = currentActivity.findViewById(R.id.training_log_form_hr_zone_5_error);
        hrZone6Error = currentActivity.findViewById(R.id.training_log_form_hr_zone_6_error);
        sleepQualityError = currentActivity.findViewById(R.id.training_log_form_sleep_quality_error);

        athletesCommentsError = currentActivity.findViewById(R.id.training_log_form_athletes_comments_error);
        coachesCommentsError = currentActivity.findViewById(R.id.training_log_form_coachs_comments_error);
        workoutCommentsError = currentActivity.findViewById(R.id.training_log_form_workout_comments_error);
    }

    private void initialiseFormFields() {
        entryNameField = currentActivity.findViewById(R.id.training_log_form_name_field);
        entryDateField = currentActivity.findViewById(R.id.training_log_form_date_field);
        CustomDatePicker entryDatePicker = new CustomDatePicker(entryDateField, currentActivity, false);
        entryTimeField = currentActivity.findViewById(R.id.training_log_form_time_field);
        CustomTimePicker entryTimePicker = new CustomTimePicker(entryTimeField, currentActivity);
        entryDescriptionField = currentActivity.findViewById(R.id.training_log_form_description_field);
        entryTypeField = currentActivity.findViewById(R.id.training_log_form_type_field);

        plannedDurationField = currentActivity.findViewById(R.id.training_log_form_planned_duration_field);
        CustomTimeSpinnerPicker plannedDurationTimePicker = new CustomTimeSpinnerPicker(plannedDurationField, currentActivity);
        plannedDistanceField = currentActivity.findViewById(R.id.training_log_form_planned_distance_field);
        plannedDistanceUnitField = currentActivity.findViewById(R.id.training_log_form_planned_distance_unit);
        plannedRestingHrField = currentActivity.findViewById(R.id.training_log_form_planned_resting_hr_field);
        plannedAvgHrField = currentActivity.findViewById(R.id.training_log_form_planned_avg_hr_field);
        plannedMaxHrField = currentActivity.findViewById(R.id.training_log_form_planned_max_hr_field);
        plannedAvgWattsField = currentActivity.findViewById(R.id.training_log_form_planned_avg_watts_field);
        plannedMaxWattsField = currentActivity.findViewById(R.id.training_log_form_planned_max_watts_field);
        plannedRPEField = currentActivity.findViewById(R.id.training_log_form_planned_rpe_field);

        actualDurationField = currentActivity.findViewById(R.id.training_log_form_actual_duration_field);
        CustomTimeSpinnerPicker actualDurationTimePicker = new CustomTimeSpinnerPicker(actualDurationField, currentActivity);
        actualDistanceField = currentActivity.findViewById(R.id.training_log_form_actual_distance_field);
        actualDistanceUnitField = currentActivity.findViewById(R.id.training_log_form_actual_distance_unit);
        actualRestingHrField = currentActivity.findViewById(R.id.training_log_form_actual_resting_hr_field);
        actualAvgHrField = currentActivity.findViewById(R.id.training_log_form_actual_avg_hr_field);
        actualMaxHrField = currentActivity.findViewById(R.id.training_log_form_actual_max_hr_field);
        actualAvgWattsField = currentActivity.findViewById(R.id.training_log_form_actual_avg_watts_field);
        actualMaxWattsField = currentActivity.findViewById(R.id.training_log_form_actual_max_watts_field);
        actualRPEField = currentActivity.findViewById(R.id.training_log_form_actual_rpe_field);

        caloriesBurnedField = currentActivity.findViewById(R.id.training_log_form_calories_burned_field);
        hrZone1Field = currentActivity.findViewById(R.id.training_log_form_hr_zone_1_field);
        CustomTimeSpinnerPicker hrZone1TimePicker = new CustomTimeSpinnerPicker(hrZone1Field, currentActivity);
        hrZone2Field = currentActivity.findViewById(R.id.training_log_form_hr_zone_2_field);
        CustomTimeSpinnerPicker hrZone2TimePicker = new CustomTimeSpinnerPicker(hrZone2Field, currentActivity);
        hrZone3Field = currentActivity.findViewById(R.id.training_log_form_hr_zone_3_field);
        CustomTimeSpinnerPicker hrZone3TimePicker = new CustomTimeSpinnerPicker(hrZone3Field, currentActivity);
        hrZone4Field = currentActivity.findViewById(R.id.training_log_form_hr_zone_4_field);
        CustomTimeSpinnerPicker hrZone4TimePicker = new CustomTimeSpinnerPicker(hrZone4Field, currentActivity);
        hrZone5Field = currentActivity.findViewById(R.id.training_log_form_hr_zone_5_field);
        CustomTimeSpinnerPicker hrZone5TimePicker = new CustomTimeSpinnerPicker(hrZone5Field, currentActivity);
        hrZone6Field = currentActivity.findViewById(R.id.training_log_form_hr_zone_6_field);
        CustomTimeSpinnerPicker hrZone6TimePicker = new CustomTimeSpinnerPicker(hrZone6Field, currentActivity);
        sleepQualityField = currentActivity.findViewById(R.id.training_log_form_sleep_quality_field);

        athletesCommentsField = currentActivity.findViewById(R.id.training_log_form_athletes_comments_field);
        coachesCommentsField = currentActivity.findViewById(R.id.training_log_form_coachs_comments_field);
        workoutCommentsField = currentActivity.findViewById(R.id.training_log_form_workout_comments_field);
    }

    private void initialiseListenersAndAdapters() {
        final Resources resources = currentActivity.getResources();

        infoContainer.setVisibility(View.VISIBLE);
        dataContainer.setVisibility(View.GONE);
        extrasContainer.setVisibility(View.GONE);
        pageTitle.setText(R.string.training_log_form_info_page_name);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.training_log_bottom_nav_info:
                                infoContainer.setVisibility(View.VISIBLE);
                                dataContainer.setVisibility(View.GONE);
                                extrasContainer.setVisibility(View.GONE);
                                pageTitle.setText(R.string.training_log_form_info_page_name);
                                break;

                            case R.id.training_log_bottom_nav_data:
                                infoContainer.setVisibility(View.GONE);
                                dataContainer.setVisibility(View.VISIBLE);
                                extrasContainer.setVisibility(View.GONE);
                                pageTitle.setText(R.string.training_log_form_data_page_name);
                                break;

                            case R.id.training_log_bottom_nav_extras:
                                infoContainer.setVisibility(View.GONE);
                                dataContainer.setVisibility(View.GONE);
                                extrasContainer.setVisibility(View.VISIBLE);
                                pageTitle.setText(R.string.training_log_form_extras_page_name);
                                break;

                        }
                        return true;
                    }
                });

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(currentActivity, R.layout.spinner_item, entryTypeValues);
        entryTypeField.setAdapter(typeAdapter);
        entryTypeField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals(resources.getString(R.string.app_default_spinner_message))) { selectedType = ""; }
                else { selectedType = entryTypeKeys[position]; }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> distanceUnitAdapter = new ArrayAdapter<String>(currentActivity, R.layout.spinner_item, distanceUnits);
        plannedDistanceUnitField.setAdapter(distanceUnitAdapter);
        actualDistanceUnitField.setAdapter(distanceUnitAdapter);

        plannedDistanceUnitField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistanceUnit = distanceUnits[position];
                setActualDistanceUnitField(selectedDistanceUnit);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        actualDistanceUnitField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistanceUnit = distanceUnits[position];
                setPlannedDistanceUnitField(selectedDistanceUnit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> sleepQualityAdapter = new ArrayAdapter<String>(currentActivity, R.layout.spinner_item, sleepQualityValues);
        sleepQualityField.setAdapter(sleepQualityAdapter);
        sleepQualityField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (parent.getItemAtPosition(position).equals(resources.getString(R.string.app_default_spinner_message))) { selectedSleepQuality = ""; }
                else { selectedSleepQuality = sleepQualityKeys[position]; }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private String getEditTextString(EditText editText) {
        String text = editText.getText().toString();
        return text.equals("") ? null : text;
    }

    private Float getEditTextFloat(EditText editText) {
        String text = editText.getText().toString();
        return text.equals("") ? null : Float.parseFloat(text);
    }

    private Integer getEditTextInt(EditText editText) {
        String text = editText.getText().toString();
        return text.equals("") ? null : Integer.parseInt(text);
    }

    private Integer getIntFromString(String text) {
        return text.equals("") ? null : Integer.parseInt(text);
    }
}
