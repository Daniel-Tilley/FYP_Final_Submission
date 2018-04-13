import {NgModule} from '@angular/core';
import {UserProfileComponent} from '../../users/user-profile/user-profile.component';
import {EditUserProfileComponent} from '../../users/myprofile/edit-user/edit-user-profile/edit-user-profile.component';
import {UsersComponent} from '../../users/users.component';
import {SharedModule} from '../shared.module';
import {EditUserPasswordComponent} from '../../users/myprofile/edit-user/edit-user-password/edit-user-password.component';
import {EditUserComponent} from '../../users/myprofile/edit-user/edit-user.component';
import { MyAthletesComponent } from '../../users/my-athletes/my-athletes.component';
import { MyCoachesComponent } from '../../users/my-coaches/my-coaches.component';
import {FormsModule} from '@angular/forms';
import { MyProfileComponent } from '../../users/myprofile/myprofile.component';

@NgModule({
  declarations: [
    UsersComponent,
    EditUserComponent,
    EditUserPasswordComponent,
    EditUserProfileComponent,
    UserProfileComponent,
    MyAthletesComponent,
    MyCoachesComponent,
    MyProfileComponent
  ],
  imports: [
    SharedModule,
    FormsModule
  ]
})
export class UserModule {}
