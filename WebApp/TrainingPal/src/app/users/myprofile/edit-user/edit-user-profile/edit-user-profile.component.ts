import { Component, OnInit } from '@angular/core';
import {AppConstants} from '../../../../shared/app.constants';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../../../../_services/alert.service';
import {UserService} from '../../../../_services/user.service';
import {ErrorResponse} from '../../../../_models/response/error-response.model';
import {User} from '../../../../_models/user.model';
import {UserResponse} from '../../../../_models/response/user-response.model';
import {UpdateObject} from '../../../../_models/update_object.model';
import {BaseResponse} from '../../../../_models/response/base-response.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-edit-user-profile',
  templateUrl: './edit-user-profile.component.html',
  styleUrls: ['./edit-user-profile.component.css']
})
export class EditUserProfileComponent implements OnInit {

  userForm: FormGroup;
  locations: string[];
  userTypes: object[];
  today: string;

  private currentUser: User;

  constructor(
    private userService: UserService,
    private alertService: AlertService,
    private router: Router
  ) {
    this.locations = AppConstants.COUNTRY_LIST;
    this.userTypes = AppConstants.USER_TYPES;
  }

  ngOnInit() {
    this.today = new Date().toISOString().slice(0, 10);

    this.userForm = new FormGroup({
      'username': new FormControl(null, [Validators.required]),
      'firstName': new FormControl(null, [Validators.required]),
      'lastName': new FormControl(null, [Validators.required]),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'dob': new FormControl(null, [Validators.required]),
      'location': new FormControl(null, [Validators.required]),
      'bio': new FormControl(null)
    });

    this.setFormDetails();
  }

  onSubmit() {
    const updatedUser = new User();

    updatedUser.Id = this.userForm.value.username;
    updatedUser.Password = this.currentUser.Password;
    updatedUser.F_Name = this.userForm.value.firstName;
    updatedUser.L_Name = this.userForm.value.lastName;
    updatedUser.E_Mail = this.userForm.value.email;
    updatedUser.DOB = this.userForm.value.dob;
    updatedUser.Type = this.currentUser.Type;
    updatedUser.Location = this.userForm.value.location;
    updatedUser.Bio = (this.userForm.value.bio !== null) ? this.userForm.value.bio : null;

    const updatedObject = new UpdateObject();
    const userKeys = Object.keys(updatedUser);

    let count = 0;

    for (const key of userKeys) {
      if (updatedUser[key] !== this.currentUser[key]) {
        updatedObject.MemberKeys.push(key);
        updatedObject.MemberValues.push(updatedUser[key]);
        count ++;
      }
    }

    if (count !== 0) {
      this.userService.updateUser(updatedObject, updatedUser.Id)
        .subscribe(
          (response: BaseResponse) => {
            this.alertService.success(response.Message, true);
            this.router.navigate(['my-profile']);
          },
          (response: ErrorResponse) => {
            this.alertService.error(response.error.Message);
          }
        );
    } else {
      this.alertService.error('Nothing to update!');
    }
  }

  setFormDetails() {
    this.userService.getCurrentUser()
      .subscribe(
        (response: UserResponse) => {

          let user = new User();
          user = response.Data.User;

          this.userForm.setValue({
            username: user.Id,
            firstName: user.F_Name,
            lastName: user.L_Name,
            email: user.E_Mail,
            dob: new Date(user.DOB).toISOString().slice(0, 10),
            location: user.Location,
            bio: user.Bio
          });

          this.currentUser = user;
          this.currentUser.DOB = new Date(user.DOB).toISOString().slice(0, 10);
        },
        (response: ErrorResponse) => {
          this.alertService.error(response.error.Message);
        }
      );
  }
}
