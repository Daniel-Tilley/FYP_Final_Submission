import {BaseResponse} from './base-response.model';
import {TrainingLogInterface} from '../../_interfaces/training-log.interface';

export class TrainingLogsResponse extends BaseResponse {
  Data: {
    TrainingLogs: TrainingLogInterface[]
  };
}
