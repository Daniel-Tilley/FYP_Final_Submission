import {BaseResponse} from './base-response.model';
import {UserWithDataAccessInterface} from '../../_interfaces/user-with-data-access.interface';

export class UsersWithDataAccessResponse extends  BaseResponse {
  Data: {
    Users: UserWithDataAccessInterface[]
  };
}
