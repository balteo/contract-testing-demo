import { Component, OnInit } from '@angular/core';
import { User } from '../../domain/user.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  users: User[] = [];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.users = [...this.route.snapshot.data.users];
  }

}
