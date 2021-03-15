package org.example.contracttestingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;

@SpringBootApplication(exclude = {
    HypermediaAutoConfiguration.class
})
public class ContractTestingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractTestingDemoApplication.class, args);
    }
}
