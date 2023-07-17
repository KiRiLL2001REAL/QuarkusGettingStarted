package edu.my.resource;

import edu.my.data.dto.MovieDTO;
import edu.my.data.dto.TagDTO;
import edu.my.service.controller_layer.MovieControllerService;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.Set;

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
    public Response add(
            @Parameter(description = "Movie to be added", required = true)
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
    public Response update(
            @Parameter(description = "The ID that needs to find movie to be updated", required = true)
            @PathParam("id")
            Long id,
            @Parameter(description = "Movie with data to update", required = true)
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
    public Response attachTag(
            @Parameter(description = "The movie ID to attach tag", required = true)
            @QueryParam("movie_id")
            Long id,
            @Parameter(description = "Tag to be attached", required = true)
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
    public Response attachTags(
            @Parameter(description = "The movie ID to attach tag", required = true)
            @QueryParam("movie_id")
            Long id,
            @Parameter(description = "Tags to be attached", required = true)
            Set<@Valid TagDTO> tagDTOSet
    ) throws SystemException {
        movieControllerService.attachTags(id, tagDTOSet);
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
    public Response detachTag(
            @Parameter(description = "The movie ID to detach tag", required = true)
            @QueryParam("movie_id")
            Long id,
            @Parameter(description = "Tag to be detached", required = true)
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
    public Response detachAllTags(
            @Parameter(description = "The movie ID to detach tags", required = true)
            @QueryParam("movie_id")
            Long id
    ) throws SystemException {
        movieControllerService.detachAllTags(id);
        return Response.noContent().build();
    }
}
