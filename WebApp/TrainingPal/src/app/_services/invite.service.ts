import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {HttpService} from './http.service';
import {Request} from '../_models/request.model';
import {Invite} from '../_models/invite.model';
import {InviteWithRecipients} from '../_models/invite-with-recipients.model';
import {UpdateObject} from '../_models/update_object.model';
import {UpdateInvitesObject} from '../_models/update-invites-object.model';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class InviteService {

  private updateNotification = new Subject<any>();

  constructor(private httpService: HttpService, private authService: AuthService) { }

  informSubscribers() {
    this.updateNotification.next('Update');
  }

  listenForNotifications() {
    return this.updateNotification.asObservable();
  }

  createInvite(invite: Invite) {
    const request = new Request();
    request.url_extension = 'invites/single';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = invite;

    return this.httpService.post(request);
  }

  createMultipeInvite(invite: InviteWithRecipients) {
    const request = new Request();
    request.url_extension = 'invites/multiple';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = invite;

    return this.httpService.post(request);
  }

  updateInvite(updates: UpdateObject, inviteId: number) {
    const request = new Request();
    request.url_extension = 'invites/single/' + inviteId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = updates;

    return this.httpService.put(request);
  }

  updateInvites(updates: UpdateInvitesObject) {
    const request = new Request();
    request.url_extension = 'invites/multiple';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    request.body = updates;

    return this.httpService.put(request);
  }

  declineInvite(inviteId: number) {
    const request = new Request();
    request.url_extension = 'invites/' + inviteId;
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.delete(request);
  }

  getNewInviteCount() {
    const request = new Request();
    request.url_extension = 'invites/new';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getSentInvites() {
    const request = new Request();
    request.url_extension = 'invites/sent';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getReceivedInvites() {
    const request = new Request();
    request.url_extension = 'invites/received';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getAthleteDataAccessInvites() {
    const request = new Request();
    request.url_extension = 'invites/received?type=ath-dat';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getCoachDataAccessInvites() {
    const request = new Request();
    request.url_extension = 'invites/received?type=coa-dat';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getOneToOneInvites() {
    const request = new Request();
    request.url_extension = 'invites/received?type=one-one';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }

  getVideoConferenceInvites() {
    const request = new Request();
    request.url_extension = 'invites/received?type=vid-con';
    request.headers = {
      tokenString: this.authService.getCurrentAuthObject().TokenString,
      basicAuth: null
    };

    return this.httpService.get(request);
  }
}
