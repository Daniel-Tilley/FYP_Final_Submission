import {Request} from '../_models/request.model';
import {Injectable, isDevMode} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AppConstants} from '../shared/app.constants';

@Injectable()
export class HttpService {

  private baseURL: string;
  private completeURL: string;

  constructor (private http: HttpClient) {
    if (isDevMode()) {
      this.baseURL = AppConstants.DEV_API_BASE_URL;
    } else {
      this.baseURL = AppConstants.PROD_API_BASE_URL;
    }
  }

  get (data: Request) {
    this.formatUrl(data);

    return this.http.get(this.completeURL, {headers: this.formatHeaders(data)});
  }

  post (data: Request) {
    this.formatUrl(data);

    return this.http.post(this.completeURL, data.body, {headers: this.formatHeaders(data)});
  }

  put (data: Request) {
    this.formatUrl(data);

    return this.http.put(this.completeURL, data.body, {headers: this.formatHeaders(data)});
  }

  delete (data: Request) {
    this.formatUrl(data);

    return this.http.delete(this.completeURL, {headers: this.formatHeaders(data)});
  }

  formatUrl (data: Request) {
    if (data.base_url == null) {
      this.completeURL = this.baseURL + data.url_extension;
    } else {
      this.completeURL = data.base_url + data.url_extension;
    }
  }

  formatHeaders (data: Request) {
    if (data.headers.tokenString !== null) {
      return new HttpHeaders().append(
        'Authorization',
        'Bearer ' + data.headers.tokenString);
    }

    if (data.headers.basicAuth !== null) {
      if (data.headers.basicAuth.username !== null && data.headers.basicAuth.password !== null) {
        const headerString = 'Basic ' + btoa(data.headers.basicAuth.username + ':' + data.headers.basicAuth.password);
        return new HttpHeaders().append('Authorization', headerString);
      }
    }

    return new HttpHeaders();
  }
}
