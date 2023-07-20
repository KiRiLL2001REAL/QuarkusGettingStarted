package edu.my.service;

import edu.my.api.dto.MovieDTO;
import edu.my.api.dto.TagDTO;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/movies")
@RegisterRestClient(configKey = "movie-api")
public interface MovieControllerService {
    @POST
    void add(@Valid MovieDTO movieDTO);
    @GET
    List<MovieDTO> getAll();
    @GET
    @Path("/{id}")
    MovieDTO getById(@PathParam("id") Long id);
    @PUT
    @Path("/{id}")
    void update(@PathParam("id") Long id, @Valid MovieDTO movieDTO);
    @DELETE
    @Path("/{id}")
    void deleteById(@PathParam("id") Long id);

    @GET
    @Path("/tags/{id}")
    List<TagDTO> getAttachedTags(@PathParam("id") Long id);
    @POST
    @Path("/tag")
    void attachTag(@QueryParam("movie_id") Long id, @Valid TagDTO tagDTO);
    @POST
    @Path("/tags")
    void attachTags(@QueryParam("movie_id") Long id, List<@Valid TagDTO> tagDTOSet) throws SystemException;
    @DELETE
    @Path("/tag")
    void detachTag(@QueryParam("movie_id") Long id, @Valid TagDTO tagDTO);
    @DELETE
    @Path("/tags")
    void detachAllTags(@QueryParam("movie_id") Long id) throws SystemException;
}
