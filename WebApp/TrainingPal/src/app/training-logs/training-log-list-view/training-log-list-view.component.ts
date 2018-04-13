import { Component, OnInit } from '@angular/core';
import {TrainingLog} from '../../_models/training-log.model';
import {TrainingLogService} from '../../_services/training-log.service';
import * as curWeek from 'current-week-number';
import {ActivatedRoute, Router} from '@angular/router';
import {TrainingLogsResponse} from '../../_models/response/training-logs-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';

@Component({
  selector: 'app-training-log-list-view',
  templateUrl: './training-log-list-view.component.html',
  styleUrls: ['./training-log-list-view.component.css']
})
export class TrainingLogListViewComponent implements OnInit {

  swimmingWorkouts: TrainingLog[] = [];
  cyclingWorkouts: TrainingLog[] = [];
  runningWorkouts: TrainingLog[] = [];

  constructor(private trainingLogService: TrainingLogService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {

    this.initialise();
  }

  private initialise() {
    const today = new Date();
    const thisWeek = this.getWeekIndex(curWeek(today) - 1);

    // Get swimming workouts
    this.trainingLogService.getTrainingLogsByWeekAndType(thisWeek, String(today.getFullYear()), 2 , this.route.parent.snapshot.params['user_id']).subscribe(
      (response: TrainingLogsResponse) => {
        this.swimmingWorkouts = response.Data.TrainingLogs;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );

    // Get cycling workouts
    this.trainingLogService.getTrainingLogsByWeekAndType(thisWeek, String(today.getFullYear()), 3 , this.route.parent.snapshot.params['user_id']).subscribe(
      (response: TrainingLogsResponse) => {
        this.cyclingWorkouts = response.Data.TrainingLogs;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );

    // Get running workouts
    this.trainingLogService.getTrainingLogsByWeekAndType(thisWeek, String(today.getFullYear()), 4 , this.route.parent.snapshot.params['user_id']).subscribe(
      (response: TrainingLogsResponse) => {
        this.runningWorkouts = response.Data.TrainingLogs;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );
  }

  onLogClicked(Id: Number | String) {
    this.router.navigate(['training-log/', this.route.parent.snapshot.params['user_id'], Id]);
  }

  private getWeekIndex(week: number) {
    if (week > 0 && week < 10 ) {
      return '0' + week;
    } else {
      return '' + week;
    }
  }
}
