package edu.my.data.mapper;

import edu.my.data.dto.MovieDTO;
import edu.my.data.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "jakarta",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MovieMapper {
    MovieDTO toDTO(MovieEntity movieEntity);
    MovieEntity toEntity(MovieDTO movieDTO);
}
