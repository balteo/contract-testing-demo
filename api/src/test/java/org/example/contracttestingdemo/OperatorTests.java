package org.example.contracttestingdemo;

import org.example.contracttestingdemo.utils.ReactiveOnOperatorDebugHook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(ReactiveOnOperatorDebugHook.class)
class OperatorTests {


    @Test
    void orTest() {
        Mono<String> chain = Mono.<String>empty().switchIfEmpty(Mono.just("hello"));

        StepVerifier.create(
            chain
        )
            .expectNext("hello")
            .verifyComplete();
    }

}
