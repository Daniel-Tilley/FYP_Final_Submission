import {UserInterface} from '../_interfaces/user.interface';

export class User implements  UserInterface {
  Id: string;
  Password: string;
  F_Name: string;
  L_Name: string;
  E_Mail: string;
  DOB: string;
  Type: string;
  Location: string;
  Bio: string;
}
