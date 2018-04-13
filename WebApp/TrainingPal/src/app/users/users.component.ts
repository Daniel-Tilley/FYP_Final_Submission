import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {User} from '../_models/user.model';
import {UserService} from '../_services/user.service';
import {ErrorResponse} from '../_models/response/error-response.model';
import {AuthService} from '../_services/auth.service';
import 'rxjs/add/operator/filter';
import {UsersResponse} from '../_models/response/users-response.model';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  noUserMessage: string;
  userMessage: string;
  searchType: string;
  pageName: string;

  users: User[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService, private userService: UserService) { }

  ngOnInit() {
    this.pageName = 'search';

    this.router.events.filter(event => event instanceof NavigationEnd)
      .subscribe(event => {
        this.getUsers(this.route.snapshot.queryParams['search']);
      });

    // Get users
    if (this.authService.isUserAthlete()) {
      this.userMessage = 'Coaches';
      this.noUserMessage = 'No Coaches Found!';
      this.searchType = 'coach';
    }

    if (this.authService.isUserCoach()) {
      this.userMessage = 'Athletes';
      this.searchType = 'athlete';
      this.noUserMessage = 'No Athletes Found!';
    }

    this.getUsers(this.route.snapshot.queryParams['search']);
  }

  getUsers(search: string) {
    this.userService.getUsers(search, this.searchType).subscribe(
      (response: UsersResponse) => {
        this.users = response.Data.Users;
      },
      (error: ErrorResponse) => {
        this.users = [];
        console.error(error.error.Message);
      }
    );
  }

  onUserClicked(id: String) {
    this.router.navigate(['user', id]);
  }
}
