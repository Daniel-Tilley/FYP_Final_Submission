import {BaseResponse} from './base-response.model';
import {TrainingLogTemplate} from '../training-log-template.model';

export class TrainingLogTemplateResponse extends BaseResponse {
  Data: {
    Template: TrainingLogTemplate
  };
}
