import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTrainingLogComponent } from './create-training-log.component';

describe('CreateTrainingLogComponent', () => {
  let component: CreateTrainingLogComponent;
  let fixture: ComponentFixture<CreateTrainingLogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTrainingLogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTrainingLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
