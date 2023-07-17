package edu.my.service.logic_layer.impl;

import edu.my.data.entity.MovieEntity;
import edu.my.data.entity.MovieHasTagEntity;
import edu.my.data.entity.TagEntity;
import edu.my.data.repository.MovieHasTagRepository;
import edu.my.data.repository.MovieRepository;
import edu.my.exception.EntityIsNotFoundException;
import edu.my.exception.TagIsAlreadyBoundException;
import edu.my.service.logic_layer.MovieService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {
    @Inject
    MovieRepository movieRepository;
    @Inject
    MovieHasTagRepository movieHasTagRepository;
    @Inject
    TransactionManager transactionManager;

    @Override
    public void add(MovieEntity movieEntity) {
        movieRepository.persist(movieEntity);
    }

    @Override
    public List<MovieEntity> getAll() {
        List<MovieEntity> movieList = movieRepository.findAll().list();
        if (movieList.isEmpty())
            throw new EntityIsNotFoundException("Can't find any movies in DB.");
        return movieList;
    }

    @Override
    public MovieEntity getById(Long id) {
        MovieEntity notNullMovie = movieRepository.findById(id);
        if (notNullMovie == null)
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");
        return notNullMovie;
    }

    @Override
    public void update(Long id, MovieEntity movieEntity) {
        MovieEntity notNullMovie = movieRepository.findById(id);
        if (notNullMovie == null)
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ". Nothing to update.");
        notNullMovie.name = movieEntity.name;
        notNullMovie.description = movieEntity.description;
        notNullMovie.reasonsToView = movieEntity.reasonsToView;
        notNullMovie.facts = movieEntity.facts;
        notNullMovie.durationInSeconds = movieEntity.durationInSeconds;
        notNullMovie.distributor = movieEntity.distributor;
        notNullMovie.country = movieEntity.country;
        notNullMovie.releaseYear = movieEntity.releaseYear;
    }

    @Override
    public void deleteById(Long id) {
        MovieEntity notNullMovie = movieRepository.findById(id);
        if (notNullMovie == null)
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ". Nothing to delete.");
        movieRepository.deleteById(id);
    }

    @Override
    public List<TagEntity> getAttachedTags(Long id) {
        MovieEntity notNullMovie = movieRepository.findById(id);
        if (notNullMovie == null)
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");

        List<MovieHasTagEntity> linkList = movieHasTagRepository.find("movieEntity = ?1", notNullMovie).list();
        List<TagEntity> tagList = new ArrayList<>();
        for (MovieHasTagEntity link : linkList)
            tagList.add(link.tagEntity);
        return tagList;
    }

    @Override
    public void attachTag(Long id, TagEntity tagEntity) {
        MovieEntity notNullMovie = movieRepository.findById(id);
        if (notNullMovie == null)
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ". Nothing to attach to.");

        MovieHasTagEntity mustBeNullLink = movieHasTagRepository.find("movieEntity = ?1 AND tagEntity = ?2", notNullMovie, tagEntity).firstResult();
        if (mustBeNullLink != null)
            throw new TagIsAlreadyBoundException("Tag '" + mustBeNullLink.tagEntity.name + "' is already bound to movie '" + notNullMovie.name + "'.");

        MovieHasTagEntity link = new MovieHasTagEntity();
        link.movieEntity = notNullMovie;
        link.tagEntity = tagEntity;
        movieHasTagRepository.persist(link);

        notNullMovie.addLink(link);
        tagEntity.addLink(link);
    }

    @Override
    @Transactional
    public void attachTags(Long id, Set<TagEntity> tagEntitySet) throws SystemException {
        try {
            for (TagEntity tag : tagEntitySet)
                attachTag(id, tag);
        } catch (EntityIsNotFoundException | TagIsAlreadyBoundException e) {
            transactionManager.setRollbackOnly();
            throw e;
        }
    }

    @Override
    public void detachTag(Long id, TagEntity tagEntity) {
        MovieEntity notNullMovie = movieRepository.findById(id);
        if (notNullMovie == null)
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");

        MovieHasTagEntity notNullLink = movieHasTagRepository.find("movieEntity = ?1 AND tagEntity = ?2", notNullMovie, tagEntity).firstResult();
        if (notNullLink == null)
            throw new EntityIsNotFoundException("Can't find link with movieId=" + id + " and tagId=" + tagEntity.id + ". Nothing to detach.");

        notNullMovie.removeLink(notNullLink);
        tagEntity.removeLink(notNullLink);
        movieHasTagRepository.delete(notNullLink);
    }

    @Override
    @Transactional
    public void detachAllTags(Long id) throws SystemException {
        try {
            MovieEntity notNullMovie = movieRepository.findById(id);
            if (notNullMovie == null)
                throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");

            List<MovieHasTagEntity> linksToDetach = movieHasTagRepository.find("movieEntity = ?1", notNullMovie).list();
            for (MovieHasTagEntity link : linksToDetach)
                detachTag(id, link.tagEntity);
        } catch (EntityIsNotFoundException e) {
            transactionManager.setRollbackOnly();
            throw e;
        }
    }
}