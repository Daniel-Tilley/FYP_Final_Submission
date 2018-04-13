import { Component, OnInit } from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../_models/user.model';
import {UserService} from '../_services/user.service';
import {UsersResponse} from '../_models/response/users-response.model';
import {ErrorResponse} from '../_models/response/error-response.model';
import {Event} from '../_models/event.model';
import {EventService} from '../_services/event.service';
import {AlertService} from '../_services/alert.service';
import {ActivatedRoute, Router} from '@angular/router';
import {InviteService} from '../_services/invite.service';
import {BaseResponse} from '../_models/response/base-response.model';
import {InviteWithRecipients} from '../_models/invite-with-recipients.model';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  createEventForm: FormGroup;
  today: string;

  isEventOneToOne: boolean;
  isEventVideoCon: boolean;
  isUserCoach: boolean;
  isUserAthlete: boolean;

  eventType: number;

  showRecipients: boolean;
  users: User[] = [];
  oneToOneParticipant: string;
  videoConfParticipants: string[] = [];

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private eventService: EventService,
    private alertService: AlertService,
    private inviteService: InviteService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.today = new Date().toISOString().slice(0, 10);

    this.isUserCoach = this.authService.isUserCoach();
    this.isUserAthlete = this.authService.isUserAthlete();

    this.isEventOneToOne = true;
    this.isEventVideoCon = false;
    this.showRecipients = false;
    this.eventType = 1;

    this.createEventForm = new FormGroup({
      'name': new FormControl(null, [Validators.required]),
      'eventDate': new FormControl(null, [Validators.required, this.eventDateValidator.bind(this)])
    });

    if (this.authService.isUserCoach()) {
      this.userService.getCoachAthletes().subscribe(
        (response: UsersResponse) => {
          this.users = response.Data.Users;
        },
        (error: ErrorResponse) => { }
      );
    }

    if (this.authService.isUserAthlete()) {
      this.userService.getAthleteCoaches().subscribe(
        (response: UsersResponse) => {
          this.users = response.Data.Users;
        },
        (error: ErrorResponse) => { }
      );
    }
  }

  onCreateClicked() {
    const newEvent = new Event();

    newEvent.Type = this.eventType;
    newEvent.Name = this.createEventForm.value.name;
    newEvent.Host_Username = this.authService.getCurrentAuthObject().UserId;
    newEvent.Created_Date = this.today;
    newEvent.Event_Date = this.createEventForm.value.eventDate;
    newEvent.Attendees = null;

    let recipients: string[] = [];

    if (this.eventType.toString() === '1') {
      recipients.push(this.oneToOneParticipant);
    }

    if (this.eventType.toString() === '2') {
      recipients = this.videoConfParticipants.slice(0);
    }

    this.eventService.createEvent(newEvent).subscribe(
      (response: BaseResponse) => {

        if (recipients.length !== 0) {

          const invite = new InviteWithRecipients();

          invite.Sent_By = this.authService.getCurrentAuthObject().UserId;
          invite.Status = 1;
          invite.Invite_Type = this.eventType;
          invite.Send_Date = this.today;
          invite.Event_Id = response.Data;
          invite.Recipients = recipients;

          this.eventService.addParticipants(response.Data, recipients).subscribe(
            (response: BaseResponse) => {

              this.inviteService.createMultipeInvite(invite).subscribe(
                (response: BaseResponse) => {
                  this.alertService.success('Event created and invites sent');
                  this.resetVariables();
                  location.reload();
                },
                (error: ErrorResponse) => {
                  this.alertService.error(error.error.Message);
                  this.resetVariables();
                }
              );
            },
            (error: ErrorResponse) => {
              this.alertService.error(error.error.Message);
            });
        } else {
          this.alertService.success('Event created and no invites sent!');
        }
      },
      (error: ErrorResponse) => {
        this.alertService.error(error.error.Message);
      }
    );
  }

  resetVariables() {
    this.createEventForm.reset();
    this.videoConfParticipants = [];
    this.oneToOneParticipant = '';
    this.eventType = 1;
  }

  switchType() {
    if (this.eventType.toString() === '1') {
      this.isEventOneToOne = true;
      this.isEventVideoCon = false;
    }

    if (this.eventType.toString() === '2') {
      this.isEventOneToOne = false;
      this.isEventVideoCon = true;
    }
  }

  videoConfParticipantSelected(athlete: string, checked: boolean) {
    const userIndex = this.videoConfParticipants.indexOf(athlete);

    if (checked && userIndex === -1) {
      this.videoConfParticipants.push(athlete);
    }

    if (!checked && userIndex !== -1) {
      this.videoConfParticipants.splice(userIndex, 1);
    }
  }

  eventDateValidator(control: FormControl): {[s: string]: boolean} {
    const now = new Date(this.today);
    const eventDate = new Date(control.value);
    if (eventDate < now) {
      return {'invalidEventDate': true};
    }
    return null;
  }
}
