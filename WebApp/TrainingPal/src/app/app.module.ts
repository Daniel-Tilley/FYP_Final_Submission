import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {CoreModule} from './_modules/core.module';
import {AuthModule} from './_modules/component/auth.module';
import {UserModule} from './_modules/component/user.module';
import {SharedModule} from './_modules/shared.module';
import {DashboardModule} from './_modules/component/dashboard.module';
import {HttpClientModule} from '@angular/common/http';
import {TrainingLogModule} from './_modules/component/training-log.module';
import {TrainingLogTemplateModule} from './_modules/component/training-log-template.module';
import {TargetsModule} from './_modules/component/targets.module';
import {EventModule} from './_modules/component/event.module';
import {InviteModule} from './_modules/component/invite.module';
import {GraphModule} from './_modules/component/graph.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CoreModule,
    SharedModule,
    AuthModule,
    DashboardModule,
    EventModule,
    GraphModule,
    InviteModule,
    UserModule,
    TargetsModule,
    TrainingLogModule,
    TrainingLogTemplateModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
