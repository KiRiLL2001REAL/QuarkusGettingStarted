package edu.my.data.mapper;

import edu.my.api.dto.MovieHasTagDTO;
import edu.my.data.entity.MovieHasTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "jakarta",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MovieMapper.class, TagMapper.class}
)
public interface MovieHasTagMapper {
    MovieHasTagDTO toDTO(MovieHasTagEntity movieHasTagEntity);
    MovieHasTagEntity toEntity(MovieHasTagDTO movieHasTagDTO);
}
