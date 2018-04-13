import { Component, OnInit } from '@angular/core';
import {EventService} from '../../_services/event.service';
import {Router} from '@angular/router';
import {Event} from '../../_models/event.model';
import {EventsResponse} from '../../_models/response/events-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {BaseResponse} from '../../_models/response/base-response.model';
import {AlertService} from '../../_services/alert.service';

@Component({
  selector: 'app-hosting',
  templateUrl: './hosting.component.html',
  styleUrls: ['./hosting.component.css']
})
export class HostingComponent implements OnInit {

  events: Event[] = [];
  currentEvent: Event;

  constructor(
    private eventService: EventService,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.getEvents();
  }

  getEvents() {
    this.eventService.getHostedEvents().subscribe(
      (response: EventsResponse) => {
        this.events = response.Data.Events;
      },
      (error: ErrorResponse) => {
        this.events = [];
      }
    );
  }

  onEventClicked(event: Event) {
    this.currentEvent = event;
  }

  goToEvent() {
    this.router.navigate(['event', this.currentEvent.Id]);
  }

  onDeleteClicked() {
    const attendeeList = this.currentEvent.Attendees.map((item) => {
      return item.User_ID;
    });

    if (attendeeList.length !== 0) {
      this.eventService.removeParticipants(this.currentEvent.Id, attendeeList).subscribe(
        (response: BaseResponse) => {
        },
        (error: ErrorResponse) => {
          console.error(error.error.Message);
        }
      );
    }

    this.eventService.deleteEvent(this.currentEvent.Id).subscribe(
      (response: BaseResponse) => {
        this.alertService.success('Event Deleted!');
        this.getEvents();
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );
  }
}
