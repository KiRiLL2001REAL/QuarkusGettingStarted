package edu.my.resource.impl;

import edu.my.API.data.dto.MovieDTO;
import edu.my.API.data.dto.TagDTO;
import edu.my.API.resource.MovieResource;
import edu.my.service.controller_layer.MovieControllerService;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResourceImpl implements MovieResource {
    @Inject
    MovieControllerService movieControllerService;

    @Override
    @Counted(name = "performedGetAllMovies", description = "How many requests were made for getting list of movies")
    @Timed(name = "getAllMoviesTimer", description = "A measure of how long it takes to get a list of movies", unit = MetricUnits.MILLISECONDS)
    public Response getAll() {
        return Response.ok(movieControllerService.getAll()).build();
    }

    @Override
    @Counted(name = "performedGetByIdMovie", description = "How many requests were made for getting specific movie")
    @Timed(name = "getByIdMovieTimer", description = "A measure of how long it takes to get specific movie", unit = MetricUnits.MILLISECONDS)
    public Response getById(Long id) {
        return Response.ok(movieControllerService.getById(id)).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedAddMovie", description = "How many movies has been added")
    @Timed(name = "getAddMovieTimer", description = "A measure of how long it takes to add movie", unit = MetricUnits.MILLISECONDS)
    public Response add(MovieDTO movieDTO) {
        movieControllerService.add(movieDTO);
        return Response.status(Response.Status.CREATED).entity(movieControllerService.getAll()).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedDeleteByIdMovie", description = "How many movies has been deleted")
    @Timed(name = "getDeleteByIdMovieTimer", description = "A measure of how long it takes to delete movie", unit = MetricUnits.MILLISECONDS)
    public Response deleteById(Long id) {
        movieControllerService.deleteById(id);
        return Response.noContent().build();
    }

    @Override
    @Transactional
    @Counted(name = "performedUpdateMovie", description = "How many movies has been updated")
    @Timed(name = "getUpdateMovieTimer", description = "A measure of how long it takes to update movie", unit = MetricUnits.MILLISECONDS)
    public Response update(Long id, MovieDTO movieDTO) {
        movieControllerService.update(id, movieDTO);
        return Response.ok(movieControllerService.getById(id)).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedGetAllMovieTags", description = "How many requests were made for getting list of movie tags")
    @Timed(name = "getAllMovieTagsTimer", description = "A measure of how long it takes to get a list of movie tags", unit = MetricUnits.MILLISECONDS)
    public Response getAllTags(Long id) {
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedAttachMovieTag", description = "How many tags has been added to movies")
    @Timed(name = "getAttachMovieTagTimer", description = "A measure of how long it takes to add tag to movie", unit = MetricUnits.MILLISECONDS)
    public Response attachTag(Long id, TagDTO tagDTO) {
        movieControllerService.attachTag(id, tagDTO);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedAttachMovieMultipleTags", description = "How many multiple attaches of tags has been performed to movies")
    @Timed(name = "getAttachMovieMultipleTagsTimer", description = "A measure of how long it takes to add multiple tags to movie", unit = MetricUnits.MILLISECONDS)
    public Response attachTags(Long id, List<@Valid TagDTO> tagDTOList) throws SystemException {
        movieControllerService.attachTags(id, tagDTOList);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedDetachMovieTag", description = "How many tags has been detached from movies")
    @Timed(name = "getDetachMovieTagTimer", description = "A measure of how long it takes to detach tag from movie", unit = MetricUnits.MILLISECONDS)
    public Response detachTag(Long id, TagDTO tagDTO) {
        movieControllerService.detachTag(id, tagDTO);
        return Response.ok(movieControllerService.getAttachedTags(id)).build();
    }

    @Override
    @Transactional
    @Counted(name = "performedDetachMovieAllTags", description = "How many times all tags has been detached from movies")
    @Timed(name = "getDetachMovieAllTagsTimer", description = "A measure of how long it takes to detach all tags from movie", unit = MetricUnits.MILLISECONDS)
    public Response detachAllTags(Long id) throws SystemException {
        movieControllerService.detachAllTags(id);
        return Response.noContent().build();
    }
}
