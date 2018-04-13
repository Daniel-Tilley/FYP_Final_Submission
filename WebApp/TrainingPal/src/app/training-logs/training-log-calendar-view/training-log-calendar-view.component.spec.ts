import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingLogCalendarViewComponent } from './training-log-calendar-view.component';

describe('TrainingLogCalendarViewComponent', () => {
  let component: TrainingLogCalendarViewComponent;
  let fixture: ComponentFixture<TrainingLogCalendarViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingLogCalendarViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingLogCalendarViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
