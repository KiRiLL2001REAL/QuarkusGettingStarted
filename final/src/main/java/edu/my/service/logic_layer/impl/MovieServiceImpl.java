package edu.my.service.logic_layer.impl;

import edu.my.data.entity.MovieEntity;
import edu.my.data.entity.MovieHasTagEntity;
import edu.my.data.entity.TagEntity;
import edu.my.data.mapper.MovieMapper;
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
import java.util.Optional;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {
    @Inject
    MovieRepository movieRepository;
    @Inject
    MovieHasTagRepository movieHasTagRepository;
    @Inject
    MovieMapper movieMapper;

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
        Optional<MovieEntity> shouldBeNotNullMovie = movieRepository.findByIdOptional(id);
        if (shouldBeNotNullMovie.isEmpty())
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");
        return shouldBeNotNullMovie.get();
    }

    @Override
    public void update(Long id, MovieEntity movieData) {
        Optional<MovieEntity> shouldBeNotNullMovie = movieRepository.findByIdOptional(id);
        if (shouldBeNotNullMovie.isEmpty())
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ". Nothing to update.");

        MovieEntity movieEntity = shouldBeNotNullMovie.get();

        movieMapper.mapTo(movieData, movieEntity);
        movieRepository.persist(movieEntity);
    }

    @Override
    public void deleteById(Long id) {
        Optional<MovieEntity> shouldBeNotNullMovie = movieRepository.findByIdOptional(id);
        if (shouldBeNotNullMovie.isEmpty())
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ". Nothing to delete.");

        MovieEntity movieEntity = shouldBeNotNullMovie.get();

        for (MovieHasTagEntity link : movieEntity.getLinks()) {
            link.getTagEntity().getLinks().remove(link);
            movieHasTagRepository.delete(link);
        }

        movieEntity.setLinks(null);
        movieRepository.deleteById(id);
    }

    @Override
    public List<TagEntity> getAttachedTags(Long id) {
        Optional<MovieEntity> shouldBeNotNullMovie = movieRepository.findByIdOptional(id);
        if (shouldBeNotNullMovie.isEmpty())
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");

        MovieEntity movieEntity = shouldBeNotNullMovie.get();

        List<MovieHasTagEntity> linkList = movieHasTagRepository.find("movieEntity = ?1", movieEntity).list();
        List<TagEntity> tagList = new ArrayList<>();
        for (MovieHasTagEntity link : linkList)
            tagList.add(link.getTagEntity());

        return tagList;
    }

    @Override
    public void attachTag(Long id, TagEntity tagEntity) {
        Optional<MovieEntity> shouldBeNotNullMovie = movieRepository.findByIdOptional(id);
        if (shouldBeNotNullMovie.isEmpty())
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ". Nothing to attach to.");

        MovieEntity movieEntity = shouldBeNotNullMovie.get();

        MovieHasTagEntity mustBeNullLink = movieHasTagRepository.find("movieEntity = ?1 AND tagEntity = ?2", movieEntity, tagEntity).firstResult();
        if (mustBeNullLink != null)
            throw new TagIsAlreadyBoundException("Tag '" + mustBeNullLink.getTagEntity().getName() + "' is already bound to movie '" + movieEntity.getName() + "'.");

        MovieHasTagEntity link = new MovieHasTagEntity();
        link.setMovieEntity(movieEntity);
        link.setTagEntity(tagEntity);
        movieHasTagRepository.persist(link);

        movieEntity.addLink(link);
        tagEntity.addLink(link);
    }

    @Override
    @Transactional
    public void attachTags(Long id, List<TagEntity> tagEntityList) throws SystemException {
        try {
            for (TagEntity tag : tagEntityList)
                attachTag(id, tag);
        } catch (EntityIsNotFoundException | TagIsAlreadyBoundException e) {
            transactionManager.setRollbackOnly();
            throw e;
        }
    }

    @Override
    public void detachTag(Long id, TagEntity tagEntity) {
        Optional<MovieEntity> shouldBeNotNullMovie = movieRepository.findByIdOptional(id);
        if (shouldBeNotNullMovie.isEmpty())
            throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");

        MovieEntity movieEntity = shouldBeNotNullMovie.get();

        Optional<MovieHasTagEntity> shouldBeNotNullLink = movieHasTagRepository.find("movieEntity = ?1 AND tagEntity = ?2", movieEntity, tagEntity).firstResultOptional();
        if (shouldBeNotNullLink.isEmpty())
            throw new EntityIsNotFoundException("Can't find link with movieId=" + id + " and tagId=" + tagEntity.getId() + ". Nothing to detach.");

        MovieHasTagEntity movieHasTagEntity = shouldBeNotNullLink.get();

        movieEntity.removeLink(movieHasTagEntity);
        tagEntity.removeLink(movieHasTagEntity);
        movieHasTagRepository.delete(movieHasTagEntity);
    }

    @Override
    @Transactional
    public void detachAllTags(Long id) throws SystemException {
        try {
            Optional<MovieEntity> shouldBeNotNullMovie = movieRepository.findByIdOptional(id);
            if (shouldBeNotNullMovie.isEmpty())
                throw new EntityIsNotFoundException("Can't find movie with id=" + id + ".");

            MovieEntity movieEntity = shouldBeNotNullMovie.get();

            List<MovieHasTagEntity> linksToDetach = movieHasTagRepository.find("movieEntity = ?1", movieEntity).list();
            for (MovieHasTagEntity link : linksToDetach)
                detachTag(id, link.getTagEntity());
        } catch (EntityIsNotFoundException e) {
            transactionManager.setRollbackOnly();
            throw e;
        }
    }
}
