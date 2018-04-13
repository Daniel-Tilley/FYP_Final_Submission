import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingLogViewComponent } from './training-log-view.component';

describe('TrainingLogViewComponent', () => {
  let component: TrainingLogViewComponent;
  let fixture: ComponentFixture<TrainingLogViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingLogViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingLogViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
