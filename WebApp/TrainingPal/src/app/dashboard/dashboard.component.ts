import { Component, OnInit } from '@angular/core';
import {TrainingLog} from '../_models/training-log.model';
import {Event} from '../_models/event.model';
import {TrainingLogService} from '../_services/training-log.service';
import {AuthService} from '../_services/auth.service';
import * as curWeek from 'current-week-number';
import {TrainingLogsResponse} from '../_models/response/training-logs-response.model';
import {ErrorResponse} from '../_models/response/error-response.model';
import {Router} from '@angular/router';
import {EventService} from "../_services/event.service";
import {EventsResponse} from "../_models/response/events-response.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  todaysEntries: TrainingLog[] = [];
  thisWeeksEntries: TrainingLog[] = [];
  thisMonthsEntries: TrainingLog[] = [];

  hostedEvents: Event[] = [];
  attendingEvents: Event[] = [];

  isUserAthlete: boolean;

  currentUser: string;

  constructor(
    private trainingLogService: TrainingLogService,
    private authService: AuthService,
    private router: Router,
    private eventService: EventService
  ) { }

  ngOnInit() {

    const date = new Date();
    const today = String(date.getDate());
    const thisWeek = this.getWeekIndex(curWeek(date) - 1);
    const month = this.getMonthIndex(date.getMonth());
    const year = String(date.getFullYear());
    this.currentUser = this.authService.getCurrentAuthObject().UserId;
    this.isUserAthlete = this.authService.isUserAthlete();

    this.eventService.getHostedEvents().subscribe(
      (response: EventsResponse) => {
        this.hostedEvents = response.Data.Events;
      },
      (error: ErrorResponse) => {
        this.hostedEvents = [];
      }
    );

    this.eventService.getAttendingEvents().subscribe(
      (response: EventsResponse) => {
        this.attendingEvents = response.Data.Events;
      },
      (error: ErrorResponse) => {
        this.attendingEvents = [];
      }
    );

    if (this.isUserAthlete) {
      this.trainingLogService.getTrainingLogsByDay(today, month, year, this.currentUser).subscribe(
        (response: TrainingLogsResponse) => {
          this.todaysEntries = response.Data.TrainingLogs;
        },
        (error: ErrorResponse) => {
          console.error(error.error.Message);
        }
      );

      this.trainingLogService.getTrainingLogsByWeek(thisWeek, year, this.currentUser).subscribe(
        (response: TrainingLogsResponse) => {
          this.thisWeeksEntries = response.Data.TrainingLogs;
        },
        (error: ErrorResponse) => {
          console.error(error.error.Message);
        }
      );

      this.trainingLogService.getTrainingLogsByMonth(month, year, this.currentUser).subscribe(
        (response: TrainingLogsResponse) => {
          this.thisMonthsEntries = response.Data.TrainingLogs;
        },
        (error: ErrorResponse) => {
          console.error(error.error.Message);
        }
      );
    }
  }

  onLogClicked(Id: Number | String) {
    this.router.navigate(['training-log/', this.currentUser, Id]);
  }

  onEventClicked(event: Event) {
    this.router.navigate(['event', event.Id]);
  }

  private getWeekIndex(week: number) {
    if (week > 0 && week < 10 ) {
      return '0' + week;
    } else {
      return '' + week;
    }
  }

  private getMonthIndex(month: number) {
    const months = [
      '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'
    ];

    return months[month];
  }

}
