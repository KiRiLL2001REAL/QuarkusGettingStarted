package edu.my.data.mapper;

import edu.my.data.dto.TagDTO;
import edu.my.data.entity.TagEntity;
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
public interface TagMapper {
    TagDTO toDTO(TagEntity tagEntity);
    List<TagDTO> toDTO(List<TagEntity> tagEntityList);
    Set<TagDTO> toDTO(Set<TagEntity> tagEntitySet);
    TagEntity toEntity(TagDTO tagDTO);
    List<TagEntity> toEntity(List<TagDTO> tagDTOList);
    Set<TagEntity> toEntity(Set<TagDTO> tagDTOSet);
}
