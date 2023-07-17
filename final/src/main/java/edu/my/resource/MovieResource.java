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

import java.util.Set;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {
    @Inject
    MovieControllerService movieControllerService;

    @GET
    public Response getAll() {
        return Response.ok(movieControllerService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(movieControllerService.getById(id)).build();
    }

    @POST
    @Transactional
    public Response add(@Valid MovieDTO movieDTO) {
        movieControllerService.add(movieDTO);
        return Response.status(Response.Status.CREATED).entity(movieControllerService.getAll()).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        movieControllerService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid MovieDTO movieDTO) {
        movieControllerService.update(id, movieDTO);
        return Response.ok(movieControllerService.getById(id)).build();
    }

    @GET
    @Transactional
    @Path("/tags/{id}")
    public Response getAllTags(@PathParam("id") Long id) {
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @POST
    @Transactional
    @Path("/tag")
    public Response attachTag(@QueryParam("movie_id") Long id, @Valid TagDTO tagDTO) {
        movieControllerService.attachTag(id, tagDTO);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @POST
    @Transactional
    @Path("/tags")
    public Response attachTags(@QueryParam("movie_id") Long id, Set<@Valid TagDTO> tagDTOSet) throws SystemException {
        movieControllerService.attachTags(id, tagDTOSet);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @DELETE
    @Transactional
    @Path("/tag")
    public Response detachTag(@QueryParam("movie_id") Long id, @Valid TagDTO tagDTO) {
        movieControllerService.detachTag(id, tagDTO);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @DELETE
    @Transactional
    @Path("/tags")
    public Response detachAllTags(@QueryParam("movie_id") Long id) throws SystemException {
        movieControllerService.detachAllTags(id);
        return Response.noContent().build();
    }
}
