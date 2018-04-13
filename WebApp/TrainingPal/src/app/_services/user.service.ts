import {HttpService} from './http.service';
import {Injectable} from '@angular/core';
import {User} from '../_models/user.model';
import {Request} from '../_models/request.model';
import {AuthService} from './auth.service';
import {UpdateObject} from '../_models/update_object.model';

@Injectable()
export class UserService {
  constructor (private httpService: HttpService, private authService: AuthService) { }

  createUser(user: User) {
    const request = new Request();
    request.url_extension = 'users';
    request.headers = {
      tokenString: null,
      basicAuth: null
    };
    request.body = user;

    return this.httpService.post(request);
  }

  updateUser(user: UpdateObject, username: string) {
    const request = new Request();
    request.url_extension = 'users/' + username;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };
    request.body = user;

    return this.httpService.put(request);
  }

  getCurrentUser() {
    const request = new Request();
    request.url_extension = 'users/' + this.authService.getCurrentAuthObject().UserId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getUser(username: string) {
    const request = new Request();
    request.url_extension = 'users/' + username;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getUsers(searchQuery: string, searchType: string) {
    const request = new Request();
    request.url_extension = 'users/search/' + searchType + '/' + searchQuery;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getAthleteCoaches() {
    const request = new Request();
    request.url_extension = 'users/athlete/coaches';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getCoachAthletes() {
    const request = new Request();
    request.url_extension = 'users/coach/athletes';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }
}
