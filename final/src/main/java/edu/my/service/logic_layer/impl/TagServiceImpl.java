package edu.my.service.logic_layer.impl;

import edu.my.data.entity.MovieHasTagEntity;
import edu.my.data.entity.TagEntity;
import edu.my.data.mapper.TagMapper;
import edu.my.data.repository.MovieHasTagRepository;
import edu.my.data.repository.TagRepository;
import edu.my.exception.EntityIsNotFoundException;
import edu.my.service.logic_layer.TagService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TagServiceImpl implements TagService {
    @Inject
    TagRepository tagRepository;
    @Inject
    MovieHasTagRepository movieHasTagRepository;
    @Inject
    TagMapper tagMapper;

    @Override
    public void add(TagEntity tagEntity) {
        tagRepository.persist(tagEntity);
    }

    @Override
    public List<TagEntity> getAll() {
        List<TagEntity> tagList = tagRepository.findAll().list();
        if (tagList.isEmpty())
            throw new EntityIsNotFoundException("Can't find any tags in DB.");
        return tagList;
    }

    @Override
    public TagEntity getById(Long id) {
        Optional<TagEntity> shouldBeNotNullTag = tagRepository.findByIdOptional(id);
        if (shouldBeNotNullTag.isEmpty())
            throw new EntityIsNotFoundException("Can't find tag with id=" + id + ".");
        return shouldBeNotNullTag.get();
    }

    @Override
    public void update(Long id, TagEntity tagData) {
        Optional<TagEntity> shouldBeNotNullTag = tagRepository.findByIdOptional(id);
        if (shouldBeNotNullTag.isEmpty())
            throw new EntityIsNotFoundException("Can't find tag with id=" + id + ". Nothing to update.");

        TagEntity tagEntity = shouldBeNotNullTag.get();

        tagMapper.mapTo(tagData, tagEntity);
        tagRepository.persist(tagEntity);
    }

    @Override
    public void deleteById(Long id) {
        Optional<TagEntity> shouldBeNotNullTag = tagRepository.findByIdOptional(id);
        if (shouldBeNotNullTag.isEmpty())
            throw new EntityIsNotFoundException("Can't find tag with id=" + id + ". Nothing to delete.");

        TagEntity tagEntity = shouldBeNotNullTag.get();

        for (MovieHasTagEntity link : tagEntity.getLinks()) {
            link.getMovieEntity().getLinks().remove(link);
            movieHasTagRepository.delete(link);
        }
        tagEntity.setLinks(null);

        tagRepository.deleteById(id);
    }
}
