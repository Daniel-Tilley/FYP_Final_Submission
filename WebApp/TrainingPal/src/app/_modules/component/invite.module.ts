import {SharedModule} from '../shared.module';
import {NgModule} from '@angular/core';
import { InvitesComponent } from '../../invites/invites.component';

@NgModule({
  declarations: [
  InvitesComponent
  ],
  imports: [
    SharedModule
  ]
})
export class InviteModule { }

