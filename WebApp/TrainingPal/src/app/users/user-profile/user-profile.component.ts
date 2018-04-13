import { Component, OnInit } from '@angular/core';
import {UserService} from '../../_services/user.service';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../_services/auth.service';
import {UserWithDataAccess} from '../../_models/user-with-data-access.model';
import {UserWithDataAccessResponse} from '../../_models/response/user-with-data-access-response.model';
import {AccessService} from '../../_services/access.service';
import {AccessResponse} from '../../_models/response/access-response.model';
import {Access} from '../../_models/access.model';
import {AlertService} from '../../_services/alert.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {BaseResponse} from '../../_models/response/base-response.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  username: string;

  access: Access;
  user: UserWithDataAccess;

  showAthleteOptions: boolean;
  showCoachOptions: boolean;
  hasTrainingLogAccess: boolean;
  hasTargetsAccess: boolean;
  noAccess: boolean;
  requestedAccess: boolean;

  accessForm: FormGroup;

  constructor(
    private userService: UserService,
    private accessService: AccessService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.username = this.route.snapshot.params['user_id'];

    this.accessForm = new FormGroup({
      'training_log_access': new FormControl(null, [Validators.required]),
      'target_access': new FormControl(null, [Validators.required])
    });

    this.accessForm.patchValue({
      'training_log_access': false,
      'target_access': false
    });

    if (this.username.toString() === this.authService.getCurrentAuthObject().UserId) {
      this.router.navigate(['my-profile']);
    } else {
      this.getUser();
    }
  }

  getUser() {
    this.userService.getUser(this.username)
      .subscribe(
        (response: UserWithDataAccessResponse) => {
          this.user = response.Data.User;
          this.checkAccess();
        },
        (response: ErrorResponse) => {
          this.router.navigate(['not-found']);
        }
      );
  }

  checkAccess() {

    if (this.authService.isUserCoach() && this.user.Type === 'ATH') {

      this.getCoachAccess();

      this.showAthleteOptions = false;
      this.showCoachOptions = true;

    } else if (this.authService.isUserAthlete() && this.user.Type === 'COA') {

      this.getAthleteAccess();

      this.showAthleteOptions = true;
      this.showCoachOptions = false;

    } else {

      this.showAthleteOptions = false;
      this.showCoachOptions = false;

    }
  }

  getCoachAccess() {
    this.accessService.checkCoachAccess(this.username).subscribe(
      (response: AccessResponse) => {
        this.access = response.Data.Access;

        this.hasTrainingLogAccess = this.access.Can_Access_Training_Log.toString() === '1';
        this.hasTargetsAccess = this.access.Can_Access_Targets.toString() === '1';
        this.requestedAccess = this.access.Is_Active.toString() === '0';
        this.noAccess = false;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);

        this.hasTrainingLogAccess = false;
        this.hasTargetsAccess = false;
        this.requestedAccess = false;
        this.noAccess = true;
      }
    );
  }

  getAthleteAccess() {
    this.accessService.checkAthleteAccess(this.username).subscribe(
      (response: AccessResponse) => {
        this.access = response.Data.Access;

        this.hasTrainingLogAccess = this.access.Can_Access_Training_Log.toString() === '1';
        this.hasTargetsAccess = this.access.Can_Access_Targets.toString() === '1';
        this.requestedAccess = this.access.Is_Active.toString() === '0';
        this.noAccess = false;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);

        this.hasTrainingLogAccess = false;
        this.hasTargetsAccess = false;
        this.requestedAccess = false;
        this.noAccess = true;
      }
    );
  }

  // Button Clicks
  clearAlert() {
    this.alertService.clear();
  }


  onGrantAccessClicked() {
    const newAccess = new Access();

    newAccess.Can_Access_Training_Log = (this.accessForm.value.training_log_access ? 1 : 0);
    newAccess.Can_Access_Targets = (this.accessForm.value.target_access ? 1 : 0);
    newAccess.Is_Active = 1;
    newAccess.Start_Date = new Date().toISOString().slice(0, 10);
    newAccess.Invite_Id = null;

    this.accessService.grantCoachAccess(this.username, newAccess).subscribe(
      (response: BaseResponse) => {
        this.alertService.success(response.Message);
        this.getUser();
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message);
      }
    );
  }

  onRequestAccessClicked() {
    const newAccess = new Access();
    newAccess.Can_Access_Training_Log = (this.accessForm.value.training_log_access ? 1 : 0);
    newAccess.Can_Access_Targets = (this.accessForm.value.target_access ? 1 : 0);
    newAccess.Is_Active = 0;
    newAccess.Start_Date = new Date().toISOString().slice(0, 10);
    newAccess.Invite_Id = null;

    this.accessService.requestAthleteAccess(this.username, newAccess).subscribe(
      (response: BaseResponse) => {
        this.alertService.success('Access has been requested!');
        this.getUser();
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message);
      }
    );
  }

  onTrainingLogClicked() {
    this.router.navigate(['training-log', this.username]);
  }

  onTargetsClicked() {
    this.router.navigate(['targets', this.username]);
  }

  onEditAccessClicked() {
    this.router.navigate(['my-coaches'], {queryParams: { coach: this.username }});
  }
}
