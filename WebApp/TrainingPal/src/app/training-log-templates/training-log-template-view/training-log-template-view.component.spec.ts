import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingLogTemplateViewComponent } from './training-log-template-view.component';

describe('TrainingLogTemplateViewComponent', () => {
  let component: TrainingLogTemplateViewComponent;
  let fixture: ComponentFixture<TrainingLogTemplateViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingLogTemplateViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingLogTemplateViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
