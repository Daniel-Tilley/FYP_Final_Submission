import { Component, OnInit } from '@angular/core';
import {UserService} from '../../_services/user.service';
import {Router} from '@angular/router';
import {UserWithDataAccess} from '../../_models/user-with-data-access.model';
import {UsersWithDataAccessResponse} from '../../_models/response/users-with-data-access-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';

@Component({
  selector: 'app-my-athletes',
  templateUrl: '../users.component.html',
  styleUrls: ['./my-athletes.component.css']
})
export class MyAthletesComponent implements OnInit {

  users: UserWithDataAccess[] = [];
  userMessage: string;
  noUserMessage: string;
  pageName: string;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.pageName = 'my-athletes';
    this.userMessage = 'Your Athletes';
    this.noUserMessage = 'No Athletes Found!';

    this.userService.getCoachAthletes().subscribe(
      (response: UsersWithDataAccessResponse) => {
        this.users = response.Data.Users;
      },
      (error: ErrorResponse) => {
        this.users = [];
        console.error(error.error.Message);
      }
    );
  }

  onUserClicked(user: UserWithDataAccess) {
    this.router.navigate(['user', user.Id]);
  }

  onTrainingLogClicked(id: string) {
    this.router.navigate(['training-log', id, 'calendar']);
  }

  onTargetClicked(id: string) {
    this.router.navigate(['targets', id]);
  }
}
