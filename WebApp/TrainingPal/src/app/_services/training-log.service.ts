import {HttpService} from './http.service';
import {Injectable} from '@angular/core';
import {Request} from '../_models/request.model';
import {AuthService} from './auth.service';
import {UpdateObject} from '../_models/update_object.model';
import {TrainingLog} from '../_models/training-log.model';

@Injectable()
export class TrainingLogService {
  constructor (private httpService: HttpService, private authService: AuthService) { }

  createTrainingLog(trainingLog: TrainingLog, username: string) {
    const request = new Request();
    request.url_extension = 'training-log/' + username;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };
    request.body = trainingLog;

    return this.httpService.post(request);
  }

  updateTrainingLog(trainingLog: UpdateObject, username: string, workoutId: number) {
    const request = new Request();
    request.url_extension = 'training-log/' + username + '/workout/' + workoutId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };
    request.body = trainingLog;

    return this.httpService.put(request);
  }

  deleteTrainingLog(workoutId: number, username: string) {
    const request = new Request();
    request.url_extension = 'training-log/' + username + '/workout/' + workoutId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.delete(request);
  }

  getTrainingLog(workoutId: number, username: string) {
    const request = new Request();
    request.url_extension = 'training-log/' + username + '/workout/' + workoutId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getTrainingLogsByDay(day: string, month: string, year: string, username: string) {
    const request = new Request();
    request.url_extension = 'training-log/' + username + '/workouts?day=' + day + '&month=' + month + '&year=' + year;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getTrainingLogsByWeek(week: string, year: string, username: string) {
    const request = new Request();
    request.url_extension = 'training-log/' + username + '/workouts?week=' + week + '&year=' + year;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getTrainingLogsByWeekAndType(week: string, year: string, type: number, username: string) {
    const request = new Request();
    request.url_extension = 'training-log/' + username + '/workouts?type=' + type + '&week=' + week + '&year=' + year;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getTrainingLogsByMonth(month: string, year: string, username: string) {
    const request = new Request();
    request.url_extension = 'training-log/' + username + '/workouts?month=' + month + '&year=' + year;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }
}
