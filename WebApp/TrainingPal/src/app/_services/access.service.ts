import {AuthService} from './auth.service';
import {HttpService} from './http.service';
import {Injectable} from '@angular/core';
import {Request} from '../_models/request.model';
import {Access} from '../_models/access.model';
import {UpdateObject} from '../_models/update_object.model';

@Injectable()
export class AccessService {
  constructor(private httpService: HttpService, private authService: AuthService) { }

  checkCoachTrainingLogAccess(athleteId: string) {
    const request = new Request();
    request.url_extension = 'access/coach/training-log/' + athleteId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  checkCoachTargetAccess(athleteId: string) {
    const request = new Request();
    request.url_extension = 'access/coach/targets/' + athleteId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  updateCoachAccess(coachId: string, privileges: Access) {
    const request = new Request();
    request.url_extension = 'access/athlete/privileges/' + coachId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = privileges;

    return this.httpService.put(request);
  }

  updateCoachAccessFromInvite(inviteId: number, updateData: UpdateObject) {
    const request = new Request();
    request.url_extension = 'access/athlete/invite/' + inviteId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = updateData;

    return this.httpService.put(request);
  }

  revokeCoachAccess(coachId: string) {
    const request = new Request();
    request.url_extension = 'access/athlete/revoke/' + coachId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.delete(request);
  }

  grantCoachAccess(coachId: string, access: Access) {
    const request = new Request();
    request.url_extension = 'access/athlete/grant/' + coachId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = access;

    return this.httpService.post(request);
  }

  requestAthleteAccess(athleteId: string, access: Access) {
    const request = new Request();
    request.url_extension = 'access/coach/request/' + athleteId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = access;

    return this.httpService.post(request);
  }

  checkAthleteAccess(coachId: string) {
    const request = new Request();
    request.url_extension = 'access/athlete/all/' + coachId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  checkCoachAccess(athleteId: string) {
    const request = new Request();
    request.url_extension = 'access/coach/all/' + athleteId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }
}
