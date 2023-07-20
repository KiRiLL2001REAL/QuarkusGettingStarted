package edu.my.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagDTO {
    @Schema(title = "Tag id", required = true)
    private Long id;
    @NotBlank
    @Schema(title = "Tag description", required = true)
    private String name;

    @Schema(title = "Link of the tag with the films that include it", required = false, hidden = true)
    @JsonIgnore
    private Set<MovieHasTagDTO> tagHasMovieSet;
}
