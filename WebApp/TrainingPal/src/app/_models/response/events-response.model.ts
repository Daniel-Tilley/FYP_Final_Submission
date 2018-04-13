import {BaseResponse} from './base-response.model';
import {EventInterface} from '../../_interfaces/event.interface';

export class EventsResponse extends BaseResponse {
  Data: {
    Events: EventInterface[]
  };
}
