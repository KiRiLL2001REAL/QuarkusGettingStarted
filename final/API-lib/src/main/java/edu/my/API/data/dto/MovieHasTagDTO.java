package edu.my.API.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieHasTagDTO {
    @Schema(title = "Link id", required = true)
    private Long id;
    @NotBlank
    @Schema(title = "The movie referenced by this link", required = false, hidden = true)
    private MovieDTO movie;
    @NotBlank
    @Schema(title = "The tag referenced by this link", required = false, hidden = true)
    private TagDTO tag;
}
