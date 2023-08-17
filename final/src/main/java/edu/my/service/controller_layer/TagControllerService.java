package edu.my.service.controller_layer;

import edu.my.API.data.dto.TagDTO;

import java.util.List;

public interface TagControllerService {
    void add(TagDTO tagDTO);
    List<TagDTO> getAll();
    TagDTO getById(Long id);
    void update(Long id, TagDTO tagDTO);
    void deleteById(Long id);
}
