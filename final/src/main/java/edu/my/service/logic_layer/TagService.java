package edu.my.service.logic_layer;

import edu.my.data.entity.TagEntity;

import java.util.List;

public interface TagService {
    void add(TagEntity tagEntity);
    List<TagEntity> getAll();
    TagEntity getById(Long id);
    void update(Long id, TagEntity tagData);
    void deleteById(Long id);
}
