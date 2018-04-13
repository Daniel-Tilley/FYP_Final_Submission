import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingLogTemplateListComponent } from './training-log-template-list.component';

describe('TrainingLogTemplateListComponent', () => {
  let component: TrainingLogTemplateListComponent;
  let fixture: ComponentFixture<TrainingLogTemplateListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingLogTemplateListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingLogTemplateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
