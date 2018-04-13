import {UserInterface} from '../../_interfaces/user.interface';
import {BaseResponse} from './base-response.model';

export class UsersResponse extends  BaseResponse {
  Data: {
    Users: UserInterface[]
  };
}
