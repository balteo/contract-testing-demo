package org.example.contracttestingdemo.user;

import org.example.contracttestingdemo.domain.User;
import org.example.contracttestingdemo.handler.UserHandler;
import org.example.contracttestingdemo.routerfunction.UserRouter;
import org.example.contracttestingdemo.utils.DatabaseExtension;
import org.example.contracttestingdemo.utils.ReactiveOnOperatorDebugHook;
import org.example.contracttestingdemo.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ReactiveOnOperatorDebugHook.class, DatabaseExtension.class})
@SpringBootTest
class UserSignUpTest {

    @Autowired
    private UserRouter config;

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private UserValidator userValidator;


    @Test
    void shouldRejectInvalidEmail() {
        User user = User.builder()
            .firstName("John")
            .lastName("Smith")
            .email("wrong")
            .build();
        Errors errors = new BeanPropertyBindingResult(user, User.class.getName());
        userValidator.validate(user, errors);
        assertThat(errors.hasErrors()).isTrue();
    }

    @Test
    void shouldSignUpUser() {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.route(userHandler))
            .build();

        User user = User.builder()
            .firstName("John")
            .lastName("Smith")
            .email("john@example.com")
            .build();

        client
            .post()
            .uri("/api/user")
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .jsonPath("$.id")
            .isNotEmpty()
            .jsonPath("$.firstName")
            .isEqualTo("John");
    }

    @Test
    void shouldRejectDuplicateEmail() {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.route(userHandler))
            .build();

        User user = User.builder()
            .firstName("John")
            .lastName("Smith")
            .email("john@example.com")
            .build();

        User otherUser = User.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .build();

        client
            .post()
            .uri("/api/user")
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus()
            .is2xxSuccessful();

        client
            .post()
            .uri("/api/user")
            .body(Mono.just(otherUser), User.class)
            .exchange()
            .expectStatus()
            .is4xxClientError();

    }

    @Test
    void shouldRejectEmptyFirstName() {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.route(userHandler))
            .build();

        User user = User.builder()
            .firstName("")
            .lastName("Smith")
            .email("john@example.com")
            .build();

        client
            .post()
            .uri("/api/user")
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }
}
