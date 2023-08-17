package edu.my.API.resource;

import edu.my.API.data.dto.MovieDTO;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/massive_movie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@IfBuildProfile("teststand")
public interface MassiveMovieResource {

    @POST
    @Path("/{count}")
    @Operation(summary = "Add fake movies", description = "Adds new movies")
    @APIResponse(
            responseCode = "201",
            description = "Movies are added",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
    )
    Response add(
            @Parameter(description = "The number of movies to create", required = true)
            @PathParam("count") int count
    );

    @PUT
    @Operation(summary = "Update all existing movies", description = "Updates all movies facts")
    @APIResponse(
            responseCode = "200",
            description = "Movies are updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class)))
    Response updateAllFacts();
}
