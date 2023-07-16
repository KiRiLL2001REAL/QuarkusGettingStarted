package edu.my.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class MovieDTO {
    public Long id;
    @NotBlank
    public String name;
    public String description;
    public String reasonsToView;
    public String facts;
    @NotBlank
    @Min(value = 60, message = "The value must be more than 59 (at least 1 minute)")
    @Max(value = 21600, message = "The value must be less than 21601 (at most 6 hours)")
    public Integer durationInSeconds;
    public String distributor;
    @NotBlank
    public String country;
    @NotBlank
    @Min(value = 1895, message = "The value must be more than 1894")
    @Max(value = 2100, message = "The value must be less than 2101")
    public Integer releaseYear;

    public Set<MovieHasTagDTO> movieHasTagSet;
}
