package mobile_app.trainingpal.me.models;

import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;

public class TrainingLog implements ITrainingLog{

    private Integer Id;
    private String Athlete_Id;
    private Integer Type_ID;
    private String Log_Date;
    private String Log_Time;
    private String Log_Name;
    private String Log_Description;
    private String Athletes_Comments;
    private String Coaches_Comments;
    private String Workout_Comments;
    private String Duration_Planned;
    private String Duration_Actual;
    private Float Distance_Planned;
    private Float Distance_Actual;
    private String Distance_Unit;
    private Integer HR_Resting_Planned;
    private Integer HR_Avg_Planned;
    private Integer HR_Max_Planned;
    private Integer HR_Resting_Actual;
    private Integer HR_Avg_Actual;
    private Integer HR_Max_Actual;
    private Integer Watts_Avg_Planned;
    private Integer Watts_Max_Planned;
    private Integer Watts_Avg_Actual;
    private Integer Watts_Max_Actual;
    private Integer RPE_Planned;
    private Integer RPE_Actual;
    private String HR_Zone1_Time;
    private String HR_Zone2_Time;
    private String HR_Zone3_Time;
    private String HR_Zone4_Time;
    private String HR_Zone5_Time;
    private String HR_Zone6_Time;
    private Integer Calories_Burned;
    private Integer Sleep_Quality;

    public TrainingLog() { }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAthlete_Id() {
        return Athlete_Id;
    }

    public void setAthlete_Id(String athlete_Id) {
        Athlete_Id = athlete_Id;
    }

    public Integer getType_ID() {
        return Type_ID;
    }

    public void setType_ID(Integer type_ID) {
        Type_ID = type_ID;
    }

    public String getLog_Date() {
        return Log_Date;
    }

    public void setLog_Date(String log_Date) {
        Log_Date = log_Date;
    }

    public String getLog_Time() {
        return Log_Time;
    }

    public void setLog_Time(String log_Time) {
        Log_Time = log_Time;
    }

    public String getLog_Name() {
        return Log_Name;
    }

    public void setLog_Name(String log_Name) {
        Log_Name = log_Name;
    }

    public String getLog_Description() {
        return Log_Description;
    }

    public void setLog_Description(String log_Description) {
        Log_Description = log_Description;
    }

    public String getAthletes_Comments() {
        return Athletes_Comments;
    }

    public void setAthletes_Comments(String athletes_Comments) {
        Athletes_Comments = athletes_Comments;
    }

    public String getCoaches_Comments() {
        return Coaches_Comments;
    }

    public void setCoaches_Comments(String coaches_Comments) {
        Coaches_Comments = coaches_Comments;
    }

    public String getWorkout_Comments() {
        return Workout_Comments;
    }

    public void setWorkout_Comments(String workout_Comments) {
        Workout_Comments = workout_Comments;
    }

    public String getDuration_Planned() {
        return Duration_Planned;
    }

    public void setDuration_Planned(String duration_Planned) {
        Duration_Planned = duration_Planned;
    }

    public String getDuration_Actual() {
        return Duration_Actual;
    }

    public void setDuration_Actual(String duration_Actual) {
        Duration_Actual = duration_Actual;
    }

    public Float getDistance_Planned() {
        return Distance_Planned;
    }

    public void setDistance_Planned(Float distance_Planned) {
        Distance_Planned = distance_Planned;
    }

    public Float getDistance_Actual() {
        return Distance_Actual;
    }

    public void setDistance_Actual(Float distance_Actual) {
        Distance_Actual = distance_Actual;
    }

    public String getDistance_Unit() {
        return Distance_Unit;
    }

    public void setDistance_Unit(String distance_Unit) {
        Distance_Unit = distance_Unit;
    }

    public Integer getHR_Resting_Planned() {
        return HR_Resting_Planned;
    }

    public void setHR_Resting_Planned(Integer HR_Resting_Planned) {
        this.HR_Resting_Planned = HR_Resting_Planned;
    }

    public Integer getHR_Avg_Planned() {
        return HR_Avg_Planned;
    }

    public void setHR_Avg_Planned(Integer HR_Avg_Planned) {
        this.HR_Avg_Planned = HR_Avg_Planned;
    }

    public Integer getHR_Max_Planned() {
        return HR_Max_Planned;
    }

    public void setHR_Max_Planned(Integer HR_Max_Planned) {
        this.HR_Max_Planned = HR_Max_Planned;
    }

    public Integer getHR_Resting_Actual() {
        return HR_Resting_Actual;
    }

    public void setHR_Resting_Actual(Integer HR_Resting_Actual) {
        this.HR_Resting_Actual = HR_Resting_Actual;
    }

    public Integer getHR_Avg_Actual() {
        return HR_Avg_Actual;
    }

    public void setHR_Avg_Actual(Integer HR_Avg_Actual) {
        this.HR_Avg_Actual = HR_Avg_Actual;
    }

    public Integer getHR_Max_Actual() {
        return HR_Max_Actual;
    }

    public void setHR_Max_Actual(Integer HR_Max_Actual) {
        this.HR_Max_Actual = HR_Max_Actual;
    }

    public Integer getWatts_Avg_Planned() {
        return Watts_Avg_Planned;
    }

    public void setWatts_Avg_Planned(Integer watts_Avg_Planned) {
        Watts_Avg_Planned = watts_Avg_Planned;
    }

    public Integer getWatts_Max_Planned() {
        return Watts_Max_Planned;
    }

    public void setWatts_Max_Planned(Integer watts_Max_Planned) {
        Watts_Max_Planned = watts_Max_Planned;
    }

    public Integer getWatts_Avg_Actual() {
        return Watts_Avg_Actual;
    }

    public void setWatts_Avg_Actual(Integer watts_Avg_Actual) {
        Watts_Avg_Actual = watts_Avg_Actual;
    }

    public Integer getWatts_Max_Actual() {
        return Watts_Max_Actual;
    }

    public void setWatts_Max_Actual(Integer watts_Max_Actual) {
        Watts_Max_Actual = watts_Max_Actual;
    }

    public Integer getRPE_Planned() {
        return RPE_Planned;
    }

    public void setRPE_Planned(Integer RPE_Planned) {
        this.RPE_Planned = RPE_Planned;
    }

    public Integer getRPE_Actual() {
        return RPE_Actual;
    }

    public void setRPE_Actual(Integer RPE_Actual) {
        this.RPE_Actual = RPE_Actual;
    }

    public String getHR_Zone1_Time() {
        return HR_Zone1_Time;
    }

    public void setHR_Zone1_Time(String HR_Zone1_Time) {
        this.HR_Zone1_Time = HR_Zone1_Time;
    }

    public String getHR_Zone2_Time() {
        return HR_Zone2_Time;
    }

    public void setHR_Zone2_Time(String HR_Zone2_Time) {
        this.HR_Zone2_Time = HR_Zone2_Time;
    }

    public String getHR_Zone3_Time() {
        return HR_Zone3_Time;
    }

    public void setHR_Zone3_Time(String HR_Zone3_Time) {
        this.HR_Zone3_Time = HR_Zone3_Time;
    }

    public String getHR_Zone4_Time() {
        return HR_Zone4_Time;
    }

    public void setHR_Zone4_Time(String HR_Zone4_Time) {
        this.HR_Zone4_Time = HR_Zone4_Time;
    }

    public String getHR_Zone5_Time() {
        return HR_Zone5_Time;
    }

    public void setHR_Zone5_Time(String HR_Zone5_Time) {
        this.HR_Zone5_Time = HR_Zone5_Time;
    }

    public String getHR_Zone6_Time() {
        return HR_Zone6_Time;
    }

    public void setHR_Zone6_Time(String HR_Zone6_Time) {
        this.HR_Zone6_Time = HR_Zone6_Time;
    }

    public Integer getCalories_Burned() {
        return Calories_Burned;
    }

    public void setCalories_Burned(Integer calories_Burned) {
        Calories_Burned = calories_Burned;
    }

    public Integer getSleep_Quality() {
        return Sleep_Quality;
    }

    public void setSleep_Quality(Integer sleep_Quality) {
        Sleep_Quality = sleep_Quality;
    }
}
