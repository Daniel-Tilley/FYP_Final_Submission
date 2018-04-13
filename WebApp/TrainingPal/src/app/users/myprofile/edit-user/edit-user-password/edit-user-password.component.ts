import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../../_services/user.service';
import {UserResponse} from '../../../../_models/response/user-response.model';
import {User} from '../../../../_models/user.model';
import {ErrorResponse} from '../../../../_models/response/error-response.model';
import {AlertService} from '../../../../_services/alert.service';
import {Router} from '@angular/router';
import {UpdateObject} from '../../../../_models/update_object.model';
import {BaseResponse} from '../../../../_models/response/base-response.model';
import {AuthService} from '../../../../_services/auth.service';

@Component({
  selector: 'app-edit-user-password',
  templateUrl: './edit-user-password.component.html',
  styleUrls: ['./edit-user-password.component.css']
})
export class EditUserPasswordComponent implements OnInit {

  userForm: FormGroup;

  private currentUser: User;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private alertService: AlertService,
    private router: Router) { }

  ngOnInit() {

    this.userForm = new FormGroup({
      'password': new FormControl(null, [Validators.required]),
      'passwords': new FormGroup({
        'newPassword': new FormControl(null, [Validators.required]),
        'confirmPassword': new FormControl(null, [Validators.required])
      })
    });

    this.setFormDetails();
  }

  onSubmit() {
    const formCurrentPassword = this.userForm.value.password;
    const formNewPassword = this.userForm.value.passwords.newPassword;
    const formConfirmPassword = this.userForm.value.passwords.confirmPassword;

    this.authService.checkPassword(formCurrentPassword).subscribe(
      (response: BaseResponse) => {
        if (formNewPassword === formConfirmPassword) {
          if (formCurrentPassword !== formNewPassword) {
            this.sendNewPasswordToServer(formNewPassword);
          } else {
            this.alertService.error('Your new password cannot be your existing password!');
          }
        } else {
          this.alertService.error('New password and confirm password do not match!');
        }
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message);
      }
    );
  }

  sendNewPasswordToServer(formNewPassword: string) {
    const updatedObject = new UpdateObject();

    updatedObject.MemberKeys.push('Password');
    updatedObject.MemberValues.push(formNewPassword);

    this.userService.updateUser(updatedObject, this.currentUser.Id)
      .subscribe(
        (response: BaseResponse) => {
          this.alertService.success(response.Message, true);
          this.router.navigate(['my-profile']);
        },
        (response: ErrorResponse) => {
          this.alertService.error(response.error.Message);
        }
      );
  }

  setFormDetails() {
    this.userService.getCurrentUser()
      .subscribe(
        (response: UserResponse) => {
          this.currentUser = response.Data.User;

          this.userForm.setValue({
            password: '',
            passwords: {
              newPassword: '',
              confirmPassword: ''
            }
          });
        },
        (response: ErrorResponse) => {
          this.alertService.error(response.error.Message);
        }
      );
  }
}
