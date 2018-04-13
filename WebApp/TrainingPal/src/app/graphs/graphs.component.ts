import { Component, OnInit } from '@angular/core';
import {TrainingLogService} from '../_services/training-log.service';
import {TrainingLog} from '../_models/training-log.model';
import {TrainingLogsResponse} from '../_models/response/training-logs-response.model';
import {ErrorResponse} from '../_models/response/error-response.model';
import {AlertService} from '../_services/alert.service';
import {AuthService} from '../_services/auth.service';

@Component({
  selector: 'app-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.css']
})
export class GraphsComponent implements OnInit {

  // Class Variables
  trainingLogs: TrainingLog[] = [];

  // Type Variables
  public typeChartLabels: string[] = ['Swimming', 'Cycling', 'Running'];
  public typeChartData: number[] = [];
  public typeChartType = 'pie';


  // HR Variables
  public hrChartLabels: string[] = ['Resting', 'Average', 'Max'];
  public hrChartType = 'bar';
  public hrChartLegend = true;

  public hrChartData: any[] = [];

  public hrChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true
  };


  // Watts Variables
  public wattsChartLabels: string[] = ['Average', 'Max'];
  public wattsChartType = 'bar';
  public wattsChartLegend = true;

  public wattsChartData: any[] = [];

  public wattsChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true
  };


  // RPE Variables
  public rpeChartLabels: string[] = ['Level 1', 'Level 2', 'Level 3', 'Level 4', 'Level 5', 'Level 6', 'Level 7', 'Level 8', 'Level 9', 'Level 10'];
  public rpeChartType = 'radar';

  public rpeChartData: any = [];


  // HR Zone Variables
  public hrZoneChartLabels: string[] = ['Zone 1', 'Zone 2', 'Zone 3', 'Zone 4', 'Zone 5', 'Zone 6'];
  public hrZoneChartType = 'doughnut';

  public hrZoneChartData: number[] = [];


  // Sleep Variables
  public polarAreaChartLabels: string[] = ['Bad', 'Ok', 'Good'];
  public polarAreaChartData: number[] = [];
  public polarAreaLegend = true;

  public polarAreaChartType = 'polarArea';

  constructor(private trainingLogService: TrainingLogService, private alertService: AlertService, private authService: AuthService) { }

  ngOnInit() {
    const date = new Date();
    const today = String(date.getDate());
    const month = this.getMonthIndex(date.getMonth());
    const year = String(date.getFullYear());

    this.trainingLogService.getTrainingLogsByMonth(month, year, this.authService.getCurrentAuthObject().UserId).subscribe(
      (response: TrainingLogsResponse) => {
        this.trainingLogs = response.Data.TrainingLogs;

        this.initTypeChart();
        this.initHRChart();
        this.initWattsChart();
        this.initRPEChart();
        this.initHrZoneChart();
        this.initSleepChart();
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message);
      }
    );
  }

  initTypeChart() {
    const swimmingLogs = this.trainingLogs.filter(item => item.Type_ID.toString() === '2');
    const cyclingLogs = this.trainingLogs.filter(item => item.Type_ID.toString() === '3');
    const runningLogs = this.trainingLogs.filter(item => item.Type_ID.toString() === '4');

    const actual_swimming_duration = swimmingLogs.map((item) => this.convertTimeToMinutes(item.Duration_Actual));
    const actual_cycling_duration = cyclingLogs.map((item) => this.convertTimeToMinutes(item.Duration_Actual));
    const actual_running_duration = runningLogs.map((item) => this.convertTimeToMinutes(item.Duration_Actual));

    const actual_swimming_sum = actual_swimming_duration.length === 0 ? 0 : actual_swimming_duration.reduce((a, b) => a + b);
    const actual_cycling_sum = actual_cycling_duration.length === 0 ? 0 : actual_cycling_duration.reduce((a, b) => a + b);
    const actual_running_sum = actual_running_duration.length === 0 ? 0 : actual_running_duration.reduce((a, b) => a + b);

    this.typeChartData.push((actual_swimming_sum / actual_swimming_duration.length));
    this.typeChartData.push((actual_cycling_sum / actual_cycling_duration.length));
    this.typeChartData.push((actual_running_sum / actual_running_duration.length));
  }

  initHRChart() {
    const arraySize = this.trainingLogs.length;

    const planned_resting_hr_sum = this.trainingLogs.map((item) => item.HR_Resting_Planned === null ? 0 : item.HR_Resting_Planned).reduce((a, b) =>  Number(a + b));
    const actual_resting_hr_sum = this.trainingLogs.map((item) => item.HR_Resting_Actual === null ? 0 : item.HR_Resting_Actual).reduce((a, b) =>  Number(a + b));
    const planned_avg_hr_sum = this.trainingLogs.map((item) => item.HR_Avg_Planned === null ? 0 : item.HR_Avg_Planned).reduce((a, b) =>  Number(a + b));
    const actual_avg_hr_sum = this.trainingLogs.map((item) => item.HR_Avg_Actual === null ? 0 : item.HR_Avg_Actual).reduce((a, b) =>  Number(a + b));
    const planned_max_hr_sum = this.trainingLogs.map((item) => item.HR_Max_Planned === null ? 0 : item.HR_Max_Planned).reduce((a, b) =>  Number(a + b));
    const actual_max_hr_sum = this.trainingLogs.map((item) => item.HR_Max_Actual === null ? 0 : item.HR_Max_Actual).reduce((a, b) =>  Number(a + b));

    this.hrChartData = [
      { data: [(planned_resting_hr_sum / arraySize), (planned_avg_hr_sum / arraySize), (planned_max_hr_sum / arraySize)], label: 'Planned'},
      { data: [(actual_resting_hr_sum / arraySize), (actual_avg_hr_sum / arraySize), (actual_max_hr_sum / arraySize)], label: 'Actual'}
    ];
  }

  initWattsChart() {
    const arraySize = this.trainingLogs.length;

    const planned_avg_watts_sum = this.trainingLogs.map((item) => item.Watts_Avg_Planned === null ? 0 : item.Watts_Avg_Planned).reduce((a, b) => Number(a + b));
    const actual_avg_watts_sum = this.trainingLogs.map((item) => item.Watts_Avg_Actual === null ? 0 : item.Watts_Avg_Actual).reduce((a, b) =>  Number(a + b));
    const planned_max_watts_sum = this.trainingLogs.map((item) => item.Watts_Max_Planned === null ? 0 : item.Watts_Max_Planned).reduce((a, b) =>  Number(a + b));
    const actual_max_watts_sum = this.trainingLogs.map((item) => item.Watts_Max_Actual === null ? 0 : item.Watts_Max_Actual).reduce((a, b) =>  Number(a + b));

    this.wattsChartData = [
      { data: [(planned_avg_watts_sum / arraySize), (planned_max_watts_sum / arraySize)], label: 'Planned'},
      { data: [(actual_avg_watts_sum / arraySize), (actual_max_watts_sum / arraySize)], label: 'Actual'}
    ];
  }

  initRPEChart() {
    const filteredPlanned = this.trainingLogs.filter(item => item.RPE_Planned !== null);
    const filteredActual = this.trainingLogs.filter(item => item.RPE_Actual !== null);

    this.rpeChartData = [
      {data: [
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '1').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '2').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '3').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '4').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '5').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '6').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '7').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '8').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '9').length,
        filteredPlanned.filter(item => item.RPE_Planned.toString() === '10').length
      ], label: 'Planned'},
      {data: [
        filteredActual.filter(item => item.RPE_Actual.toString() === '1').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '2').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '3').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '4').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '5').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '6').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '7').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '8').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '9').length,
        filteredActual.filter(item => item.RPE_Actual.toString() === '10').length
      ], label: 'Actual'}
    ];
  }

  initHrZoneChart() {
    const arraySize = this.trainingLogs.length;

    this.hrZoneChartData = [
      this.trainingLogs.map((item) => item.HR_Zone1_Time === null ? 0 : this.convertTimeToMinutes(item.HR_Zone1_Time)).reduce((a, b) => Number(a + b)) / arraySize,
      this.trainingLogs.map((item) => item.HR_Zone2_Time === null ? 0 : this.convertTimeToMinutes(item.HR_Zone2_Time)).reduce((a, b) => Number(a + b)) / arraySize,
      this.trainingLogs.map((item) => item.HR_Zone3_Time === null ? 0 : this.convertTimeToMinutes(item.HR_Zone3_Time)).reduce((a, b) => Number(a + b)) / arraySize,
      this.trainingLogs.map((item) => item.HR_Zone4_Time === null ? 0 : this.convertTimeToMinutes(item.HR_Zone4_Time)).reduce((a, b) => Number(a + b)) / arraySize,
      this.trainingLogs.map((item) => item.HR_Zone5_Time === null ? 0 : this.convertTimeToMinutes(item.HR_Zone5_Time)).reduce((a, b) => Number(a + b)) / arraySize,
      this.trainingLogs.map((item) => item.HR_Zone6_Time === null ? 0 : this.convertTimeToMinutes(item.HR_Zone6_Time)).reduce((a, b) => Number(a + b)) / arraySize
    ];
  }

  initSleepChart() {
    const arraySize = this.trainingLogs.length;

    const filteredArray = this.trainingLogs.filter(item => item.Sleep_Quality !== null);

    this.polarAreaChartData = [
      filteredArray.filter(item => item.Sleep_Quality.toString() === '1').length,
      filteredArray.filter(item => item.Sleep_Quality.toString() === '2').length,
      filteredArray.filter(item => item.Sleep_Quality.toString() === '3').length,
    ];
  }

  convertTimeToMinutes(time: string) {
    let hours: number;
    let minutes: number;
    let seconds: number;
    let totalMinutes: number;

    if (time === null) {
      return 0;
    } else {
      if (parseInt(time, 10) < 10) {
        hours = Number(time.slice(0, 1));
        minutes = Number(time.slice(2, 4));
        seconds = Number(time.slice(5, 7));
      } else {
        hours = Number(time.slice(0, 2));
        minutes = Number(time.slice(3, 5));
        seconds = Number(time.slice(6, 8));
      }

      totalMinutes = (hours * 60) + minutes + (seconds / 60);

      return totalMinutes;
    }
  }

  private getMonthIndex(month: number) {
    const months = [
      '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'
    ];

    return months[month];
  }
}
