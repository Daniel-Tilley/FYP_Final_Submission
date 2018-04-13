import {BaseResponse} from './base-response.model';
import {InviteInterface} from '../../_interfaces/invite.interface';

export class InvitesResponse extends BaseResponse {
  Data: {
    Invites: InviteInterface[]
  };
}
