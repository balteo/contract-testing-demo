import { provider } from '../../../pactSetup';
import { User } from '../domain/user.model';
import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './user.service';
import { Interaction, InteractionObject } from '@pact-foundation/pact';

describe("User API", () => {

  const user: User = {
    firstName: 'John',
    lastName: 'Smith',
    email: 'john@example.com'
  };

  const createUser: User = {id: 1, ...user};

  describe("should create user", () => {

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientModule],
        providers: [UserService]
      });
    });

    beforeEach(() => {
      const interaction: InteractionObject | Interaction = {
        state: "I have no user in DB",
        uponReceiving: "a POST request with a user",
        withRequest: {
          method: "POST",
          path: "/api/user",
          headers: {
            //'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
          },
          body: user
        },
        willRespondWith: {
          status: 201,
          headers: {
            //'Accept': 'application/json, text/plain, */*',
            "Content-Type": 'application/json',
          },
          body: createUser,
        },
      };

      return provider.addInteraction(interaction);
    });

    it('should create user', (done) => {
      const service: UserService = TestBed.get(UserService);

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
            provider.verify();
          });
    });

    // verify with Pact, and reset expectations
    //afterEach(() => provider.verify())
  })
});
