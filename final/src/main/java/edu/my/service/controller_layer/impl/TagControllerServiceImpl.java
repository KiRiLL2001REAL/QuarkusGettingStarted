package edu.my.service.controller_layer.impl;

import edu.my.data.dto.TagDTO;
import edu.my.data.entity.TagEntity;
import edu.my.data.mapper.MovieHasTagMapper;
import edu.my.data.mapper.TagMapper;
import edu.my.data.repository.TagRepo;
import edu.my.service.controller_layer.TagControllerService;
import edu.my.service.logic_layer.TagService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TagControllerServiceImpl implements TagControllerService {
    @Inject
    TagMapper tagMapper;
    @Inject
    TagService tagService;

    @Override
    public void add(TagDTO tagDTO) {
        tagService.add(tagMapper.toEntity(tagDTO));
    }

    @Override
    public List<TagDTO> getAll() {
        return tagMapper.toDTO(tagService.getAll());
    }

    @Override
    public TagDTO getById(Long id) {
        return tagMapper.toDTO(tagService.getById(id));
    }

    @Override
    public void update(Long id, TagDTO tagDTO) {
        tagService.update(id, tagMapper.toEntity(tagDTO));
    }

    @Override
    public void deleteById(Long id) {
        tagService.deleteById(id);
    }
}
