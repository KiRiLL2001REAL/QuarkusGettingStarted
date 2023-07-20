package edu.my.resource;

import edu.my.service.MassiveMovieProcessingControllerService;
import edu.my.service.MovieControllerService;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/massive_movie_processing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@IfBuildProfile("teststand")
public class MassiveMovieProcessingResource {
    @RestClient
    MassiveMovieProcessingControllerService massiveMovieProcessingControllerService;
    @RestClient
    MovieControllerService movieControllerService;

    @POST
    @Path("/{count}")
    @Transactional
    public Response add(
            @PathParam("count")
            int count
    ) {
        massiveMovieProcessingControllerService.add(count);
        return Response.status(Response.Status.CREATED).entity(movieControllerService.getAll()).build();
    }

    @PUT
    @Transactional
    public Response updateAllFacts() {
        massiveMovieProcessingControllerService.updateAllFacts();
        return Response.status(Response.Status.CREATED).entity(movieControllerService.getAll()).build();
    }
}
