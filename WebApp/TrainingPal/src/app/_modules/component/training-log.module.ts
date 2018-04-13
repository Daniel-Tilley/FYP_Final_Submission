import {NgModule} from '@angular/core';
import {SharedModule} from '../shared.module';
import {TrainingLogsComponent} from '../../training-logs/training-logs.component';
import { CreateTrainingLogComponent } from '../../training-logs/create-training-log/create-training-log.component';
import { EditTrainingLogComponent } from '../../training-logs/edit-training-log/edit-training-log.component';
import { TrainingLogCalendarViewComponent } from '../../training-logs/training-log-calendar-view/training-log-calendar-view.component';
import { TrainingLogListViewComponent } from '../../training-logs/training-log-list-view/training-log-list-view.component';
import {FormsModule} from '@angular/forms';
import { TrainingLogViewComponent } from '../../training-logs/training-log-view/training-log-view.component';

@NgModule({
  declarations: [
    TrainingLogsComponent,
    CreateTrainingLogComponent,
    EditTrainingLogComponent,
    TrainingLogCalendarViewComponent,
    TrainingLogListViewComponent,
    TrainingLogViewComponent
  ],
  imports: [
    SharedModule,
    FormsModule
  ]
})
export class TrainingLogModule {}
