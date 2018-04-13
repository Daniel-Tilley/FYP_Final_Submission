import {BaseResponse} from './base-response.model';
import {Event} from '../event.model';

export class EventResponse extends BaseResponse {
  Data: {
    Event: Event
  };
}
