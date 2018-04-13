import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {AppConstants} from '../shared/app.constants';
import {HttpService} from './http.service';
import {Request} from '../_models/request.model';
import {AlertService} from './alert.service';
import {AuthResponse} from '../_models/response/auth-response.model';
import {AuthObjectModel} from '../_models/auth_object.model';
import {ErrorResponse} from '../_models/response/error-response.model';

@Injectable()
export class AuthService {
  private isSessionExpired: boolean;
  private hasJustLoggedOut: boolean;

  constructor(private router: Router, private httpService: HttpService, private alertService: AlertService) {
    this.isSessionExpired = false;
    this.hasJustLoggedOut = false;
  }

  isAuthenticated() {
    if (localStorage.getItem(AppConstants.TOKEN_STORAGE_KEY) === null) {
      return false;
    } else {
      const now = new Date();
      const expiryDate = new Date(this.getCurrentAuthObject().ExpiryDate);

      if (now < expiryDate) {
        return true;
      } else {
        this.isSessionExpired = true;
        this.alertService.error('Session has expired, please login again!', true);
        return false;
      }
    }
  }

  login(username: string, password: string) {
    const request = new Request();
    request.url_extension = 'auth/login';
    request.headers = {
      tokenString: null,
      basicAuth: {
        username: username,
        password: password
      }
    };

    this.httpService.get(request)
      .subscribe(
      (response: AuthResponse) => {
        localStorage.setItem(AppConstants.TOKEN_STORAGE_KEY, JSON.stringify(response.Data.Token));
        this.hasJustLoggedOut = false;
        this.router.navigate(['']);
      },
      (response: ErrorResponse) => {
        this.alertService.error(response.error.Message);
      }
    );
  }

  checkPassword(password: string) {
    const request = new Request();
    request.url_extension = 'auth/check-password';
    request.headers = {
      tokenString: this.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = {'Password': password};

    return this.httpService.post(request);
  }

  logout() {
    this.hasJustLoggedOut = true;
    localStorage.removeItem(AppConstants.TOKEN_STORAGE_KEY);
    this.router.navigate(['login']);
  }

  getCurrentAuthObject (): AuthObjectModel {
    const obj = JSON.parse(localStorage.getItem(AppConstants.TOKEN_STORAGE_KEY));

    const authObject = new AuthObjectModel();
    authObject.CreatedDate = obj.CreatedDate;
    authObject.ExpiryDate = obj.ExpiryDate;
    authObject.UserId = obj.UserId;
    authObject.TokenString = obj.TokenString;
    authObject.UserType = obj.UserType;

    return authObject;
  }

  checkIsSessionExpired() {
    return this.isSessionExpired;
  }

  isUserAthlete() {
    if (this.getCurrentAuthObject().UserType === 'ATH') {
      return true;
    } else {
      return false;
    }
  }

  isUserCoach() {
    if (this.getCurrentAuthObject().UserType === 'COA') {
      return true;
    } else {
      return false;
    }
  }

  checkIfJustLoggedOut() {
    return this.hasJustLoggedOut;
  }
}
