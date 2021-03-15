package org.example.contracttestingdemo.utils;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

public class DatabaseExtension implements BeforeAllCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private ConnectionFactory connectionFactory;
    private Resource setUpScript;
    private Resource cleanUpScript;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        ApplicationContext springContext = SpringExtension.getApplicationContext(extensionContext);
        this.connectionFactory = springContext.getBean(ConnectionFactory.class);
        this.setUpScript = springContext.getResource("classpath:/db/migration/V1__init.sql");
        this.cleanUpScript = springContext.getResource("classpath:/db/clean.sql");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        executeScriptBlocking(cleanUpScript);
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        // executeScriptBlocking(setUpScript);
    }

    private void executeScriptBlocking(final Resource sqlScript) {
        Mono.from(connectionFactory.create())
            .flatMap(connection -> ScriptUtils.executeSqlScript(connection, sqlScript))
            .block();
    }
}
