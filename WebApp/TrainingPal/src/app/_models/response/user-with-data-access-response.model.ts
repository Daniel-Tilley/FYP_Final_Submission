import {BaseResponse} from './base-response.model';
import {UserWithDataAccess} from '../user-with-data-access.model';

export class UserWithDataAccessResponse extends  BaseResponse {
  Data: {
    User: UserWithDataAccess
  };
}
