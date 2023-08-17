package edu.my.service.controller_layer.impl;

import edu.my.API.data.dto.MovieDTO;
import edu.my.API.data.dto.TagDTO;
import edu.my.data.mapper.MovieMapper;
import edu.my.data.mapper.TagMapper;
import edu.my.service.controller_layer.MovieControllerService;
import edu.my.service.logic_layer.MovieService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;

import java.util.List;

@ApplicationScoped
public class MovieControllerServiceImpl implements MovieControllerService {
    @Inject
    MovieMapper movieMapper;
    @Inject
    TagMapper tagMapper;
    @Inject
    MovieService movieService;

    @Override
    public void add(MovieDTO movieDTO) {
        movieService.add(movieMapper.toEntity(movieDTO));
    }

    @Override
    public List<MovieDTO> getAll() {
        return movieMapper.toDTO(movieService.getAll());
    }

    @Override
    public MovieDTO getById(Long id) {
        return movieMapper.toDTO(movieService.getById(id));
    }

    @Override
    public void update(Long id, MovieDTO movieDTO) {
        movieService.update(id, movieMapper.toEntity(movieDTO));
    }

    @Override
    public void deleteById(Long id) {
        movieService.deleteById(id);
    }

    @Override
    public List<TagDTO> getAttachedTags(Long id) {
        return tagMapper.toDTO(movieService.getAttachedTags(id));
    }

    @Override
    public void attachTag(Long id, TagDTO tagDTO) {
        movieService.attachTag(id, tagMapper.toEntity(tagDTO));
    }

    @Override
    public void attachTags(Long id, List<TagDTO> tagDTOList) throws SystemException {
        movieService.attachTags(id, tagMapper.toEntity(tagDTOList));
    }

    @Override
    public void detachTag(Long id, TagDTO tagDTO) {
        movieService.detachTag(id, tagMapper.toEntity(tagDTO));
    }

    @Override
    public void detachAllTags(Long id) throws SystemException {
        movieService.detachAllTags(id);
    }
}
