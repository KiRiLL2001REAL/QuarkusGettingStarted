package edu.my.resource;

import edu.my.data.dto.MovieDTO;
import edu.my.service.logic_layer.MassiveMovieProcessingService;
import edu.my.service.logic_layer.MovieService;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/massive_movie_processing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@IfBuildProfile("teststand")
public class MassiveMovieProcessingResource {
    @Inject
    MassiveMovieProcessingService massiveMovieService;
    @Inject
    MovieService movieService;

    @POST
    @Path("/{count}")
    @Transactional
    @Counted(name = "performedAddMassiveMovie", description = "How many massive add was called")
    @Timed(name = "addMassiveMovieTimer", description = "A measure of how long it takes to add all movies", unit = MetricUnits.MILLISECONDS)
    @Operation(summary = "Add fake movies", description = "Adds new movies")
    @APIResponse(
            responseCode = "201",
            description = "Movies are added",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
    )
    public Response add(
            @Parameter(description = "The number of movies to create", required = true)
            @PathParam("count") int count
    ){
        massiveMovieService.add(count);
        return Response.status(Response.Status.CREATED).entity(movieService.getAll()).build();
    }

    @PUT
    @Transactional
    @Counted(name = "performedUpdateMassiveMovie", description = "How many massive update was called")
    @Timed(name = "updateMassiveMovieTimer", description = "A measure of how long it takes to update all movies", unit = MetricUnits.MILLISECONDS)
    @Operation(summary = "Update all existing movies", description = "Updates all movies")
    @APIResponse(
            responseCode = "200",
            description = "Movies are updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class)))
    public Response add(){
        massiveMovieService.updateAllFacts();
        return Response.status(Response.Status.CREATED).entity(movieService.getAll()).build();
    }
}
