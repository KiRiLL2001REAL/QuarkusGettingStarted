package edu.my.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Set;

public class TagDTO {
    @Schema(title = "Tag id", required = true)
    public Long id;
    @NotBlank
    @Schema(title = "Tag description", required = true)
    public String name;

    @Schema(title = "Link of the tag with the films that include it", required = false)
    @JsonIgnore
    Set<MovieHasTagDTO> tagHasMovieSet;
}
