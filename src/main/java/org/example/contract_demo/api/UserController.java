package org.example.contract_demo.api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.contract_demo.domain.User;
import org.example.contract_demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final @NonNull UserService userService;

    @GetMapping("/api/user")
    public Flux<User> findAllUsers() {
        return userService.findAll();
    }
}
