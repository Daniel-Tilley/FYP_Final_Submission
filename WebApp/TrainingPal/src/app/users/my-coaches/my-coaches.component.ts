import {Component, OnInit} from '@angular/core';
import {UserWithDataAccess} from '../../_models/user-with-data-access.model';
import {UserService} from '../../_services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UsersWithDataAccessResponse} from '../../_models/response/users-with-data-access-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../../_services/alert.service';
import {Access} from '../../_models/access.model';
import {AccessService} from '../../_services/access.service';
import {BaseResponse} from '../../_models/response/base-response.model';
declare const jquery: any;
declare const $: any;

@Component({
  selector: 'app-my-coaches',
  templateUrl: '../users.component.html',
  styleUrls: ['./my-coaches.component.css']
})
export class MyCoachesComponent implements OnInit {

  users: UserWithDataAccess[] = [];
  userMessage: string;
  noUserMessage: string;
  pageName: string;

  coachAccessForm: FormGroup;

  currentUser: UserWithDataAccess;

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private alertService: AlertService,
    private accessService: AccessService
  ) { }

  ngOnInit() {
    this.pageName = 'my-coaches';
    this.userMessage = 'Your Coaches';
    this.noUserMessage = 'No Coaches Found!';

    this.coachAccessForm = new FormGroup({
      'training_log_access': new FormControl(null, [Validators.required]),
      'target_access': new FormControl(null, [Validators.required])
    });

    this.getData();
  }

  getData() {
    this.userService.getAthleteCoaches().subscribe(
      (response: UsersWithDataAccessResponse) => {
        this.users = response.Data.Users;

        if (this.route.snapshot.queryParams['coach']) {

          const singleUserArray = this.users.filter((item) => {
            return item.Id === this.route.snapshot.queryParams['coach'];
          });

          if (singleUserArray.length > 0) {
           this.onUserClicked(singleUserArray[0]);
            $('#modalCoachAccess').modal('show');
          }
        }
      },
      (error: ErrorResponse) => {
        this.users = [];
        console.error(error.error.Message);
      }
    );
  }

  onUserClicked(user: UserWithDataAccess) {
    this.alertService.clear();

    this.currentUser = user;

    this.coachAccessForm.patchValue({
      'training_log_access': (user.Can_Access_Training_Log.toString()) === '1',
      'target_access': (user.Can_Access_Targets.toString()) === '1'
    });
  }

  onUpdateClicked() {
    const trainingLogAccessFormValue = this.coachAccessForm.value.training_log_access === true ? '1' : '0';
    const targetAccessFormValue = this.coachAccessForm.value.target_access === true ? '1' : '0';

    if (
      trainingLogAccessFormValue === this.currentUser.Can_Access_Training_Log.toString() &&
        targetAccessFormValue === this.currentUser.Can_Access_Targets.toString()
    ) {
      this.alertService.error('Nothing to Update!');
    } else {

      const updatedAccess = new Access();

      updatedAccess.Can_Access_Targets = Number(targetAccessFormValue);
      updatedAccess.Can_Access_Training_Log = Number(trainingLogAccessFormValue);

      this.accessService.updateCoachAccess(this.currentUser.Id, updatedAccess).subscribe(
        (response: BaseResponse) => {
          this.alertService.success(response.Message);
          this.getData();
        },
        (error: ErrorResponse) => {
          this.alertService.error(error.error.Message);
        }
      );
    }
  }

  onProfileClicked() {
    this.router.navigate(['user', this.currentUser.Id]);
  }

  onRevokeClicked() {
    if (confirm('Are you sure you wish to revoke access?')) {
      this.accessService.revokeCoachAccess(this.currentUser.Id).subscribe(
        (response: BaseResponse) => {
          this.alertService.success(response.Message);
          this.getData();
        },
        (error: ErrorResponse) => {
          this.alertService.error(error.error.Message);
        }
      );
    }
  }

  clearParams() {
    if (this.route.snapshot.queryParams['coach']) {
      this.router.navigate(['my-coaches']);
    }
  }

}
