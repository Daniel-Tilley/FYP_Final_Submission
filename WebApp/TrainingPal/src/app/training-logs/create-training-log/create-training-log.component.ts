import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppConstants} from '../../shared/app.constants';
import {TrainingLogService} from '../../_services/training-log.service';
import {TrainingLog} from '../../_models/training-log.model';
import {BaseResponse} from '../../_models/response/base-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService} from '../../_services/alert.service';
import {AuthService} from '../../_services/auth.service';
import {TrainingLogTemplateService} from '../../_services/training-log-template.service';
import {TrainingLogTemplate} from '../../_models/training-log-template.model';
import {TrainingLogTemplatesResponse} from '../../_models/response/training-log-templates-response.model';
import {TrainingLogTemplateResponse} from '../../_models/response/training-log-template-response.model';

@Component({
  selector: 'app-create-training-log',
  templateUrl: '../common/training-log-form.component.html',
  styleUrls: ['./create-training-log.component.css']
})
export class CreateTrainingLogComponent implements OnInit {
  pageId: number;
  pageName: string;

  trainingLogEntry: FormGroup;
  trainingLogTypes: string[];
  trainingLogSleepQuality: string[];
  trainingLogMeasureTypes: object[];

  trainingLogDistanceType: string;

  isUserCoach: boolean;
  trainingTemplate: FormGroup;
  templates: TrainingLogTemplate[] = [];
  currentTemplateId: number;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private trainingLogService: TrainingLogService,
    private templateService: TrainingLogTemplateService,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.pageId = 1;
    this.pageName = 'create';

    this.trainingLogTypes = AppConstants.TRAINING_LOG_TYPES;
    this.trainingLogMeasureTypes = AppConstants.TRAINING_LOG_MEASUREMENT_TYPES;
    this.trainingLogSleepQuality = AppConstants.TRAINING_LOG_SLEEP_QUALITY;

    this.isUserCoach = this.authService.isUserCoach();

    this.trainingLogEntry = new FormGroup({
      'Athlete_Id': new FormControl(null, [Validators.required]),
      'Log_Type': new FormControl(null, [Validators.required]),
      'Log_Date': new FormControl(null, [Validators.required]),
      'Log_Time': new FormControl(null),
      'Log_Name': new FormControl(null),
      'Log_Description': new FormControl(null),

      'Duration_Planned': new FormControl(null),
      'Distance_Planned': new FormControl(null, [Validators.max(9999), Validators.min(1), Validators.pattern(/^\s*-?\d+(\.\d{1,2})?\s*$/)]),
      'HR_Resting_Planned': new FormControl(null, [Validators.max(999), Validators.min(1)]),
      'HR_Avg_Planned': new FormControl(null, [Validators.max(999), Validators.min(1)]),
      'HR_Max_Planned': new FormControl(null, [Validators.max(999), Validators.min(1)]),
      'Watts_Avg_Planned': new FormControl(null, [Validators.max(9999), Validators.min(1)]),
      'Watts_Max_Planned': new FormControl(null, [Validators.max(9999), Validators.min(1)]),
      'RPE_Planned': new FormControl(null, [Validators.max(10), Validators.min(1)]),

      'Duration_Actual': new FormControl(null),
      'Distance_Actual': new FormControl(null, [Validators.max(9999), Validators.min(1), Validators.pattern(/^\s*-?\d+(\.\d{1,2})?\s*$/)]),
      'HR_Resting_Actual': new FormControl(null, [Validators.max(999), Validators.min(1)]),
      'HR_Avg_Actual': new FormControl(null, [Validators.max(999), Validators.min(1)]),
      'HR_Max_Actual': new FormControl(null, [Validators.max(999), Validators.min(1)]),
      'Watts_Avg_Actual': new FormControl(null, [Validators.max(9999), Validators.min(1)]),
      'Watts_Max_Actual': new FormControl(null, [Validators.max(9999), Validators.min(1)]),
      'RPE_Actual': new FormControl(null, [Validators.max(10), Validators.min(1)]),

      'Calories_Burned': new FormControl(null, [Validators.max(9999), Validators.min(1)]),
      'HR_Zone1_Time': new FormControl(null),
      'HR_Zone2_Time': new FormControl(null),
      'HR_Zone3_Time': new FormControl(null),
      'HR_Zone4_Time': new FormControl(null),
      'HR_Zone5_Time': new FormControl(null),
      'HR_Zone6_Time': new FormControl(null),
      'Sleep_Quality': new FormControl(null),

      'Athletes_Comments': new FormControl(null),
      'Coaches_Comments': new FormControl(null),
      'Workout_Comments': new FormControl(null)
    });

    this.trainingLogEntry.patchValue({'Athlete_Id': this.route.parent.snapshot.params['user_id']});
    this.trainingLogDistanceType = 'KM';

    this.trainingTemplate = new FormGroup({
      'template': new FormControl(null, [Validators.required])
    });

    if (this.isUserCoach) {
      this.templateService.getTemplates(this.authService.getCurrentAuthObject().UserId).subscribe(
        (response: TrainingLogTemplatesResponse) => {
          this.templates = response.Data.Templates;
        },
        (error: ErrorResponse) => {
          console.error(error.error.Message);
          this.templates = [];
        }
      );
    }
  }

  onNextClicked() {
    this.pageId ++;
  }

  onBackClicked() {
    this.pageId --;
  }

  onPageClicked(pageNum: number) {
    this.pageId = pageNum;
  }

  onApplyTemplateClicked() {
    this.alertService.clear();

    if (this.currentTemplateId === this.trainingTemplate.value.template) {
      this.alertService.error('Please pick a different template!');
    } else {
      if (this.pageId !== 2) { this.pageId = 2; }

      this.currentTemplateId = this.trainingTemplate.value.template;

      this.templateService.getTemplate(this.currentTemplateId, this.authService.getCurrentAuthObject().UserId).subscribe(
        (response: TrainingLogTemplateResponse) => {

          const template: TrainingLogTemplate = response.Data.Template;

          template.Duration_Planned = this.checkTimeStamp(template.Duration_Planned);
          template.Duration_Actual = this.checkTimeStamp(template.Duration_Actual);
          template.HR_Zone1_Time = this.checkTimeStamp(template.HR_Zone1_Time);
          template.HR_Zone2_Time = this.checkTimeStamp(template.HR_Zone2_Time);
          template.HR_Zone3_Time = this.checkTimeStamp(template.HR_Zone3_Time);
          template.HR_Zone4_Time = this.checkTimeStamp(template.HR_Zone4_Time);
          template.HR_Zone5_Time = this.checkTimeStamp(template.HR_Zone5_Time);
          template.HR_Zone6_Time = this.checkTimeStamp(template.HR_Zone6_Time);

          this.trainingLogEntry.patchValue({
            'Duration_Planned': template.Duration_Planned,
            'Distance_Planned': template.Distance_Planned,
            'HR_Resting_Planned': template.HR_Resting_Planned,
            'HR_Avg_Planned': template.HR_Avg_Planned,
            'HR_Max_Planned': template.HR_Max_Planned,
            'Watts_Avg_Planned': template.Watts_Avg_Planned,
            'Watts_Max_Planned': template.Watts_Max_Planned,
            'RPE_Planned': template.RPE_Planned,

            'Duration_Actual': template.Duration_Actual,
            'Distance_Actual': template.Distance_Actual,
            'HR_Resting_Actual': template.HR_Resting_Actual,
            'HR_Avg_Actual': template.HR_Avg_Actual,
            'HR_Max_Actual': template.HR_Max_Actual,
            'Watts_Avg_Actual': template.Watts_Avg_Actual,
            'Watts_Max_Actual': template.Watts_Max_Actual,
            'RPE_Actual': template.RPE_Actual,

            'HR_Zone1_Time': template.HR_Zone1_Time,
            'HR_Zone2_Time': template.HR_Zone2_Time,
            'HR_Zone3_Time': template.HR_Zone3_Time,
            'HR_Zone4_Time': template.HR_Zone4_Time,
            'HR_Zone5_Time': template.HR_Zone5_Time,
            'HR_Zone6_Time': template.HR_Zone6_Time,
          });

          this.trainingLogDistanceType = template.Distance_Unit;
        },
        (error: ErrorResponse) => {
          console.error(error.error.Message);
        }
      );
    }
  }

  onSubmit() {
    const trainingLog = new TrainingLog();

    trainingLog.Athlete_Id = this.trainingLogEntry.value.Athlete_Id;
    trainingLog.Type_ID = this.trainingLogEntry.value.Log_Type;
    trainingLog.Log_Date = this.trainingLogEntry.value.Log_Date;
    trainingLog.Log_Time = this.trainingLogEntry.value.Log_Time;
    trainingLog.Log_Name = this.trainingLogEntry.value.Log_Name;
    trainingLog.Log_Description = this.trainingLogEntry.value.Log_Description;

    trainingLog.Duration_Planned = this.trainingLogEntry.value.Duration_Planned;
    trainingLog.Distance_Planned = this.trainingLogEntry.value.Distance_Planned;
    trainingLog.HR_Resting_Planned = this.trainingLogEntry.value.HR_Resting_Planned;
    trainingLog.HR_Avg_Planned = this.trainingLogEntry.value.HR_Avg_Planned;
    trainingLog.HR_Max_Planned = this.trainingLogEntry.value.HR_Max_Planned;
    trainingLog.Watts_Avg_Planned = this.trainingLogEntry.value.Watts_Avg_Planned;
    trainingLog.Watts_Max_Planned = this.trainingLogEntry.value.Watts_Max_Planned;
    trainingLog.RPE_Planned = this.trainingLogEntry.value.RPE_Planned;

    trainingLog.Duration_Actual = this.trainingLogEntry.value.Duration_Actual;
    trainingLog.Distance_Actual = this.trainingLogEntry.value.Distance_Actual;
    trainingLog.HR_Resting_Actual = this.trainingLogEntry.value.HR_Resting_Actual;
    trainingLog.HR_Avg_Actual = this.trainingLogEntry.value.HR_Avg_Actual;
    trainingLog.HR_Max_Actual = this.trainingLogEntry.value.HR_Max_Actual;
    trainingLog.Watts_Avg_Actual = this.trainingLogEntry.value.Watts_Avg_Actual;
    trainingLog.Watts_Max_Actual = this.trainingLogEntry.value.Watts_Max_Actual;
    trainingLog.RPE_Actual = this.trainingLogEntry.value.RPE_Actual;

    trainingLog.Distance_Unit = this.trainingLogDistanceType;
    trainingLog.Calories_Burned = this.trainingLogEntry.value.Calories_Burned;
    trainingLog.HR_Zone1_Time = this.trainingLogEntry.value.HR_Zone1_Time;
    trainingLog.HR_Zone2_Time = this.trainingLogEntry.value.HR_Zone2_Time;
    trainingLog.HR_Zone3_Time = this.trainingLogEntry.value.HR_Zone3_Time;
    trainingLog.HR_Zone4_Time = this.trainingLogEntry.value.HR_Zone4_Time;
    trainingLog.HR_Zone5_Time = this.trainingLogEntry.value.HR_Zone5_Time;
    trainingLog.HR_Zone6_Time = this.trainingLogEntry.value.HR_Zone6_Time;
    trainingLog.Sleep_Quality = this.trainingLogEntry.value.Sleep_Quality;

    trainingLog.Athletes_Comments = this.trainingLogEntry.value.Athletes_Comments;
    trainingLog.Coaches_Comments = this.trainingLogEntry.value.Coaches_Comments;
    trainingLog.Workout_Comments = this.trainingLogEntry.value.Workout_Comments;

    this.trainingLogService.createTrainingLog(trainingLog, trainingLog.Athlete_Id).subscribe(
      (response: BaseResponse) => {
        this.alertService.success(response.Message, true);
        this.router.navigate(['training-log', trainingLog.Athlete_Id, 'calendar']);
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message, true);
      }
    );
  }

  checkTimeStamp(timestamp: string) {
    if (timestamp !== null) {
      const parts = timestamp.split(':');
      let newTimeStamp = '';

      for (const part of parts) {
        if (part.length < 2) {
          newTimeStamp += '0' + part + ':';
        } else {
          newTimeStamp += part + ':';
        }
      }

      return newTimeStamp.slice(0, newTimeStamp.length - 1);
    }

    return timestamp;
  }

}
