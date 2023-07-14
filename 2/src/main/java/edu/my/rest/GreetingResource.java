package edu.my.rest;

import edu.my.config.IAppConfig;
import edu.my.service.GreetingService;
import io.smallrye.config.WithDefault;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

    @Inject
    GreetingService serviceGreeting;

    @ConfigProperty(name = "greeting.prefix")
    String prefix;
    @ConfigProperty(name = "greeting.name")
    @WithDefault("world")
    String name;
    @ConfigProperty(name = "greeting.postfix")
    String postfix;

    @Inject
    IAppConfig appConfig;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    public String greeting(@PathParam("name") String name) {
        return serviceGreeting.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/v1")
    public String hello1() {
        return prefix + " " + name + postfix;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/v2")
    public String hello2() {
        return appConfig.greeting().prefix() + " " + appConfig.greeting().name().orElse("world") + " "
                + appConfig.greeting().postfix();
    }
}
