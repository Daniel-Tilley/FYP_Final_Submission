import { Component, OnInit } from '@angular/core';
import {CalendarDay} from './calendar-day.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TrainingLogService} from '../../_services/training-log.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TrainingLogsResponse} from '../../_models/response/training-logs-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {TrainingLog} from '../../_models/training-log.model';

@Component({
  selector: 'app-training-log-calendar-view',
  templateUrl: './training-log-calendar-view.component.html',
  styleUrls: ['./training-log-calendar-view.component.css']
})
export class TrainingLogCalendarViewComponent implements OnInit {

  today: Date;
  currentMonthNumber: number;
  currentYearNumber: number;
  calendarWeeks;
  datePicker: FormGroup;

  constructor(private trainingLogService: TrainingLogService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.today = new Date();
    this.currentMonthNumber = this.today.getMonth();
    this.currentYearNumber = this.today.getFullYear();
    this.formatWeeksArray(this.currentYearNumber, this.currentMonthNumber + 1);

    this.datePicker = new FormGroup({
      'month': new FormControl(null, [Validators.required])
    });

    this.setDatePicker();

    this.datePicker.valueChanges.subscribe(
    (value) => {

        if (value.month.length !== 0) {
          const date = new Date(value.month);
          this.currentMonthNumber = date.getMonth();
          this.currentYearNumber = date.getFullYear();
          this.formatWeeksArray(this.currentYearNumber, this.currentMonthNumber + 1);
        }
    });
  }

  setDatePicker() {
    const dateString = this.currentYearNumber + '-' + this.getMonthIndex(this.currentMonthNumber);

    this.datePicker.setValue({
      'month': new Date(dateString).toISOString().slice(0, 7)
    });
  }

  formatWeeksArray(year: number, month_number: number) {
    const firstOfMonth = new Date(year, month_number - 1, 1); // get first day of month as a number
    const lastOfMonth = new Date(year, month_number, 0); // get last day of the month as a number
    const monthDays = (lastOfMonth.getDate() - firstOfMonth.getDate()) + 1; // get the number of months in a day
    const baseWeekDays = (7 - firstOfMonth.getDay()); // get the number of days in the first week
    const numWeeks = Math.ceil((monthDays - baseWeekDays) / 7) + 1; // get the total number of weeks
    const endWeekDays = ((monthDays - baseWeekDays) % 7 === 0) ? 7 : (monthDays - baseWeekDays) % 7; // get the number of days in the end week

    this.trainingLogService.getTrainingLogsByMonth(this.getMonthIndex(this.currentMonthNumber), String(this.currentYearNumber), this.route.parent.snapshot.params['user_id']).subscribe(
      (response: TrainingLogsResponse) => {
        this.setCalendarTable(baseWeekDays, numWeeks, endWeekDays, response.Data.TrainingLogs);
      },
      (error: ErrorResponse) => {
        this.setCalendarTable(baseWeekDays, numWeeks, endWeekDays, null);
        console.error(error.error.Message);
      }
    );
  }

  setCalendarTable(baseWeekDays: number, numWeeks: number, endWeekDays: number, data: TrainingLog[]) {
    const weeks = [];
    let dayCounter = 1; // used for current date

    // loop through the number of weeks in the current month
    for (let i = 0; i < numWeeks; i ++) {

      const week = [];

      // loop through the 7 days in that week
      for (let j = 0; j < 7 ; j ++) {

        const day = new CalendarDay();

        // if it's the first week
        if (i === 0) {

          // check if the current day is actually in the week
          if (j < (7 - baseWeekDays)) {
            day.isActive = false;
          } else {
            day.isActive = true;
            day.DayNumber = dayCounter ++;
            day.DayName = this.GetDayofWeekName(j);

            if (data !== null) {
              for (const entry of data) {

                if (new Date(entry.Log_Date).getDate() === day.DayNumber) {
                  day.Data.push(entry);
                  day.hasData = true;
                }
              }
            }
          }

          // if its the last week
        } else if (i === numWeeks - 1) {

          // check if the day is in the last week
          if (j < endWeekDays) {
            day.isActive = true;
            day.DayNumber = dayCounter ++;
            day.DayName = this.GetDayofWeekName(j);

            if (data !== null) {

              for (const entry of data) {
                if (new Date(entry.Log_Date).getDate() === day.DayNumber) {
                  day.Data.push(entry);
                  day.hasData = true;
                }
              }
            }
          } else {
            day.isActive = false;
          }

          // if its a week in the middle
        } else {
          day.isActive = true;
          day.DayNumber = dayCounter ++;
          day.DayName = this.GetDayofWeekName(j);

          if (data !== null) {
            for (const entry of data) {
              if (new Date(entry.Log_Date).getDate() === day.DayNumber) {
                day.Data.push(entry);
                day.hasData = true;
              }
            }
          }
        }

        week.push(day);
      }

      weeks.push(week);
    }

    this.calendarWeeks = weeks;
  }

  GetDayofWeekName(day: number) {

    const days = [
      'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'
    ];

    return days[day];
  }

  getMonthIndex(month: number) {
    const months = [
      '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'
    ];

    return months[month];
  }

  onNextMonthClicked() {
    if (this.currentMonthNumber === 11) {
      this.currentYearNumber ++;
      this.currentMonthNumber = 0;
      this.setDatePicker();
    } else {
      this.currentMonthNumber ++;
      this.setDatePicker();
    }
  }

  onPreviousMonthClicked() {
    if (this.currentMonthNumber === 0) {
      this.currentYearNumber --;
      this.currentMonthNumber = 11;
      this.setDatePicker();
    } else {
      this.currentMonthNumber --;
      this.setDatePicker();
    }
  }

  onLogClicked(Id: Number | String) {
    this.router.navigate(['training-log/', this.route.parent.snapshot.params['user_id'], Id]);
  }
}
