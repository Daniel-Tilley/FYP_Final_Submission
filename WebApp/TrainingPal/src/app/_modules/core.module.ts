import {NgModule} from '@angular/core';
import {AppRoutingModule} from './routing/app.routing.module';
import {AuthService} from '../_services/auth.service';
import {AuthGuard} from '../_guards/auth.guard';
import {HttpService} from '../_services/http.service';
import {UserService} from '../_services/user.service';
import {AlertService} from '../_services/alert.service';
import {CurrentUserGuard} from '../_guards/current-user.guard';
import {TrainingLogService} from '../_services/training-log.service';
import {TrainingLogTemplateService} from '../_services/training-log-template.service';
import {AthleteGuard} from '../_guards/athlete.guard';
import {CoachGuard} from '../_guards/coach.guard';
import {TargetService} from '../_services/target.service';
import {AccessService} from '../_services/access.service';
import {TrainingLogAccessGuard} from '../_guards/training-log-access.guard';
import {TargetsAccessGuard} from '../_guards/targets-access.guard';
import {InviteService} from '../_services/invite.service';
import {EventService} from '../_services/event.service';
import {EventGuard} from '../_guards/event.guard';

@NgModule({
  imports: [
    AppRoutingModule
  ],
  exports: [
    AppRoutingModule
  ],
  providers: [
    AccessService,
    AuthService,
    AuthGuard,
    AlertService,
    AthleteGuard,
    CurrentUserGuard,
    CoachGuard,
    EventGuard,
    EventService,
    HttpService,
    InviteService,
    TargetsAccessGuard,
    TargetService,
    TrainingLogAccessGuard,
    TrainingLogService,
    TrainingLogTemplateService,
    UserService
  ]
})
export class CoreModule { }
