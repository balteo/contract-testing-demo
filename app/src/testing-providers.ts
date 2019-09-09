import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ContractTestingInterceptor } from './app/interceptor/contract-testing-interceptor';
import { InjectionToken, Optional } from '@angular/core';

export const API_URL = new InjectionToken('location');

export const contractTestingInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useFactory: (apiUrl: string) => new ContractTestingInterceptor(apiUrl),
  multi: true,
  deps: [[new Optional(), API_URL]]
};

export const apiUrlProvider = {
  provide: API_URL,
  useValue: 'http://localhost:8080'
};
