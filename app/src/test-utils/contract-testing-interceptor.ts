import { Injectable, Optional } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class ContractTestingInterceptor implements HttpInterceptor {

  constructor(@Optional() private apiUrl: string) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      url: `${this.apiUrl}${request.urlWithParams}`
    });
    return next.handle(request);
  }
}
