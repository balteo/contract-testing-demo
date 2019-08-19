package org.example.contracttestingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = {
    WebMvcAutoConfiguration.class,
    HypermediaAutoConfiguration.class
})
public class ContractTestingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractTestingDemoApplication.class, args);
    }
}
