import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'timePipe'})
export class TimePipe implements PipeTransform {
  transform(value: any): string {
    let hours;
    let minutes;

    if (parseInt(value, 10) < 10) {
      hours = '0' + value.slice(0, 1);
      minutes = value.slice(2, 4);
    } else {
      hours = value.slice(0, 2);
      minutes = value.slice(3, 5);
    }

    return hours + ':' + minutes;
  }
}
