import {SharedModule} from '../shared.module';
import {NgModule} from '@angular/core';
import { EventsComponent } from '../../events/events.component';
import { EventComponent } from '../../events/event/event.component';
import {FormsModule} from '@angular/forms';
import { HostingComponent } from '../../events/hosting/hosting.component';
import { AttendingComponent } from '../../events/attending/attending.component';

@NgModule({
  declarations: [
  EventsComponent,
  EventComponent,
  HostingComponent,
  AttendingComponent],
  imports: [
    SharedModule,
    FormsModule
  ]
})
export class EventModule { }
