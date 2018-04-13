import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppConstants} from '../../shared/app.constants';
import {User} from '../../_models/user.model';
import {UserService} from '../../_services/user.service';
import {Router} from '@angular/router';
import {AlertService} from '../../_services/alert.service';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {BaseResponse} from '../../_models/response/base-response.model';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  signUpForm: FormGroup;
  locations: string[];
  userTypes: object[];
  today: string;

  constructor(private router: Router, private userService: UserService, private alertService: AlertService) {
    this.locations = AppConstants.COUNTRY_LIST;
    this.userTypes = AppConstants.USER_TYPES;
  }

  ngOnInit() {
    this.today = new Date().toISOString().slice(0, 10);

    this.signUpForm = new FormGroup({
      'username': new FormControl(null, [Validators.required]),
      'password': new FormControl(null, [Validators.required]),
      'firstName': new FormControl(null, [Validators.required]),
      'lastName': new FormControl(null, [Validators.required]),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'dob': new FormControl(null, [Validators.required, this.dobValidator.bind(this)]),
      'userType': new FormControl(null, [Validators.required]),
      'location': new FormControl(null, [Validators.required]),
      'bio': new FormControl(null)
    });
  }

  onSubmit() {
    const user = new User();

    user.Id = this.signUpForm.value.username;
    user.Password = this.signUpForm.value.password;
    user.F_Name = this.signUpForm.value.firstName;
    user.L_Name = this.signUpForm.value.lastName;
    user.E_Mail = this.signUpForm.value.email;
    user.DOB = this.signUpForm.value.dob;
    user.Type = this.signUpForm.value.userType;
    user.Location = this.signUpForm.value.location;
    user.Bio = (this.signUpForm.value.bio !== null) ? this.signUpForm.value.bio : null;


    this.userService.createUser(user)
      .subscribe(
        (response: BaseResponse) => {
          this.alertService.success(response.Message, true);
          this.router.navigate(['/login']);
        },
        (response: ErrorResponse) => {
          this.alertService.error(response.error.Message);
        }
      );
  }

  dobValidator(control: FormControl): {[s: string]: boolean} {
    const now = new Date();
    const dob = new Date(control.value);
    if (now <= dob) {
      return {'invalidDOB': true};
    }
    return null;
  }
}
