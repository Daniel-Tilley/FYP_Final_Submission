import {NgModule} from '@angular/core';
import {SharedModule} from '../shared.module';
import { TrainingLogTemplatesComponent } from '../../training-log-templates/training-log-templates.component';
import { TrainingLogTemplateViewComponent } from '../../training-log-templates/training-log-template-view/training-log-template-view.component';
import { EditTrainingLogTemplateComponent } from '../../training-log-templates/edit-training-log-template/edit-training-log-template.component';
import { CreateTrainingLogTemplateComponent } from '../../training-log-templates/create-training-log-template/create-training-log-template.component';
import { TrainingLogTemplateListComponent } from '../../training-log-templates/training-log-template-list/training-log-template-list.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
  TrainingLogTemplatesComponent,
  TrainingLogTemplateViewComponent,
  EditTrainingLogTemplateComponent,
  CreateTrainingLogTemplateComponent,
  TrainingLogTemplateListComponent],
  imports: [
    SharedModule,
    FormsModule
  ]
})
export class TrainingLogTemplateModule {}
