import { Component, OnInit } from '@angular/core';
import {TrainingLogTemplate} from '../../_models/training-log-template.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TrainingLogTemplateService} from '../../_services/training-log-template.service';
import {AlertService} from '../../_services/alert.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AppConstants} from '../../shared/app.constants';
import {TrainingLogTemplateResponse} from '../../_models/response/training-log-template-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {AuthService} from '../../_services/auth.service';
import {BaseResponse} from '../../_models/response/base-response.model';
import {UpdateObject} from '../../_models/update_object.model';

@Component({
  selector: 'app-edit-training-log-template',
  templateUrl: '../common/training-log-template-form.component.html',
  styleUrls: ['./edit-training-log-template.component.css']
})
export class EditTrainingLogTemplateComponent implements OnInit {

  pageName: string;
  currentTemplate: TrainingLogTemplate;
  templateEntry: FormGroup;
  trainingLogTemplateMeasureTypes: object[];
  trainingLogTemplateDistanceType: string;

  constructor(
    private alertService: AlertService,
    private authService: AuthService,
    private templateService: TrainingLogTemplateService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.pageName = 'edit';
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

    this.setFormValues();
  }

  setFormValues () {
    this.templateService.getTemplate(this.route.snapshot.params['template_id'], this.authService.getCurrentAuthObject().UserId).subscribe(
      (response: TrainingLogTemplateResponse) => {

        const template = response.Data.Template;

        template.Duration_Planned = this.checkTimeStamp(template.Duration_Planned);
        template.Duration_Actual = this.checkTimeStamp(template.Duration_Actual);
        template.HR_Zone1_Time = this.checkTimeStamp(template.HR_Zone1_Time);
        template.HR_Zone2_Time = this.checkTimeStamp(template.HR_Zone2_Time);
        template.HR_Zone3_Time = this.checkTimeStamp(template.HR_Zone3_Time);
        template.HR_Zone4_Time = this.checkTimeStamp(template.HR_Zone4_Time);
        template.HR_Zone5_Time = this.checkTimeStamp(template.HR_Zone5_Time);
        template.HR_Zone6_Time = this.checkTimeStamp(template.HR_Zone6_Time);

        this.currentTemplate = template;

        this.templateEntry.setValue({
          'Name': template.Name,

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
          'HR_Zone6_Time': template.HR_Zone6_Time
        });

        this.trainingLogTemplateDistanceType = template.Distance_Unit;

      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );
  }

  onSubmit() {
    const updatedTemplate = new TrainingLogTemplate();

    updatedTemplate.Name = this.templateEntry.value.Name;

    updatedTemplate.Duration_Planned = this.templateEntry.value.Duration_Planned;
    updatedTemplate.Distance_Planned = this.templateEntry.value.Distance_Planned;
    updatedTemplate.HR_Resting_Planned = this.templateEntry.value.HR_Resting_Planned;
    updatedTemplate.HR_Avg_Planned = this.templateEntry.value.HR_Avg_Planned;
    updatedTemplate.HR_Max_Planned = this.templateEntry.value.HR_Max_Planned;
    updatedTemplate.Watts_Avg_Planned = this.templateEntry.value.Watts_Avg_Planned;
    updatedTemplate.Watts_Max_Planned = this.templateEntry.value.Watts_Max_Planned;
    updatedTemplate.RPE_Planned = this.templateEntry.value.RPE_Planned;

    updatedTemplate.Duration_Actual = this.templateEntry.value.Duration_Actual;
    updatedTemplate.Distance_Actual = this.templateEntry.value.Distance_Actual;
    updatedTemplate.HR_Resting_Actual = this.templateEntry.value.HR_Resting_Actual;
    updatedTemplate.HR_Avg_Actual = this.templateEntry.value.HR_Avg_Actual;
    updatedTemplate.HR_Max_Actual = this.templateEntry.value.HR_Max_Actual;
    updatedTemplate.Watts_Avg_Actual = this.templateEntry.value.Watts_Avg_Actual;
    updatedTemplate.Watts_Max_Actual = this.templateEntry.value.Watts_Max_Actual;
    updatedTemplate.RPE_Actual = this.templateEntry.value.RPE_Actual;

    updatedTemplate.Distance_Unit = this.trainingLogTemplateDistanceType;
    updatedTemplate.HR_Zone1_Time = this.templateEntry.value.HR_Zone1_Time;
    updatedTemplate.HR_Zone2_Time = this.templateEntry.value.HR_Zone2_Time;
    updatedTemplate.HR_Zone3_Time = this.templateEntry.value.HR_Zone3_Time;
    updatedTemplate.HR_Zone4_Time = this.templateEntry.value.HR_Zone4_Time;
    updatedTemplate.HR_Zone5_Time = this.templateEntry.value.HR_Zone5_Time;
    updatedTemplate.HR_Zone6_Time = this.templateEntry.value.HR_Zone6_Time;

    const updatedObject = new UpdateObject();
    const userKeys = Object.keys(updatedTemplate);

    let count = 0;

    for (const key of userKeys) {
      if (updatedTemplate[key] !== this.currentTemplate[key]) {
        updatedObject.MemberKeys.push(key);
        updatedObject.MemberValues.push(updatedTemplate[key]);
        count ++;
      }
    }

    if (count !== 0) {

      this.templateService.updateTemplate(updatedObject, this.route.snapshot.params['template_id'], this.authService.getCurrentAuthObject().UserId)
        .subscribe(
          (response: BaseResponse) => {
            this.alertService.success(response.Message, true);
            this.router.navigate(['templates']);
          },
          (error: ErrorResponse) => {
            console.error(error.error.Message);
          }
        );
    } else {
      this.alertService.error('Nothing To Update!');
    }
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
