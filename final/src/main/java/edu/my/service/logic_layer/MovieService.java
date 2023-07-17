package edu.my.service.logic_layer;

import edu.my.data.entity.MovieEntity;
import edu.my.data.entity.TagEntity;
import jakarta.transaction.SystemException;

import java.util.List;
import java.util.Set;

public interface MovieService {
    void add(MovieEntity movieEntity);
    List<MovieEntity> getAll();
    MovieEntity getById(Long id);
    void update(Long id, MovieEntity movieEntity);
    void deleteById(Long id);

    List<TagEntity> getAttachedTags(Long id);
    void attachTag(Long id, TagEntity tagEntity);
    void attachTags(Long id, Set<TagEntity> tagEntitySet) throws SystemException;
    void detachTag(Long id, TagEntity tagEntity);
    void detachAllTags(Long id) throws SystemException;
}
