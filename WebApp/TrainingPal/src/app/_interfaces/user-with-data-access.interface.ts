import {UserInterface} from './user.interface';

export interface UserWithDataAccessInterface extends UserInterface {
  Can_Access_Targets: number;
  Can_Access_Training_Log: number;
}
