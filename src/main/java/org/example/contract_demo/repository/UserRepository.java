package org.example.contract_demo.repository;

import org.example.contract_demo.domain.User;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("select id, firstName, lastName, email from user c where c.lastName = :lastName")
    Flux<User> findByLastName(String lastName);
}
