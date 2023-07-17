package edu.my.data.mapper;

import edu.my.data.dto.MovieHasTagDTO;
import edu.my.data.entity.MovieHasTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "jakarta",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MovieMapper.class, TagMapper.class}
)
public interface MovieHasTagMapper {
    MovieHasTagDTO toDTO(MovieHasTagEntity movieHasTagEntity);
    MovieHasTagEntity toEntity(MovieHasTagDTO movieHasTagDTO);
}
