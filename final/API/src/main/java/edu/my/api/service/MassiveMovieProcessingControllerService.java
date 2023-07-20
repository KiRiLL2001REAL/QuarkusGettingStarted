package edu.my.service;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/massive_movie_processing")
@IfBuildProfile("teststand")
@RegisterRestClient(configKey = "massive-movie-api")
public interface MassiveMovieProcessingControllerService {
    @POST
    @Path("/{count}")
    void add(@PathParam("count") Integer count);
    @PUT
    void updateAllFacts();
}

