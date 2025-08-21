import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token'); // Get token

    // URLs to skip (no token needed)
    const skipUrls = ['/auth/login', '/auth/signup'];
    const shouldSkip = skipUrls.some(url => req.url.includes(url));

    // If token exists and URL is not in skip list, add token
    if (token && !shouldSkip) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(authReq);
    }

    // Otherwise, send request without token
    return next.handle(req);
  }
}
