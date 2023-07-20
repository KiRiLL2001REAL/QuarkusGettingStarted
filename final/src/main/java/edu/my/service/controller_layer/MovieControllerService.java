package edu.my.service.controller_layer;

import edu.my.api.dto.MovieDTO;
import edu.my.api.dto.TagDTO;
import jakarta.transaction.SystemException;

import java.util.List;

public interface MovieControllerService {
    void add(MovieDTO movieDTO);
    List<MovieDTO> getAll();
    MovieDTO getById(Long id);
    void update(Long id, MovieDTO movieDTO);
    void deleteById(Long id);

    List<TagDTO> getAttachedTags(Long id);
    void attachTag(Long id, TagDTO tagDTO);
    void attachTags(Long id, List<TagDTO> tagDTOSet) throws SystemException;
    void detachTag(Long id, TagDTO tagDTO);
    void detachAllTags(Long id) throws SystemException;
}
