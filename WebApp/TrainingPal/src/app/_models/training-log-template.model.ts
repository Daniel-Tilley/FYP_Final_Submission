
import {TrainingLogTemplateInterface} from '../_interfaces/training-log-template-interface';

export class TrainingLogTemplate implements TrainingLogTemplateInterface {
  Id: number;
  Coach_Id: string;
  Name: string;
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
}
