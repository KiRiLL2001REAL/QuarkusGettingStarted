package edu.my.service;

import edu.my.api.dto.TagDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/tags")
@RegisterRestClient(configKey = "tag-api")
public interface TagControllerService {
    @POST
    void add(@Valid TagDTO tagDTO);
    @GET
    List<TagDTO> getAll();
    @GET
    @Path("/{id}")
    TagDTO getById(@PathParam("id") Long id);
    @PUT
    @Path("/{id}")
    void update(@PathParam("id") Long id, @Valid TagDTO tagDTO);
    @DELETE
    @Path("/{id}")
    void deleteById(@PathParam("id") Long id);
}
