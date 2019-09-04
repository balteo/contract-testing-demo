import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../domain/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  findAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>('/api/user');
  }

  createUser(user: User): Observable<User> {
    //FIXME: introduce proxy and remove hard-coded value here
    return this.httpClient.post<User>('http://localhost:8991/api/user', user);
  }
}
