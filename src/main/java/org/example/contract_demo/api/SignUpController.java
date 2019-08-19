package org.example.contract_demo.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.contract_demo.domain.User;
import org.example.contract_demo.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final @NonNull UserService userService;

    @PostMapping("/api/sign-up")
    public Mono<User> signUpUser(@Validated @RequestBody User user, BindingResult result) {
        return userService.save(user);
    }
}
