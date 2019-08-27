package org.example.contracttestingdemo.contracts.signup;

import org.junit.jupiter.api.BeforeEach;

public abstract class ContractVerifierBase {

    @BeforeEach
    public void setup() {
        // remove::start[]
//        RestAssuredWebTestClient.standaloneSetup();
        // remove::end[]
    }
}
