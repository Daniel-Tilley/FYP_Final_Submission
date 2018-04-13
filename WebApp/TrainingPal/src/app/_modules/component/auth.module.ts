import {NgModule} from '@angular/core';
import {SignUpComponent} from '../../auth/sign-up/sign-up.component';
import {LoginComponent} from '../../auth/login/login.component';

import {SharedModule} from '../shared.module';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    LoginComponent,
    SignUpComponent
  ],
  imports: [
    SharedModule,
    ReactiveFormsModule
  ]
})
export class AuthModule { }
