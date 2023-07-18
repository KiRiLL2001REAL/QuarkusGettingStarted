package edu.my.data.mapper;

import edu.my.data.dto.TagDTO;
import edu.my.data.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "jakarta",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TagMapper {
    TagDTO toDTO(TagEntity tagEntity);
    List<TagDTO> toDTO(List<TagEntity> tagEntityList);
    TagEntity toEntity(TagDTO tagDTO);
    List<TagEntity> toEntity(List<TagDTO> tagDTOList);
    void mapTo(TagEntity src, @MappingTarget TagEntity upd);
}
