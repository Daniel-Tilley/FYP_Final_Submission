import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {HttpService} from './http.service';
import {Request} from '../_models/request.model';
import {Event} from '../_models/event.model';
import {UpdateObject} from '../_models/update_object.model';

@Injectable()
export class EventService {

  baseUrl: string;

  constructor(private httpService: HttpService, private authService: AuthService) {
    this.baseUrl = 'events/';
  }

  createEvent(event: Event) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'event';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = event;

    return this.httpService.post(request);
  }

  deleteEvent(eventId: number) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'event/' + eventId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.delete(request);
  }

  updateEvent(eventId: number, updateData: UpdateObject) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'event/' + eventId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = updateData;

    return this.httpService.put(request);
  }

  getHostedEvents() {
    const request = new Request();
    request.url_extension = this.baseUrl + 'hosted';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getAttendingEvents() {
    const request = new Request();
    request.url_extension = this.baseUrl + 'attending';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getEvent(eventId: number) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'event/' + eventId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  checkAccess(eventId: number) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'access/' + eventId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = event;

    return this.httpService.get(request);
  }

  addParticipants(eventId: number, users: string[]) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'participants/' + eventId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = {Users: users};

    return this.httpService.post(request);
  }

  acceptParticipants(eventId: number) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'participants/' + eventId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.put(request);
  }

  removeParticipants(eventId: number, users: string[]) {
    const request = new Request();
    request.url_extension = this.baseUrl + 'par-delete/' + eventId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    const obj = {Users: users};
    request.body = obj;

    return this.httpService.put(request);
  }
}
