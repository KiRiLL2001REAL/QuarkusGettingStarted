package edu.my.service;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PlayerService {
    @Inject
    Logger logger;

    void startup(@Observes StartupEvent event) {
        logger.log(Logger.Level.INFO, "PlayerService is now ONLINE");
    }

    public String playSound(String fileType) {
        return fileType + " audio file is playing.";
    }
}
