package edu.my.data.mapper;

import edu.my.data.dto.MovieDTO;
import edu.my.data.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "jakarta",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MovieMapper {
    MovieDTO toDTO(MovieEntity movieEntity);
    List<MovieDTO> toDTO(List<MovieEntity> movieEntityList);
    Set<MovieDTO> toDTO(Set<MovieEntity> movieEntitySet);
    MovieEntity toEntity(MovieDTO movieDTO);
    List<MovieEntity> toEntity(List<MovieDTO> movieDTOList);
    Set<MovieEntity> toEntity(Set<MovieDTO> movieDTOSet);
}
