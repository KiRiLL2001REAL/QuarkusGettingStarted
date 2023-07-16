package edu.my.data.dto;

import jakarta.validation.constraints.NotBlank;

public class MovieHasTagDTO {
    public Long id;
    @NotBlank
    public MovieDTO movie;
    @NotBlank
    public TagDTO tag;
}
