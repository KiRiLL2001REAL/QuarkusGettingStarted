package edu.my.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/log")
public class LoggingResource {

    Logger logger = LoggerFactory.getLogger(LoggingResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/ping")
    public String ping() {
        logger.info("Ping is called.");
        return "Check log in console.";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/exception")
    public String produceException() throws Exception {
        try {
            throw new Exception("Test exception");
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }
        return "Exception is produced. Check log in console.";
    }
}
