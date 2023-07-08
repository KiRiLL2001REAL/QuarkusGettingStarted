package config;

import io.smallrye.config.ConfigMapping;

import java.util.Optional;

@ConfigMapping(prefix="app")
public interface AppConfig {
    Greeting greeting();

    interface Greeting {
        String prefix();
        Optional<String> name();
        String postfix();
    }
}
