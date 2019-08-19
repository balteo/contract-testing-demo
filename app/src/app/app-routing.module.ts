import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from './component/user-list/user-list.component';
import { UsersResolve } from './resolve/users.resolve';

const routes: Routes = [{
  path: 'user-list',
  component: UserListComponent,
  resolve: {
    users: UsersResolve
  }
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
