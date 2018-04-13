import {InviteInterface} from '../_interfaces/invite.interface';

export class Invite implements InviteInterface {
  Id: number;
  Sent_By: string;
  Sent_To: string;
  Status: number;
  Invite_Type: number;
  Send_Date: string;
  Event_Id: number;
}
