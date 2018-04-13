import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingLogTemplatesComponent } from './training-log-templates.component';

describe('TrainingLogTemplatesComponent', () => {
  let component: TrainingLogTemplatesComponent;
  let fixture: ComponentFixture<TrainingLogTemplatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingLogTemplatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingLogTemplatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
