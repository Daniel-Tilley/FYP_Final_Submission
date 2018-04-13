import { Component, OnInit } from '@angular/core';
import {UserService} from '../../_services/user.service';
import {UserResponse} from '../../_models/response/user-response.model';
import {User} from '../../_models/user.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.css']
})
export class MyProfileComponent implements OnInit {

  user: User;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.userService.getCurrentUser().subscribe(
      (response: UserResponse) => {
        this.user = response.Data.User;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );
  }

  onEditClicked() {
    this.router.navigate(['my-profile', 'edit']);
  }
}
