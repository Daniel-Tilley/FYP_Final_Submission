import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTrainingLogTemplateComponent } from './create-training-log-template.component';

describe('CreateTrainingLogTemplateComponent', () => {
  let component: CreateTrainingLogTemplateComponent;
  let fixture: ComponentFixture<CreateTrainingLogTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTrainingLogTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTrainingLogTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
