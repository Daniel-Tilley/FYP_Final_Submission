
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from '../../_guards/auth.guard';
import {DashboardComponent} from '../../dashboard/dashboard.component';
import {LoginComponent} from '../../auth/login/login.component';
import {SignUpComponent} from '../../auth/sign-up/sign-up.component';
import {UserProfileComponent} from '../../users/user-profile/user-profile.component';
import {ErrorComponent} from '../../shared/error/error.component';
import {EditUserProfileComponent} from '../../users/myprofile/edit-user/edit-user-profile/edit-user-profile.component';
import {EditUserPasswordComponent} from '../../users/myprofile/edit-user/edit-user-password/edit-user-password.component';
import {EditUserComponent} from '../../users/myprofile/edit-user/edit-user.component';
import {TrainingLogsComponent} from '../../training-logs/training-logs.component';
import {TrainingLogCalendarViewComponent} from '../../training-logs/training-log-calendar-view/training-log-calendar-view.component';
import {TrainingLogListViewComponent} from '../../training-logs/training-log-list-view/training-log-list-view.component';
import {EditTrainingLogComponent} from '../../training-logs/edit-training-log/edit-training-log.component';
import {CreateTrainingLogComponent} from '../../training-logs/create-training-log/create-training-log.component';
import {TrainingLogViewComponent} from '../../training-logs/training-log-view/training-log-view.component';
import {UsersComponent} from '../../users/users.component';
import {CoachGuard} from '../../_guards/coach.guard';
import {TrainingLogTemplatesComponent} from '../../training-log-templates/training-log-templates.component';
import {TrainingLogTemplateViewComponent} from '../../training-log-templates/training-log-template-view/training-log-template-view.component';
import {TrainingLogTemplateListComponent} from '../../training-log-templates/training-log-template-list/training-log-template-list.component';
import {EditTrainingLogTemplateComponent} from '../../training-log-templates/edit-training-log-template/edit-training-log-template.component';
import {CreateTrainingLogTemplateComponent} from '../../training-log-templates/create-training-log-template/create-training-log-template.component';
import {MyAthletesComponent} from '../../users/my-athletes/my-athletes.component';
import {MyCoachesComponent} from '../../users/my-coaches/my-coaches.component';
import {AthleteGuard} from '../../_guards/athlete.guard';
import {TargetsComponent} from '../../targets/targets.component';
import {TargetsAccessGuard} from '../../_guards/targets-access.guard';
import {TrainingLogAccessGuard} from '../../_guards/training-log-access.guard';
import {MyProfileComponent} from '../../users/myprofile/myprofile.component';
import {InvitesComponent} from '../../invites/invites.component';
import {EventsComponent} from '../../events/events.component';
import {EventComponent} from '../../events/event/event.component';
import {EventGuard} from '../../_guards/event.guard';
import {HostingComponent} from '../../events/hosting/hosting.component';
import {AttendingComponent} from '../../events/attending/attending.component';
import {GraphsComponent} from '../../graphs/graphs.component';


const appRoutes: Routes = [
  { path: '' , canActivate: [AuthGuard], component: DashboardComponent },

  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },

  { path: 'my-profile', component: MyProfileComponent, canActivate: [AuthGuard] },
  { path: 'my-profile/edit',
    component: EditUserComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'profile', pathMatch: 'full' },
      { path: 'profile', component: EditUserProfileComponent},
      { path: 'password', component: EditUserPasswordComponent },
    ]
  },

  { path: 'user', redirectTo: 'not-found', canActivate: [AuthGuard] },
  { path: 'user/:user_id', component: UserProfileComponent, canActivate: [AuthGuard] },

  { path: 'my-athletes', component: MyAthletesComponent, canActivate: [AuthGuard, CoachGuard] },
  { path: 'my-coaches', component: MyCoachesComponent, canActivate: [AuthGuard, AthleteGuard] },
  { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },

  { path: 'targets', redirectTo: 'not-found', canActivate: [AuthGuard] },
  { path: 'targets/:user_id', component: TargetsComponent, canActivate: [AuthGuard, TargetsAccessGuard] },

  { path: 'templates',
    component: TrainingLogTemplatesComponent,
    canActivate: [AuthGuard, CoachGuard],
    children: [
      { path: '', component: TrainingLogTemplateListComponent },
      { path: 'create', component: CreateTrainingLogTemplateComponent },
      { path: ':template_id', component: TrainingLogTemplateViewComponent},
      { path: ':template_id/edit', component: EditTrainingLogTemplateComponent},
    ]
  },

  { path: 'training-log', redirectTo: 'not-found', canActivate: [AuthGuard] },
  { path: 'training-log/:user_id',
    component: TrainingLogsComponent,
    canActivate: [AuthGuard, TrainingLogAccessGuard],
    children: [
      { path: '', redirectTo: 'calendar', pathMatch: 'full' },
      { path: 'calendar', component: TrainingLogCalendarViewComponent },
      { path: 'list', component: TrainingLogListViewComponent },
      { path: 'create', component: CreateTrainingLogComponent },
      { path: ':log_id', component: TrainingLogViewComponent },
      { path: ':log_id/edit', component: EditTrainingLogComponent }
    ]
  },

  { path: 'my-graphs', component: GraphsComponent, canActivate: [AuthGuard, AthleteGuard] },

  { path: 'my-invites', component: InvitesComponent, canActivate: [AuthGuard] },

  { path: 'my-events',
    component: EventsComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'hosting', pathMatch: 'full' },
      { path: 'hosting', component: HostingComponent },
      { path: 'attending', component: AttendingComponent }
    ]},
  { path: 'event', redirectTo: 'not-found', canActivate: [AuthGuard] },
  { path: 'event/:event_id', component: EventComponent, canActivate: [AuthGuard, EventGuard] },


  { path: 'not-found', component: ErrorComponent, data: { message: 'Page not found!'} },
  { path: '**', redirectTo: '/not-found' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
