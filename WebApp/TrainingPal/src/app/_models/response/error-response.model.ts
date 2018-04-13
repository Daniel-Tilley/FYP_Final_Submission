import {HttpErrorResponse} from '@angular/common/http';

export class ErrorResponse extends HttpErrorResponse {
  error: {
    Message: string,
    Data: null
  };
}
