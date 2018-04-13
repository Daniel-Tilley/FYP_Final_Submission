import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAthletesComponent } from './my-athletes.component';

describe('MyAthletesComponent', () => {
  let component: MyAthletesComponent;
  let fixture: ComponentFixture<MyAthletesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAthletesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAthletesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
