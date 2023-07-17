package edu.my.data.dto;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class MovieHasTagDTO {
    @Schema(title = "Link id", required = true)
    public Long id;
    @NotBlank
    @Schema(title = "The movie referenced by this link", required = false, hidden = true)
    public MovieDTO movie;
    @NotBlank
    @Schema(title = "The tag referenced by this link", required = false, hidden = true)
    public TagDTO tag;
}
