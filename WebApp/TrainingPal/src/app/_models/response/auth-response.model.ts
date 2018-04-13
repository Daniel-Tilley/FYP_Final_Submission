import {AuthObjectModel} from '../auth_object.model';
import {BaseResponse} from './base-response.model';

export class AuthResponse extends BaseResponse {
  Data: {
    Token: AuthObjectModel
  };
}
