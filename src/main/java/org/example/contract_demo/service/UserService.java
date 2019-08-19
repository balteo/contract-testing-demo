package org.example.contract_demo.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.contract_demo.domain.User;
import org.example.contract_demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final @NonNull UserRepository repository;

    @Transactional
    public Mono<User> save(User user) {
        return repository.save(user);
    }

    @Transactional
    public Flux<User> findAll() {
        return repository.findAll();
    }
}
