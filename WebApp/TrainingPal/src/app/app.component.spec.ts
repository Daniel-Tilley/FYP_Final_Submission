import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import {SharedModule} from './_modules/shared.module';
import {CoreModule} from './_modules/core.module';
import {DashboardModule} from './_modules/component/dashboard.module';
import {AuthModule} from './_modules/component/auth.module';
import {UserModule} from './_modules/component/user.module';
import {TrainingLogModule} from './_modules/component/training-log.module';
import {APP_BASE_HREF} from '@angular/common';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
      imports: [
        TrainingLogModule,
        UserModule,
        AuthModule,
        DashboardModule,
        SharedModule,
        CoreModule
      ],
      providers: [
        {provide: APP_BASE_HREF, useValue: '/'}
      ]
    }).compileComponents();
  }));
  it('should create', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
