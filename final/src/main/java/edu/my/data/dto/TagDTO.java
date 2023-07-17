package edu.my.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class TagDTO {
    public Long id;
    @NotBlank
    public String name;

    @JsonIgnore
    Set<MovieHasTagDTO> tagHasMovieSet;
}
