import { Component, OnInit } from '@angular/core';
import {TrainingLog} from '../../_models/training-log.model';
import {TrainingLogService} from '../../_services/training-log.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TrainingLogResponse} from '../../_models/response/training-log-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {BaseResponse} from '../../_models/response/base-response.model';
import {AlertService} from '../../_services/alert.service';

@Component({
  selector: 'app-training-log-view',
  templateUrl: './training-log-view.component.html',
  styleUrls: ['./training-log-view.component.css']
})
export class TrainingLogViewComponent implements OnInit {

  trainingLog: TrainingLog;

  constructor(private trainingLogService: TrainingLogService, private route: ActivatedRoute, private router: Router, private alertService: AlertService) { }

  ngOnInit() {
    this.trainingLogService.getTrainingLog(this.route.snapshot.params['log_id'], this.route.parent.snapshot.params['user_id']).subscribe(
      (response: TrainingLogResponse) => {
        this.trainingLog = response.Data.TrainingLog;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );
  }

  onEditClicked() {
    this.router.navigate([this.router.url, 'edit']);
  }

  onDeleteClicked() {
    if (confirm('Are you sure you wish to delete this entry?')) {
      this.trainingLogService.deleteTrainingLog(this.route.snapshot.params['log_id'], this.route.parent.snapshot.params['user_id']).subscribe(
        (response: BaseResponse) => {
          this.alertService.success(response.Message, true);
          this.router.navigate(['training-log', this.route.parent.snapshot.params['user_id'], 'calendar']);
        },
        (error: ErrorResponse) => {
          this.alertService.error(error.error.Message);
        }
      );
    }
  }
}
