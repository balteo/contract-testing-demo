package org.example.contracttestingdemo.contracts.signup;

import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.example.contracttestingdemo.handler.UserHandler;
import org.example.contracttestingdemo.routerfunction.UserRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/clean.sql")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class ContractVerifierBase {

    @Autowired
    private UserRouter config;

    @Autowired
    private UserHandler userHandler;

    @BeforeEach
    public void setup() {
        assertNotNull(config);
        assertNotNull(userHandler);
        // remove::start[]
        RestAssuredWebTestClient.standaloneSetup(config.route(userHandler));
        // remove::end[]
    }
}
