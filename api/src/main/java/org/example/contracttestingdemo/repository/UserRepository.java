package org.example.contracttestingdemo.repository;

import org.example.contracttestingdemo.domain.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("select id, first_name, last_name, email from user u where u.email = :email")
    Mono<User> findByEmail(String email);
}
