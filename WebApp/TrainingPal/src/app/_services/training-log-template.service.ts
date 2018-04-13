import {HttpService} from './http.service';
import {Injectable} from '@angular/core';
import {Request} from '../_models/request.model';
import {AuthService} from './auth.service';
import {UpdateObject} from '../_models/update_object.model';
import {TrainingLogTemplate} from '../_models/training-log-template.model';

@Injectable()
export class TrainingLogTemplateService {
  constructor (private httpService: HttpService, private authService: AuthService) { }

  createTemplate(trainingLogTemplate: TrainingLogTemplate, username: string) {
    const request = new Request();
    request.url_extension = 'templates/' + username;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };
    request.body = trainingLogTemplate;

    return this.httpService.post(request);
  }

  updateTemplate(trainingLogTemplate: UpdateObject, templateId: number, username: string) {
    const request = new Request();
    request.url_extension = 'templates/' + username + '/' + templateId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };
    request.body = trainingLogTemplate;

    return this.httpService.put(request);
  }

  deleteTemplate(templateId: number, username: string) {
    const request = new Request();
    request.url_extension = 'templates/' + username + '/' + templateId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.delete(request);
  }

  getTemplate(templateId: number, username: string) {
    const request = new Request();
    request.url_extension = 'templates/' + username + '/' + templateId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getTemplates(username: string) {
    const request = new Request();
    request.url_extension = 'templates/' + username;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }
}
