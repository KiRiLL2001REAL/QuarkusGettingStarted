package edu.my.resource;

import edu.my.api.dto.MovieDTO;
import edu.my.api.dto.TagDTO;
import edu.my.service.controller_layer.MovieControllerService;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {
    @Inject
    MovieControllerService movieControllerService;

    @GET
    @Operation(summary = "Get list of movies", description = "Shows all available movies")
    @APIResponse(
            responseCode = "200",
            description = "List of all movies",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
    )
    @Counted(name = "performedGetAllMovies", description = "How many requests were made for getting list of movies")
    @Timed(name = "getAllMoviesTimer", description = "A measure of how long it takes to get a list of movies", unit = MetricUnits.MILLISECONDS)
    public Response getAll() {
        return Response.ok(movieControllerService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get movie by ID", description = "Shows available movie")
    @APIResponse(
            responseCode = "200",
            description = "Selected movie",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
    )
    @Counted(name = "performedGetByIdMovie", description = "How many requests were made for getting specific movie")
    @Timed(name = "getByIdMovieTimer", description = "A measure of how long it takes to get specific movie", unit = MetricUnits.MILLISECONDS)
    public Response getById(
            @Parameter(description = "The ID that needs to be fetched", required = true)
            @PathParam("id")
            Long id
    ) {
        return Response.ok(movieControllerService.getById(id)).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Add not existing movie", description = "Adds new movie")
    @APIResponse(
            responseCode = "201",
            description = "Movie is added",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
    )
    @Counted(name = "performedAddMovie", description = "How many movies has been added")
    @Timed(name = "getAddMovieTimer", description = "A measure of how long it takes to add movie", unit = MetricUnits.MILLISECONDS)
    public Response add(
            @Valid
            MovieDTO movieDTO
    ) {
        movieControllerService.add(movieDTO);
        return Response.status(Response.Status.CREATED).entity(movieControllerService.getAll()).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Delete existing movie", description = "Deletes the movie")
    @APIResponse(
            responseCode = "204",
            description = "Movie is deleted"
    )
    @Counted(name = "performedDeleteByIdMovie", description = "How many movies has been deleted")
    @Timed(name = "getDeleteByIdMovieTimer", description = "A measure of how long it takes to delete movie", unit = MetricUnits.MILLISECONDS)
    public Response deleteById(
            @Parameter(description = "The movie's id that needs to be deleted", required = true)
            @PathParam("id")
            Long id
    ) {
        movieControllerService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Update available movie", description = "Updates the movie")
    @APIResponse(
            responseCode = "200",
            description = "Movie is updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
    )
    @Counted(name = "performedUpdateMovie", description = "How many movies has been updated")
    @Timed(name = "getUpdateMovieTimer", description = "A measure of how long it takes to update movie", unit = MetricUnits.MILLISECONDS)
    public Response update(
            @Parameter(description = "The ID that needs to find movie to be updated", required = true)
            @PathParam("id")
            Long id,
            @Valid
            MovieDTO movieDTO
    ) {
        movieControllerService.update(id, movieDTO);
        return Response.ok(movieControllerService.getById(id)).build();
    }

    @GET
    @Transactional
    @Path("/tags/{id}")
    @Operation(summary = "Get list of movie's tags", description = "Shows all tags which are linked to movie")
    @APIResponse(
            responseCode = "200",
            description = "List of attached tags",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    @Counted(name = "performedGetAllMovieTags", description = "How many requests were made for getting list of movie tags")
    @Timed(name = "getAllMovieTagsTimer", description = "A measure of how long it takes to get a list of movie tags", unit = MetricUnits.MILLISECONDS)
    public Response getAllTags(
            @Parameter(description = "The ID to get the associated tags", required = true)
            @PathParam("id")
            Long id
    ) {
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @POST
    @Transactional
    @Path("/tag")
    @Operation(summary = "Attach tag", description = "Attaches existing tag to existing movie")
    @APIResponse(
            responseCode = "200",
            description = "List of attached tags",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    @Counted(name = "performedAttachMovieTag", description = "How many tags has been added to movies")
    @Timed(name = "getAttachMovieTagTimer", description = "A measure of how long it takes to add tag to movie", unit = MetricUnits.MILLISECONDS)
    public Response attachTag(
            @Parameter(description = "The movie ID to attach tag", required = true)
            @QueryParam("movie_id")
            Long id,
            @Valid
            TagDTO tagDTO
    ) {
        movieControllerService.attachTag(id, tagDTO);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @POST
    @Transactional
    @Path("/tags")
    @Operation(summary = "Attach multiple tags", description = "Attaches existing tags to existing movie")
    @APIResponse(
            responseCode = "200",
            description = "List of attached tags",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    @Counted(name = "performedAttachMovieMultipleTags", description = "How many multiple attaches of tags has been performed to movies")
    @Timed(name = "getAttachMovieMultipleTagsTimer", description = "A measure of how long it takes to add multiple tags to movie", unit = MetricUnits.MILLISECONDS)
    public Response attachTags(
            @Parameter(description = "The movie ID to attach tag", required = true)
            @QueryParam("movie_id")
            Long id,
            List<@Valid TagDTO> tagDTOList
    ) throws SystemException {
        movieControllerService.attachTags(id, tagDTOList);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @DELETE
    @Transactional
    @Path("/tag")
    @Operation(summary = "Detach existing tag", description = "Deletes link between movie and associated tag")
    @APIResponse(
            responseCode = "200",
            description = "Tag is detached",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
    )
    @Counted(name = "performedDetachMovieTag", description = "How many tags has been detached from movies")
    @Timed(name = "getDetachMovieTagTimer", description = "A measure of how long it takes to detach tag from movie", unit = MetricUnits.MILLISECONDS)
    public Response detachTag(
            @Parameter(description = "The movie ID to detach tag", required = true)
            @QueryParam("movie_id")
            Long id,
            @Valid
            TagDTO tagDTO) {
        movieControllerService.detachTag(id, tagDTO);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @DELETE
    @Transactional
    @Path("/tags")
    @Operation(summary = "Detach all existing tag", description = "Deletes all links between movie and associated tags")
    @APIResponse(
            responseCode = "204",
            description = "All tags are detached"
    )
    @Counted(name = "performedDetachMovieAllTags", description = "How many times all tags has been detached from movies")
    @Timed(name = "getDetachMovieAllTagsTimer", description = "A measure of how long it takes to detach all tags from movie", unit = MetricUnits.MILLISECONDS)
    public Response detachAllTags(
            @Parameter(description = "The movie ID to detach tags", required = true)
            @QueryParam("movie_id")
            Long id
    ) throws SystemException {
        movieControllerService.detachAllTags(id);
        return Response.noContent().build();
    }
}
