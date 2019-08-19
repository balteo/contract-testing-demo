import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../service/user.service';
import { User } from '../domain/user.model';

@Injectable({
  providedIn: 'root'
})
export class UsersResolve implements Resolve<User[]> {

  constructor(private userService: UserService) {
  }

  resolve(): Observable<User[]> {
    return this.userService
      .findAllUsers();
  }

}
