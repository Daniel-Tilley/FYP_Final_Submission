import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTrainingLogTemplateComponent } from './edit-training-log-template.component';

describe('EditTrainingLogTemplateComponent', () => {
  let component: EditTrainingLogTemplateComponent;
  let fixture: ComponentFixture<EditTrainingLogTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTrainingLogTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTrainingLogTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
