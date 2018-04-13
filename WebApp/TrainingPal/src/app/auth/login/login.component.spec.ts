import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {SharedModule} from '../../_modules/shared.module';
import {ReactiveFormsModule} from '@angular/forms';
import {CoreModule} from '../../_modules/core.module';
import {DashboardModule} from '../../_modules/component/dashboard.module';
import {SignUpComponent} from '../sign-up/sign-up.component';
import {UserModule} from '../../_modules/component/user.module';
import {TrainingLogModule} from '../../_modules/component/training-log.module';
import {APP_BASE_HREF} from '@angular/common';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent, SignUpComponent ],
      imports: [DashboardModule, UserModule, TrainingLogModule, ReactiveFormsModule, SharedModule, CoreModule],
      providers: [
        {provide: APP_BASE_HREF, useValue: '/'}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
