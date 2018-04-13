import {Injectable} from '@angular/core';
import {HttpService} from './http.service';
import {AuthService} from './auth.service';
import {Request} from '../_models/request.model';
import {UpdateObject} from '../_models/update_object.model';
import {Target} from '../_models/target.model';

@Injectable()
export class TargetService {

  baseExtension = 'targets/';

  constructor (private httpService: HttpService, private authService: AuthService) { }

  createTarget(username: string, target: Target) {
    const request = new Request();
    request.url_extension = this.baseExtension + username;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };
    request.body = target;

    return this.httpService.post(request);
  }

  updateTarget(username: string, targetId: number, target: UpdateObject) {
    const request = new Request();
    request.url_extension = this.baseExtension + username + '/' + targetId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };
    request.body = target;

    return this.httpService.put(request);
  }

  deleteTarget(username: string, targetId: number) {
    const request = new Request();
    request.url_extension = this.baseExtension + username + '/' + targetId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.delete(request);
  }

  getTargets(username: string, weekNumber: string, yearNumber: string) {
    const request = new Request();
    request.url_extension = this.baseExtension + username + '/' + weekNumber + '/' + yearNumber;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }
}
