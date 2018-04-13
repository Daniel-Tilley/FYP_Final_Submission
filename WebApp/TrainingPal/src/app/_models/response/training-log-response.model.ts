import {BaseResponse} from './base-response.model';
import {TrainingLog} from '../training-log.model';

export class TrainingLogResponse extends BaseResponse {
  Data: {
    TrainingLog: TrainingLog
  };
}
