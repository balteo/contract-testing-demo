package org.example.contracttestingdemo.utils;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import reactor.core.publisher.Hooks;

public class ReactiveOnOperatorDebugHook implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Hooks.onOperatorDebug();
    }
}
