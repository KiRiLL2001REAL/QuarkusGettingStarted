package edu.my.resource.impl;

import edu.my.API.data.dto.TagDTO;
import edu.my.API.resource.TagResource;
import edu.my.service.controller_layer.TagControllerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResourceImpl implements TagResource {
    @Inject
    TagControllerService tagControllerService;

    @Override
    @Counted(name = "performedGetAllTags", description = "How many requests were made for getting list of tags")
    @Timed(name = "getAllTagsTimer", description = "A measure of how long it takes to get a list of tags", unit = MetricUnits.MILLISECONDS)
    public Response getAll() {
        return Response.ok(tagControllerService.getAll()).build();
    }

    @Override
    @Counted(name = "performedGetByIdTag", description = "How many requests were made for getting specific tag")
    @Timed(name = "getByIdTagTimer", description = "A measure of how long it takes to get specific tag", unit = MetricUnits.MILLISECONDS)
    public Response getById(Long id) {
        return Response.ok(tagControllerService.getById(id)).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedAddTag", description = "How many tags has been added")
    @Timed(name = "getAddTagTimer", description = "A measure of how long it takes to add tag", unit = MetricUnits.MILLISECONDS)
    public Response add(TagDTO tagDTO) {
        tagControllerService.add(tagDTO);
        return Response.status(Response.Status.CREATED).entity(tagControllerService.getAll()).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedDeleteByIdTag", description = "How many tags has been deleted")
    @Timed(name = "getDeleteByIdTagTimer", description = "A measure of how long it takes to delete tag", unit = MetricUnits.MILLISECONDS)
    public Response deleteById(Long id) {
        tagControllerService.deleteById(id);
        return Response.noContent().build();
    }

    @Override
    @Transactional
    @Counted(name = "performedUpdateTag", description = "How many tags has been updated")
    @Timed(name = "getUpdateTagTimer", description = "A measure of how long it takes to update tag", unit = MetricUnits.MILLISECONDS)
    public Response update(Long id, TagDTO tagDTO) {
        tagControllerService.update(id, tagDTO);
        return Response.ok(tagControllerService.getById(id)).build();
    }
}
