import { Component, OnInit } from '@angular/core';
import {TrainingLogTemplate} from '../../_models/training-log-template.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TrainingLogTemplateService} from '../../_services/training-log-template.service';
import {AlertService} from '../../_services/alert.service';
import {Router} from '@angular/router';
import {AppConstants} from '../../shared/app.constants';
import {AuthService} from '../../_services/auth.service';
import {BaseResponse} from '../../_models/response/base-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';

@Component({
  selector: 'app-create-training-log-template',
  templateUrl: '../common/training-log-template-form.component.html',
  styleUrls: ['./create-training-log-template.component.css']
})
export class CreateTrainingLogTemplateComponent implements OnInit {

  pageName: string;
  templateEntry: FormGroup;
  trainingLogTemplateMeasureTypes: object[];
  trainingLogTemplateDistanceType: string;

  constructor(
    private alertService: AlertService,
    private authService: AuthService,
    private templateService: TrainingLogTemplateService,
    private router: Router
  ) { }

  ngOnInit() {
    this.pageName = 'create';
    this.trainingLogTemplateMeasureTypes = AppConstants.TRAINING_LOG_MEASUREMENT_TYPES;

    this.templateEntry = new FormGroup({
      'Name': new FormControl(null, [Validators.required]),

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

      'HR_Zone1_Time': new FormControl(null),
      'HR_Zone2_Time': new FormControl(null),
      'HR_Zone3_Time': new FormControl(null),
      'HR_Zone4_Time': new FormControl(null),
      'HR_Zone5_Time': new FormControl(null),
      'HR_Zone6_Time': new FormControl(null)
    });

    this.trainingLogTemplateDistanceType = 'KM';
  }

  onSubmit() {
    const template = new TrainingLogTemplate();

    template.Name = this.templateEntry.value.Name;

    template.Duration_Planned = this.templateEntry.value.Duration_Planned;
    template.Distance_Planned = this.templateEntry.value.Distance_Planned;
    template.HR_Resting_Planned = this.templateEntry.value.HR_Resting_Planned;
    template.HR_Avg_Planned = this.templateEntry.value.HR_Avg_Planned;
    template.HR_Max_Planned = this.templateEntry.value.HR_Max_Planned;
    template.Watts_Avg_Planned = this.templateEntry.value.Watts_Avg_Planned;
    template.Watts_Max_Planned = this.templateEntry.value.Watts_Max_Planned;
    template.RPE_Planned = this.templateEntry.value.RPE_Planned;

    template.Duration_Actual = this.templateEntry.value.Duration_Actual;
    template.Distance_Actual = this.templateEntry.value.Distance_Actual;
    template.HR_Resting_Actual = this.templateEntry.value.HR_Resting_Actual;
    template.HR_Avg_Actual = this.templateEntry.value.HR_Avg_Actual;
    template.HR_Max_Actual = this.templateEntry.value.HR_Max_Actual;
    template.Watts_Avg_Actual = this.templateEntry.value.Watts_Avg_Actual;
    template.Watts_Max_Actual = this.templateEntry.value.Watts_Max_Actual;
    template.RPE_Actual = this.templateEntry.value.RPE_Actual;

    template.Distance_Unit = this.trainingLogTemplateDistanceType;
    template.HR_Zone1_Time = this.templateEntry.value.HR_Zone1_Time;
    template.HR_Zone2_Time = this.templateEntry.value.HR_Zone2_Time;
    template.HR_Zone3_Time = this.templateEntry.value.HR_Zone3_Time;
    template.HR_Zone4_Time = this.templateEntry.value.HR_Zone4_Time;
    template.HR_Zone5_Time = this.templateEntry.value.HR_Zone5_Time;
    template.HR_Zone6_Time = this.templateEntry.value.HR_Zone6_Time;

    this.templateService.createTemplate(template, this.authService.getCurrentAuthObject().UserId).subscribe(
      (response: BaseResponse) => {
        this.alertService.success(response.Message, true);
        this.router.navigate(['templates']);
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message, true);
      }
    );
  }
}
