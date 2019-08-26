package org.example.contracttestingdemo.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.contracttestingdemo.domain.User;
import org.example.contracttestingdemo.repository.UserRepository;
import org.example.contracttestingdemo.validator.UserValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final @NonNull UserRepository userRepository;
    private final @NonNull UserValidator userValidator;

    public Mono<ServerResponse> getUsers(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON).
            body(userRepository.findAll(), User.class);
    }

    //FIXME: separate validator from handler
    public Mono<ServerResponse> signUpUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
            .flatMap(this::createUserIfEmailNotExists);
    }


    private Mono<ServerResponse> validateUser(User user) {
        return Mono.just(new BeanPropertyBindingResult(user, User.class.getName()))
            .doOnNext(err -> userValidator.validate(user, err))
            .filter(AbstractBindingResult::hasErrors)
            .flatMap(err ->
                status(BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromObject(err.getAllErrors()))
            );
    }

    private Mono<ServerResponse> validateUser(Mono<User> userMono) {
        return userMono.flatMap(user -> Mono.just(new BeanPropertyBindingResult(user, User.class.getName()))
            .doOnNext(err -> userValidator.validate(user, err))
            .filter(AbstractBindingResult::hasErrors)
            .flatMap(err ->
                status(BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromObject(err.getAllErrors()))
            ));
    }

    private Mono<ServerResponse> validateEmailNotExists(User user) {
        return userRepository.findByEmail(user.getEmail())
            .flatMap(existingUser ->
                status(BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromObject("User already exists."))
            );
    }

    private Mono<ServerResponse> validateEmailNotExists(Mono<User> userMono) {
        return userMono.flatMap(user -> userRepository.findByEmail(user.getEmail())
            .flatMap(existingUser ->
                status(BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromObject("User already exists."))
            ));
    }

    private Mono<ServerResponse> saveUser(User user) {
        return userRepository.save(user)
            .flatMap(newUser -> status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(newUser))
            );
    }

    private Mono<ServerResponse> saveUser(Mono<User> userMono) {
        return userMono.flatMap(user -> userRepository.save(user)
            .flatMap(newUser -> status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(newUser))
            ));
    }

    private Mono<ServerResponse> createUserIfEmailNotExists(User user) {
        return userRepository.findByEmail(user.getEmail())
            .flatMap(existingUser ->
                status(BAD_REQUEST).contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromObject("User already exists."))
            )
            .switchIfEmpty(
                validateUser(user)
                    .switchIfEmpty(
                        userRepository.save(user)
                            .flatMap(newUser -> status(CREATED).contentType(APPLICATION_JSON)
                                .body(BodyInserters.fromObject(newUser)))
                    )
            );
    }
}
