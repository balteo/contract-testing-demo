package org.example.contracttestingdemo.routerfunction;

import org.example.contracttestingdemo.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {
        return RouterFunctions.route()
            .GET("/api/user", accept(APPLICATION_JSON), userHandler::findUsers)
            .POST("/api/user", accept(APPLICATION_JSON), userHandler::createUser)
            .build();
    }
}
