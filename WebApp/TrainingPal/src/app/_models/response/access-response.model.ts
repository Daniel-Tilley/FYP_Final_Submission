import {BaseResponse} from './base-response.model';
import {Access} from '../access.model';

export class AccessResponse extends BaseResponse {
  Data: {
    Access: Access
  };
}
