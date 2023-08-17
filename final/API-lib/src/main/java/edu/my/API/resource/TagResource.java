package edu.my.API.resource;

import edu.my.API.data.dto.TagDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TagResource {

    @GET
    @Operation(summary = "Get list of tags", description = "Shows all available tags")
    @APIResponse(
            responseCode = "200",
            description = "List of all tags",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    Response getAll();

    @GET
    @Path("/{id}")
    @Operation(summary = "Get tag by ID", description = "Shows available tag")
    @APIResponse(
            responseCode = "200",
            description = "Selected tag",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    Response getById(
            @Parameter(description = "The ID that needs to be fetched", required = true)
            @PathParam("id")
            Long id
    );

    @POST
    @Operation(summary = "Add not existing tag", description = "Adds new tag")
    @APIResponse(
            responseCode = "201",
            description = "Tag is added",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    Response add(
            @Valid
            TagDTO tagDTO
    );

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete existing tag", description = "Deletes the tag")
    @APIResponse(
            responseCode = "204",
            description = "Tag is deleted"
    )
    Response deleteById(
            @Parameter(description = "The tag's id that needs to be deleted", required = true)
            @PathParam("id")
            Long id
    );

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update available tag", description = "Updates the tag")
    @APIResponse(
            responseCode = "200",
            description = "Tag is updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    Response update(
            @Parameter(description = "The ID that needs to find tag to be updated", required = true)
            @PathParam("id")
            Long id,
            @Valid
            TagDTO tagDTO
    );
}
