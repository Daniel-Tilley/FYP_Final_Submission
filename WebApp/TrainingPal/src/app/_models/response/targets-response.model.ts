import {BaseResponse} from './base-response.model';
import {TargetInterface} from '../../_interfaces/target.interface';

export class TargetsResponse extends BaseResponse {
  Data: {
    Targets: TargetInterface[]
  };
}
