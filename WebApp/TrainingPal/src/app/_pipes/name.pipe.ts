import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'nameChangePipe'})
export class NamePipe implements PipeTransform {
  transform(value: any): string {
    return value === null ? '' : value.charAt(0).toUpperCase() + value.slice(1);
  }
}
