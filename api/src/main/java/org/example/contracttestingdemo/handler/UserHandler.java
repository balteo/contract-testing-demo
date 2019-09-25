package org.example.contracttestingdemo.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.contracttestingdemo.domain.User;
import org.example.contracttestingdemo.exception.DuplicateUserException;
import org.example.contracttestingdemo.exception.ValidationException;
import org.example.contracttestingdemo.repository.UserRepository;
import org.example.contracttestingdemo.validator.UserValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final @NonNull UserRepository userRepository;
    private final @NonNull UserValidator userValidator;

    public Mono<ServerResponse> findUsers(ServerRequest serverRequest) {
        return ok()
            .contentType(APPLICATION_JSON)
            .body(userRepository.findAll(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
            .flatMap(this::validate)
            .flatMap(this::validateEmailNotExists)
            .flatMap(this::saveUser)
            .flatMap(newUser -> status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(newUser))
            )
            .onErrorResume(ValidationException.class, e -> status(BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(e.getErrors()))
            )
            .onErrorResume(DuplicateUserException.class, e -> status(CONFLICT)
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(e.getErrorMessage()))
            );
    }

    private Mono<User> validateEmailNotExists(User user) {
        return userRepository.findByEmail(user.getEmail())
            .flatMap(userMono -> Mono.<User>error(new DuplicateUserException("User already exists")))
            .switchIfEmpty(Mono.just(user));
    }

    private Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    private Mono<User> validate(User user) {
        AbstractBindingResult errors = computeErrors(user);
        return errors.hasErrors() ? Mono.error(new ValidationException(errors.getAllErrors())) : Mono.just(user);
    }

    private AbstractBindingResult computeErrors(User user) {
        AbstractBindingResult errors = new BeanPropertyBindingResult(user, User.class.getName());
        userValidator.validate(user, errors);
        return errors;
    }

}
