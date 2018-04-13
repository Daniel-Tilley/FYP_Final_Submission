import {NgModule} from '@angular/core';
import {SharedModule} from '../shared.module';
import { TargetsComponent } from '../../targets/targets.component';

@NgModule({
  declarations: [
  TargetsComponent],
  imports: [
    SharedModule
  ]
})
export class TargetsModule {}
