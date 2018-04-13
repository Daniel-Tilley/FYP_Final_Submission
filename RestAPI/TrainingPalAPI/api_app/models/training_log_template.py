class TrainingLogTemplate:
    def __init__(self, Id, Coach_Id, Name, Duration_Planned, Duration_Actual, Distance_Planned, Distance_Actual, Distance_Unit, HR_Resting_Planned, HR_Avg_Planned,
                 HR_Max_Planned, HR_Resting_Actual, HR_Avg_Actual, HR_Max_Actual, Watts_Avg_Planned, Watts_Max_Planned, Watts_Avg_Actual, Watts_Max_Actual, RPE_Planned,
                 RPE_Actual, HR_Zone1_Time, HR_Zone2_Time, HR_Zone3_Time, HR_Zone4_Time, HR_Zone5_Time, HR_Zone6_Time):

        self.Id = Id
        self.Coach_Id = Coach_Id
        self.Name = Name
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