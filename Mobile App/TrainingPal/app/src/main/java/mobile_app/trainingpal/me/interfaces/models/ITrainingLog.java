package mobile_app.trainingpal.me.interfaces.models;

public interface ITrainingLog {

    public Integer getId();
    public void setId(Integer id);

    public String getAthlete_Id();
    public void setAthlete_Id(String athlete_Id);

    public Integer getType_ID();
    public void setType_ID(Integer type_ID);

    public String getLog_Date();
    public void setLog_Date(String log_Date);

    public String getLog_Time();
    public void setLog_Time(String log_Time);

    public String getLog_Name();
    public void setLog_Name(String log_Name);

    public String getLog_Description();
    public void setLog_Description(String log_Description);

    public String getAthletes_Comments();
    public void setAthletes_Comments(String athletes_Comments);

    public String getCoaches_Comments();
    public void setCoaches_Comments(String coaches_Comments);

    public String getWorkout_Comments();
    public void setWorkout_Comments(String workout_Comments);

    public String getDuration_Planned();
    public void setDuration_Planned(String duration_Planned);

    public String getDuration_Actual();
    public void setDuration_Actual(String duration_Actual);

    public Float getDistance_Planned();
    public void setDistance_Planned(Float distance_Planned);

    public Float getDistance_Actual();
    public void setDistance_Actual(Float distance_Actual);

    public String getDistance_Unit();
    public void setDistance_Unit(String distance_Unit);

    public Integer getHR_Resting_Planned();
    public void setHR_Resting_Planned(Integer HR_Resting_Planned);

    public Integer getHR_Avg_Planned();
    public void setHR_Avg_Planned(Integer HR_Avg_Planned);

    public Integer getHR_Max_Planned();
    public void setHR_Max_Planned(Integer HR_Max_Planned);

    public Integer getHR_Resting_Actual();
    public void setHR_Resting_Actual(Integer HR_Resting_Actual);

    public Integer getHR_Avg_Actual();
    public void setHR_Avg_Actual(Integer HR_Avg_Actual);

    public Integer getHR_Max_Actual();
    public void setHR_Max_Actual(Integer HR_Max_Actual);

    public Integer getWatts_Avg_Planned();
    public void setWatts_Avg_Planned(Integer watts_Avg_Planned);

    public Integer getWatts_Max_Planned();
    public void setWatts_Max_Planned(Integer watts_Max_Planned);

    public Integer getWatts_Avg_Actual();
    public void setWatts_Avg_Actual(Integer watts_Avg_Actual);

    public Integer getWatts_Max_Actual();
    public void setWatts_Max_Actual(Integer watts_Max_Actual);

    public Integer getRPE_Planned();
    public void setRPE_Planned(Integer RPE_Planned);

    public Integer getRPE_Actual();
    public void setRPE_Actual(Integer RPE_Actual);

    public String getHR_Zone1_Time();
    public void setHR_Zone1_Time(String HR_Zone1_Time);

    public String getHR_Zone2_Time();
    public void setHR_Zone2_Time(String HR_Zone2_Time);

    public String getHR_Zone3_Time();
    public void setHR_Zone3_Time(String HR_Zone3_Time);

    public String getHR_Zone4_Time();
    public void setHR_Zone4_Time(String HR_Zone4_Time);

    public String getHR_Zone5_Time();
    public void setHR_Zone5_Time(String HR_Zone5_Time);

    public String getHR_Zone6_Time();
    public void setHR_Zone6_Time(String HR_Zone6_Time);

    public Integer getCalories_Burned();
    public void setCalories_Burned(Integer calories_Burned);

    public Integer getSleep_Quality();
    public void setSleep_Quality(Integer sleep_Quality);
}
