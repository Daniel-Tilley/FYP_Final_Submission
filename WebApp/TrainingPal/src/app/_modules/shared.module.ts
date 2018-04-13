import {NgModule} from '@angular/core';
import {ErrorComponent} from '../shared/error/error.component';
import {FooterComponent} from '../shared/footer/footer.component';
import {HeaderComponent} from '../shared/header/header.component';
import {NavbarComponent} from '../shared/navbar/navbar.component';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AlertComponent} from '../shared/alert/alert.component';
import {TimePipe} from '../_pipes/time.pipe';
import {ReactiveFormsModule} from '@angular/forms';
import {NamePipe} from '../_pipes/name.pipe';

@NgModule({
  declarations: [
    ErrorComponent,
    FooterComponent,
    HeaderComponent,
    NavbarComponent,
    AlertComponent,
    NamePipe,
    TimePipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule
  ],
  exports: [
    CommonModule,
    RouterModule,
    FooterComponent,
    HeaderComponent,
    ErrorComponent,
    NavbarComponent,
    AlertComponent,
    ReactiveFormsModule,
    NamePipe,
    TimePipe
  ]
})
export class SharedModule { }
