package edu.my.resource;

import edu.my.data.dto.TagDTO;
import edu.my.service.controller_layer.TagControllerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {
    @Inject
    TagControllerService tagControllerService;

    @GET
    public Response getAll() {
        return Response.ok(tagControllerService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(tagControllerService.getById(id)).build();
    }

    @POST
    @Transactional
    public Response add(@Valid TagDTO tagDTO) {
        tagControllerService.add(tagDTO);
        return Response.status(Response.Status.CREATED).entity(tagControllerService.getAll()).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        tagControllerService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid TagDTO tagDTO) {
        tagControllerService.update(id, tagDTO);
        return Response.ok(tagControllerService.getById(id)).build();
    }
}