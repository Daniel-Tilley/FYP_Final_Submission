import {SharedModule} from '../shared.module';
import {NgModule} from '@angular/core';
import { GraphsComponent } from '../../graphs/graphs.component';
import {ChartsModule} from 'ng2-charts';

@NgModule({
  declarations: [GraphsComponent],
  imports: [
    SharedModule,
    ChartsModule
  ]
})
export class GraphModule { }
