import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyCoachesComponent } from './my-coaches.component';

describe('MyCoachesComponent', () => {
  let component: MyCoachesComponent;
  let fixture: ComponentFixture<MyCoachesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyCoachesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyCoachesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
