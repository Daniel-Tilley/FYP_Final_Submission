import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingLogListViewComponent } from './training-log-list-view.component';

describe('TrainingLogListViewComponent', () => {
  let component: TrainingLogListViewComponent;
  let fixture: ComponentFixture<TrainingLogListViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingLogListViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingLogListViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
