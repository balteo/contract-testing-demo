import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ContractTestingInterceptor } from './contract-testing-interceptor';
import { InjectionToken } from '@angular/core';

export const API_URL = new InjectionToken('location');

export const contractTestingInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useFactory: (apiUrl: string) => new ContractTestingInterceptor(apiUrl),
  multi: true,
  deps: [API_URL]
};

export const apiUrlProvider = {
  provide: API_URL,
  useValue: 'http://localhost:8080'
};
