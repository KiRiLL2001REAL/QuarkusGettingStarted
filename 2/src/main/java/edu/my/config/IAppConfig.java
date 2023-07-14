package edu.my.config;

import io.smallrye.config.ConfigMapping;

import java.util.Optional;

@ConfigMapping(prefix="app")
public interface IAppConfig {
    Greeting greeting();

    interface Greeting {
        String prefix();
        Optional<String> name();
        String postfix();
    }
}
