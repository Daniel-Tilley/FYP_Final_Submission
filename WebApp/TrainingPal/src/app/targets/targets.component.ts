import { Component, OnInit } from '@angular/core';
import {Target} from '../_models/target.model';
import {TargetService} from '../_services/target.service';
import {ActivatedRoute} from '@angular/router';
import {TargetsResponse} from '../_models/response/targets-response.model';
import {ErrorResponse} from '../_models/response/error-response.model';
import * as curWeek from 'current-week-number';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {BaseResponse} from '../_models/response/base-response.model';
import {AlertService} from '../_services/alert.service';
import {UpdateObject} from '../_models/update_object.model';

@Component({
  selector: 'app-targets',
  templateUrl: './targets.component.html',
  styleUrls: ['./targets.component.css']
})
export class TargetsComponent implements OnInit {

  targets: Target[] = [];
  currentWeek: string;
  currentYear: string;
  datePicker: FormGroup;
  currentTarget: Target;

  createTargetForm: FormGroup;
  updateTargetForm: FormGroup;

  constructor(private targetService: TargetService, private route: ActivatedRoute, private alertService: AlertService) { }

  ngOnInit() {
    const today = new Date();

    this.currentWeek = curWeek(today);
    this.currentYear = String(today.getFullYear());

    this.datePicker = new FormGroup({
      'week': new FormControl(null, [Validators.required])
    });

    this.createTargetForm = new FormGroup({
      'create_content': new FormControl(null, [Validators.required]),
      'create_completed': new FormControl(null, [Validators.required])
    });

    this.updateTargetForm = new FormGroup({
      'update_content': new FormControl(null, [Validators.required]),
      'update_completed': new FormControl(null, [Validators.required])
    });

    this.datePicker.valueChanges.subscribe(
      (value) => {
        if (value.week !== 0) {
          this.currentYear = value.week.slice(0, 4);
          this.currentWeek = value.week.slice(6, 8);
          this.getTargets();
        }
      });

    this.setDatePicker();
  }

  getTargets() {
    this.targetService.getTargets(this.route.snapshot.params['user_id'], this.currentWeek, this.currentYear).subscribe(
      (response: TargetsResponse) => {
        this.targets = response.Data.Targets;
      },
      (error: ErrorResponse) => {
        this.targets = [];
        console.error(error.error.Message);
      }
    );
  }

  setDatePicker() {
    const dateString = this.currentYear + '-W' + this.currentWeek;

    this.datePicker.setValue({
      'week': dateString
    });
  }

  onTargetClicked(target: Target) {
    this.currentTarget = target;
    this.updateTargetForm.setValue({
      'update_content': target.Content,
      'update_completed': target.Status
    });
  }

  incrementWeek() {
    const currentWeekIndex = Number(this.currentWeek);

    if (currentWeekIndex === 52) {
      this.incrementYear();
      this.currentWeek = '01';
    } else {
      this.currentWeek = (currentWeekIndex + 1) < 10 ? '0' + (currentWeekIndex + 1) : '' + (currentWeekIndex + 1);
    }

    this.setDatePicker();
  }

  decrementWeek() {
    const currentWeekIndex = Number(this.currentWeek);

    if (currentWeekIndex === 1) {
      this.decrementYear();
      this.currentWeek = '52';
    } else {
      this.currentWeek = (currentWeekIndex - 1) < 10 ? '0' + (currentWeekIndex - 1) : '' + (currentWeekIndex - 1);
    }

    this.setDatePicker();
  }

  incrementYear() {
    this.currentYear = '' + (Number(this.currentYear) + 1);
  }

  decrementYear() {
    this.currentYear = '' + (Number(this.currentYear) - 1);
  }


  // Form Functions

  onCreateSubmit() {
    const newTarget = new Target();

    newTarget.Athlete_Id = this.route.snapshot.params['user_id'];
    newTarget.Content = this.createTargetForm.value.create_content;
    newTarget.Status = this.createTargetForm.value.create_completed;
    newTarget.Week = this.currentWeek;
    newTarget.Year = this.currentYear;

    this.targetService.createTarget(newTarget.Athlete_Id, newTarget).subscribe(
      (response: BaseResponse) => {
        this.alertService.success(response.Message);
        this.getTargets();
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message);
      }
    );
  }

  onUpdateSubmit() {
    const updatedTarget = new Target();

    updatedTarget.Id = this.currentTarget.Id;
    updatedTarget.Athlete_Id = this.currentTarget.Athlete_Id;
    updatedTarget.Content = this.updateTargetForm.value.update_content;
    updatedTarget.Status = this.updateTargetForm.value.update_completed;
    updatedTarget.Week = this.currentTarget.Week;
    updatedTarget.Year = this.currentTarget.Year;

    const updatedObject = new UpdateObject();
    const userKeys = Object.keys(this.currentTarget);

    let count = 0;

    for (const key of userKeys) {
      if (updatedTarget[key] !== this.currentTarget[key]) {
        updatedObject.MemberKeys.push(key);
        updatedObject.MemberValues.push(updatedTarget[key]);
        count ++;
      }
    }

    if (count !== 0) {
      this.targetService.updateTarget(this.route.snapshot.params['user_id'], this.currentTarget.Id, updatedObject)
        .subscribe(
          (response: BaseResponse) => {
            this.alertService.success(response.Message);
            this.getTargets();
          },
          (error: ErrorResponse) => {
            this.alertService.success(error.error.Message);
          }
        );
    } else {
      this.alertService.error('Nothing To Update!');
    }

  }

  onDeleteTargetClicked() {
    if (confirm('Are you sure you wish to delete this target?')) {
      this.targetService.deleteTarget(this.route.snapshot.params['user_id'], this.currentTarget.Id).subscribe(
        (response: BaseResponse) => {
          this.alertService.success(response.Message);
          this.getTargets();
        },
        (error: ErrorResponse) => {
          this.alertService.error(error.error.Message);
        }
      );
    }
  }
}
