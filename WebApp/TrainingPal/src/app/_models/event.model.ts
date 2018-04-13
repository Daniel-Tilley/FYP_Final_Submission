import {EventInterface} from '../_interfaces/event.interface';

export class Event implements EventInterface {
  Id: number;
  Type: number;
  Name: string;
  Host_Username: string;
  Created_Date: string;
  Event_Date: string;
  Attendees: [{
    Accepted: number;
    User_ID: string;
  }];
}
