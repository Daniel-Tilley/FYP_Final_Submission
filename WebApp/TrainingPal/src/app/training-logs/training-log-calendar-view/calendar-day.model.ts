import {TrainingLog} from '../../_models/training-log.model';

export class CalendarDay {
  isActive: boolean;
  DayNumber: number;
  DayName: string;
  hasData: boolean;
  Data: TrainingLog[] = [];
}
