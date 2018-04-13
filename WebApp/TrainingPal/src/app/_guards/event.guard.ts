import {
  ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot
} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {ErrorResponse} from '../_models/response/error-response.model';
import {BaseResponse} from '../_models/response/base-response.model';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/observable/of';
import {EventService} from '../_services/event.service';

@Injectable()
export class EventGuard implements CanActivate, CanActivateChild {

  constructor(
    private router: Router,
    private eventService: EventService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const eventId = route.params['event_id'];

    return this.eventService.checkAccess(eventId).map(
      (res: BaseResponse) => {
        return true;
      }
    ).catch((err: ErrorResponse) => {
      this.router.navigate(['not-found']);
      return Observable.of(false);
    });
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.canActivate(childRoute, state);
  }
}
