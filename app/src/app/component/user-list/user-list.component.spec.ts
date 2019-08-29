import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserListComponent } from './user-list.component';
import { ActivatedRoute } from '@angular/router';

describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;

  const route = {
    provide: ActivatedRoute,
    useValue: {
      snapshot: {data: {users: []}}
    },
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers: [route],
      declarations: [UserListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
