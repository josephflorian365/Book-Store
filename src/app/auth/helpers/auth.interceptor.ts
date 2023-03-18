import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalStorage } from 'ngx-webstorage';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  @LocalStorage('token')
  token?: string;

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(this.token) {
      const cloned = request.clone({
        headers: request.headers.set('Authorization', `Bearer ${this.token}`)
      });
      return next.handle(cloned);
    }
    return next.handle(request);
  }
}
