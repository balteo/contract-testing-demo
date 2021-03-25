import { TestBed } from '@angular/core/testing';
import { UserService } from './user.service';
import { User } from '../domain/user.model';
import { HttpClientModule } from '@angular/common/http';
import { contractTestingInterceptorProvider, apiUrlProvider } from '../../test-utils/testing-providers';

describe('UserService', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        UserService,
        apiUrlProvider,
        contractTestingInterceptorProvider
      ]
    });
  });

  it('should create user', (done) => {
    const service: UserService = TestBed.inject(UserService);

    const user: User = {
      firstName: 'John',
      lastName: 'Smith',
      email: 'john@example.com'
    };

    service
      .createUser(user)
      .subscribe(
        (user: User) => {
          expect(user).not.toBeNull();
          expect(user).toEqual(
            expect.objectContaining({
              id: expect.any(Number),
              firstName: expect.any(String),
              lastName: expect.any(String),
              email: expect.any(String)
            }));
          done();
        });
  });
});
