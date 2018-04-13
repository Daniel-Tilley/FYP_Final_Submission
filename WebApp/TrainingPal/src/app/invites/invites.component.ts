import { Component, OnInit } from '@angular/core';
import {AlertService} from '../_services/alert.service';
import {Router} from '@angular/router';
import {InviteService} from '../_services/invite.service';
import {Invite} from '../_models/invite.model';
import {InvitesResponse} from '../_models/response/invites-response.model';
import {ErrorResponse} from '../_models/response/error-response.model';
import {UpdateObject} from '../_models/update_object.model';
import {BaseResponse} from '../_models/response/base-response.model';
import {AccessService} from '../_services/access.service';
import {EventService} from "../_services/event.service";

@Component({
  selector: 'app-invites',
  templateUrl: './invites.component.html',
  styleUrls: ['./invites.component.css']
})
export class InvitesComponent implements OnInit {

  invites: Invite[] = [];
  currentInvite: Invite;

  constructor(
    private alertService: AlertService,
    private router: Router,
    private inviteService: InviteService,
    private accessService: AccessService,
    private eventService: EventService
  ) { }

  ngOnInit() {
    this.getInvites();
  }

  getInvites() {
    this.inviteService.getReceivedInvites().subscribe(
      (response: InvitesResponse) => {
        this.invites = response.Data.Invites;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);

        this.invites = [];
      }
    );
  }

  onInviteClicked(invite: Invite) {
    this.currentInvite = invite;
    this.alertService.clear();
  }

  onCloseClicked() {
    if (this.currentInvite.Status.toString() === '1') {
      const updateObject = new UpdateObject();

      updateObject.MemberKeys = ['Status'];
      updateObject.MemberValues = ['2'];

      this.inviteService.updateInvite(updateObject, this.currentInvite.Id).subscribe(
        () => {
          this.getInvites();
          this.inviteService.informSubscribers();
        }
      );
    }
  }

  onAcceptClicked() {
    const inviteUpdateObject = new UpdateObject();

    inviteUpdateObject.MemberKeys = ['Status'];
    inviteUpdateObject.MemberValues = ['4'];

    this.inviteService.updateInvite(inviteUpdateObject, this.currentInvite.Id).subscribe(
      (respone: BaseResponse) => {
        this.getInvites();
        if (this.currentInvite.Status.toString() === '1') {
          this.inviteService.informSubscribers();
        }

        // If one to one or video conference request
        if (this.currentInvite.Invite_Type.toString() === '1' || this.currentInvite.Invite_Type.toString() === '2') {
            this.eventService.acceptParticipants(this.currentInvite.Event_Id).subscribe(
              (response: BaseResponse) => {},
              (error: ErrorResponse) => {
                console.error(error.error.Message);
              }
            );
        }

        // If its coach data access request
        if (this.currentInvite.Invite_Type.toString() === '3') {
          const accessUpdateObject = new UpdateObject();

          accessUpdateObject.MemberKeys = ['Is_Active'];
          accessUpdateObject.MemberValues = ['1'];

          this.accessService.updateCoachAccessFromInvite(this.currentInvite.Id, accessUpdateObject).subscribe(
            () => {},
            (error: ErrorResponse) => {
              console.error(error.error.Message);
            }
          );
        }
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
        this.alertService.error('Unable to Accept Invite');
      }
    );
  }

  onDeclineClicked() {
    const updateObject = new UpdateObject();

    updateObject.MemberKeys = ['Status'];
    updateObject.MemberValues = ['3'];

    this.inviteService.updateInvite(updateObject, this.currentInvite.Id).subscribe(
      () => {
        this.getInvites();
        if (this.currentInvite.Status.toString() === '1') {
          this.inviteService.informSubscribers();
        }

        // If one to one or video conference request
        if (this.currentInvite.Invite_Type.toString() === '1' || this.currentInvite.Invite_Type.toString() === '2') {
          this.eventService.removeParticipants(this.currentInvite.Event_Id, [this.currentInvite.Sent_To]).subscribe(
            (response: BaseResponse) => {},
            (error: ErrorResponse) => {
              console.error(error.error.Message);
            }
          );
        }

        // If its coach data access request
        if (this.currentInvite.Invite_Type.toString() === '3') {

          this.accessService.revokeCoachAccess(this.currentInvite.Sent_By).subscribe(
            () => {},
            (error: ErrorResponse) => {
              console.error(error.error.Message);
            }
          );
        }
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
        this.alertService.error('Unable to Decline Invite');
      }
    );
  }

}
