class TrainingLog:
    def __init__(self, Id, Athlete_Id, Type_Id, Log_Date, Log_Time, Log_Name, Log_Description, Athletes_Comments, Coaches_Comments, Workout_Comments, Duration_Planned,
                 Duration_Actual, Distance_Planned, Distance_Actual, Distance_Unit, HR_Resting_Planned, HR_Avg_Planned, HR_Max_Planned, HR_Resting_Actual, HR_Avg_Actual,
                 HR_Max_Actual, Watts_Avg_Planned, Watts_Max_Planned, Watts_Avg_Actual, Watts_Max_Actual, RPE_Planned, RPE_Actual, HR_Zone1_Time, HR_Zone2_Time,
                 HR_Zone3_Time, HR_Zone4_Time, HR_Zone5_Time, HR_Zone6_Time, Calories_Burned, Sleep_Quality):

        self.Id = Id
        self.Athlete_Id = Athlete_Id
        self.Type_ID = Type_Id
        self.Log_Date = Log_Date
        self.Log_Time = Log_Time
        self.Log_Name = Log_Name
        self.Log_Description = Log_Description
        self.Athletes_Comments = Athletes_Comments
        self.Coaches_Comments = Coaches_Comments
        self.Workout_Comments = Workout_Comments
        self.Duration_Planned = Duration_Planned
        self.Duration_Actual = Duration_Actual
        self.Distance_Planned = Distance_Planned
        self.Distance_Actual = Distance_Actual
        self.Distance_Unit = Distance_Unit
        self.HR_Resting_Planned = HR_Resting_Planned
        self.HR_Avg_Planned = HR_Avg_Planned
        self.HR_Max_Planned = HR_Max_Planned
        self.HR_Resting_Actual = HR_Resting_Actual
        self.HR_Avg_Actual = HR_Avg_Actual
        self.HR_Max_Actual = HR_Max_Actual
        self.Watts_Avg_Planned = Watts_Avg_Planned
        self.Watts_Max_Planned = Watts_Max_Planned
        self.Watts_Avg_Actual = Watts_Avg_Actual
        self.Watts_Max_Actual = Watts_Max_Actual
        self.RPE_Planned = RPE_Planned
        self.RPE_Actual = RPE_Actual
        self.HR_Zone1_Time = HR_Zone1_Time
        self.HR_Zone2_Time = HR_Zone2_Time
        self.HR_Zone3_Time = HR_Zone3_Time
        self.HR_Zone4_Time = HR_Zone4_Time
        self.HR_Zone5_Time = HR_Zone5_Time
        self.HR_Zone6_Time = HR_Zone6_Time
        self.Calories_Burned = Calories_Burned
        self.Sleep_Quality = Sleep_Quality