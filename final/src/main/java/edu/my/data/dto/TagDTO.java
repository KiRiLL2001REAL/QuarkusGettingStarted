package edu.my.data.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class TagDTO {
    public Long id;
    @NotBlank
    public String name;

    Set<MovieHasTagDTO> tagHasMovieSet;
}
