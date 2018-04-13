import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../_services/auth.service';
import {NavigationEnd, Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {InviteService} from '../../_services/invite.service';
import {BaseResponse} from "../../_models/response/base-response.model";
import {ErrorResponse} from "../../_models/response/error-response.model";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  newInvites: number;

  username: string;
  isUserAthlete: boolean;
  isUserCoach: boolean;
  searchBoxPlaceholder: string;

  navbarUserSearch: FormGroup;

  constructor(private authService: AuthService, private router: Router, private inviteService: InviteService) { }

  ngOnInit() {
    this.newInvites = 0;

    this.getNewInvitesCount();

    this.router.events.filter(event => event instanceof NavigationEnd)
      .subscribe(event => {

        if (this.router.url.toString() !== '/login' || this.router.url.toString() !== '/signup') {
          if (!this.authService.checkIfJustLoggedOut()) {
            this.getNewInvitesCount();
          }
        }
      });

    this.inviteService.listenForNotifications().subscribe(
      next => {
        this.getNewInvitesCount();
      }
    );

    const authObject = this.authService.getCurrentAuthObject();

    if ( authObject != null) {
      this.username = authObject.UserId;
    } else {
      this.username = 'Username';
    }

    this.isUserAthlete = this.authService.isUserAthlete();
    this.isUserCoach = this.authService.isUserCoach();

    if (this.isUserCoach) {
      this.searchBoxPlaceholder = 'Find Athletes...';
    }

    if (this.isUserAthlete) {
      this.searchBoxPlaceholder = 'Find Coaches...';
    }

    this.navbarUserSearch = new FormGroup({
      'searchField': new FormControl(null, [Validators.required])
    });
  }

  getNewInvitesCount() {
    this.inviteService.getNewInviteCount().subscribe(
      (response: BaseResponse) => {
        this.newInvites = response.Data;
      },
      (error: ErrorResponse) => {
        this.newInvites = 0;
      }
    );
  }

  onLogoutClick() {
    this.authService.logout();
  }

  onTrainingLogClick() {
    this.router.navigate(['training-log', this.username]);
  }

  onTargetsClick() {
    this.router.navigate(['targets', this.username]);
  }

  onSubmit() {
    const query = this.navbarUserSearch.value.searchField;
    this.navbarUserSearch.reset();
    this.router.navigate(['users'], { queryParams: { search: query }});
  }
}
