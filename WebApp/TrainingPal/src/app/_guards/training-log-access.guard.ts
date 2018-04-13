import {
  ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot
} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {AccessService} from '../_services/access.service';
import {ErrorResponse} from '../_models/response/error-response.model';
import {BaseResponse} from '../_models/response/base-response.model';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/observable/of';

@Injectable()
export class TrainingLogAccessGuard implements CanActivate, CanActivateChild {

  constructor(
    private authService: AuthService,
    private router: Router,
    private accessService: AccessService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const userId = route.params['user_id'];

    if (userId !== this.authService.getCurrentAuthObject().UserId) {
      if (this.authService.isUserCoach()) {
        return this.accessService.checkCoachTrainingLogAccess(userId).map(
          (res: BaseResponse) => {
            return true;
          }
        ).catch((err: ErrorResponse) => {
          this.router.navigate(['not-found']);
          return Observable.of(false);
        });
      } else {
        this.router.navigate(['not-found']);
        return false;
      }
    } else {
      if (this.authService.isUserCoach()) {
        this.router.navigate(['not-found']);
        return false;
      } else {
        return true;
      }
    }
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.canActivate(childRoute, state);
  }
}
