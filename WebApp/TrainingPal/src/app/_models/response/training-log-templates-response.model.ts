import {BaseResponse} from './base-response.model';
import {TrainingLogTemplateInterface} from '../../_interfaces/training-log-template-interface';

export class TrainingLogTemplatesResponse extends BaseResponse {
  Data: {
    Templates: TrainingLogTemplateInterface[]
  };
}
