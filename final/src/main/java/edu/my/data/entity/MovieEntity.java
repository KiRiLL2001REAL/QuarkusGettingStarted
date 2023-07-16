package edu.my.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
public class MovieEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "reasons_to_view")
    public String reasonsToView;

    @Column(name = "facts")
    public String facts;

    @Column(name = "duration")
    public Integer durationInSeconds;

    @Column(name = "distributor")
    public String distributor;

    @Column(name = "country")
    public String country;

    @Column(name = "release_year")
    public Integer releaseYear;

    @OneToMany(mappedBy = "movieEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<MovieHasTagEntity> movieHasTagEntities = new HashSet<>();

    public void addMovieHasTagEntities(MovieHasTagEntity movieHasTag) {
        movieHasTag.movieEntity = this;
        this.movieHasTagEntities.add(movieHasTag);
    }
}
