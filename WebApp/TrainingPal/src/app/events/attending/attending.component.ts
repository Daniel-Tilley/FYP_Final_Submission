import { Component, OnInit } from '@angular/core';
import {EventService} from '../../_services/event.service';
import {Router} from '@angular/router';
import {Event} from '../../_models/event.model';
import {EventsResponse} from '../../_models/response/events-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';

@Component({
  selector: 'app-attending',
  templateUrl: './attending.component.html',
  styleUrls: ['./attending.component.css']
})
export class AttendingComponent implements OnInit {

  events: Event[] = [];
  currentEvent: Event;

  constructor(
    private eventService: EventService,
    private router: Router
  ) { }

  ngOnInit() {
    this.eventService.getAttendingEvents().subscribe(
      (response: EventsResponse) => {
        this.events = response.Data.Events;
      },
      (error: ErrorResponse) => {
        this.events = [];
      }
    );
  }

  onEventClicked(event: Event) {
    this.router.navigate(['event', event.Id]);
  }

}
