import {User} from './user.model';
import {UserWithDataAccessInterface} from '../_interfaces/user-with-data-access.interface';

export class UserWithDataAccess extends User implements UserWithDataAccessInterface {
  Can_Access_Targets: number;
  Can_Access_Training_Log: number;
}
