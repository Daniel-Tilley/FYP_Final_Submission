import {BaseResponse} from './base-response.model';
import {User} from '../user.model';

export class UserResponse extends BaseResponse {
  Data: {
    User: User
  };
}
