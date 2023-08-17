package edu.my.resource;

import edu.my.data.dto.TagDTO;
import edu.my.service.controller_layer.TagControllerService;
import jakarta.inject.Inject;
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

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {
    @Inject
    TagControllerService tagControllerService;

    @GET
    @Operation(summary = "Get list of tags", description = "Shows all available tags")
    @APIResponse(
            responseCode = "200",
            description = "List of all tags",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    @Counted(name = "performedGetAllTags", description = "How many requests were made for getting list of tags")
    @Timed(name = "getAllTagsTimer", description = "A measure of how long it takes to get a list of tags", unit = MetricUnits.MILLISECONDS)
    public Response getAll() {
        return Response.ok(tagControllerService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get tag by ID", description = "Shows available tag")
    @APIResponse(
            responseCode = "200",
            description = "Selected tag",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    @Counted(name = "performedGetByIdTag", description = "How many requests were made for getting specific tag")
    @Timed(name = "getByIdTagTimer", description = "A measure of how long it takes to get specific tag", unit = MetricUnits.MILLISECONDS)
    public Response getById(
            @Parameter(description = "The ID that needs to be fetched", required = true)
            @PathParam("id")
            Long id
    ) {
        return Response.ok(tagControllerService.getById(id)).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Add not existing tag", description = "Adds new tag")
    @APIResponse(
            responseCode = "201",
            description = "Tag is added",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    @Counted(name = "performedAddTag", description = "How many tags has been added")
    @Timed(name = "getAddTagTimer", description = "A measure of how long it takes to add tag", unit = MetricUnits.MILLISECONDS)
    public Response add(
            @Valid
            TagDTO tagDTO
    ) {
        tagControllerService.add(tagDTO);
        return Response.status(Response.Status.CREATED).entity(tagControllerService.getAll()).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Delete existing tag", description = "Deletes the tag")
    @APIResponse(
            responseCode = "204",
            description = "Tag is deleted"
    )
    @Counted(name = "performedDeleteByIdTag", description = "How many tags has been deleted")
    @Timed(name = "getDeleteByIdTagTimer", description = "A measure of how long it takes to delete tag", unit = MetricUnits.MILLISECONDS)
    public Response deleteById(
            @Parameter(description = "The tag's id that needs to be deleted", required = true)
            @PathParam("id")
            Long id
    ) {
        tagControllerService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Operation(summary = "Update available tag", description = "Updates the tag")
    @APIResponse(
            responseCode = "200",
            description = "Tag is updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))
    )
    @Counted(name = "performedUpdateTag", description = "How many tags has been updated")
    @Timed(name = "getUpdateTagTimer", description = "A measure of how long it takes to update tag", unit = MetricUnits.MILLISECONDS)
    public Response update(
            @Parameter(description = "The ID that needs to find tag to be updated", required = true)
            @PathParam("id")
            Long id,
            @Valid
            TagDTO tagDTO
    ) {
        tagControllerService.update(id, tagDTO);
        return Response.ok(tagControllerService.getById(id)).build();
    }
}
