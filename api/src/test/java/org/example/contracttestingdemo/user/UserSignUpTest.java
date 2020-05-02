package org.example.contracttestingdemo.user;

import org.example.contracttestingdemo.domain.User;
import org.example.contracttestingdemo.handler.UserHandler;
import org.example.contracttestingdemo.routerfunction.UserRouter;
import org.example.contracttestingdemo.utils.ReactiveOnOperatorDebugHook;
import org.example.contracttestingdemo.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Mono;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/clean.sql")
@ExtendWith(ReactiveOnOperatorDebugHook.class)
@SpringBootTest
class UserSignUpTest {

    @Autowired
    private UserRouter config;

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    void shouldSignUpUser() throws SQLException {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.route(userHandler))
            .build();

        User user = User.builder()
            .firstName("John")
            .lastName("Smith")
            .email("john@example.com")
            .build();

//        Server.startWebServer(dataSource.getConnection());

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

        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "user")).isOne();

        client
            .post()
            .uri("/api/user")
            .body(Mono.just(otherUser), User.class)
            .exchange()
            .expectStatus()
            .is4xxClientError();

        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "user")).isOne();

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

        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "user")).isZero();

        client
            .post()
            .uri("/api/user")
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }
}
