import {TrainingLogInterface} from '../_interfaces/training-log.interface';

export class TrainingLog implements TrainingLogInterface {
  Id: number;
  Athlete_Id: string;
  Type_ID: number;
  Log_Date: string;
  Log_Time: string;
  Log_Name: string;
  Log_Description: string;
  Athletes_Comments: string;
  Coaches_Comments: string;
  Workout_Comments: string;
  Duration_Planned: string;
  Duration_Actual: string;
  Distance_Planned: string;
  Distance_Actual: string;
  Distance_Unit: string;
  HR_Resting_Planned: number;
  HR_Avg_Planned: number;
  HR_Max_Planned: number;
  HR_Resting_Actual: number;
  HR_Avg_Actual: number;
  HR_Max_Actual: number;
  Watts_Avg_Planned: number;
  Watts_Max_Planned: number;
  Watts_Avg_Actual: number;
  Watts_Max_Actual: number;
  RPE_Planned: number;
  RPE_Actual: number;
  HR_Zone1_Time: string;
  HR_Zone2_Time: string;
  HR_Zone3_Time: string;
  HR_Zone4_Time: string;
  HR_Zone5_Time: string;
  HR_Zone6_Time: string;
  Calories_Burned: number;
  Sleep_Quality: number;
}
