package edu.my.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class MovieEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "reasons_to_view", length = 3072)
    private String reasonsToView;

    @Column(name = "facts", length = 3072)
    private String facts;

    @Column(name = "duration")
    private Integer durationInSeconds;

    @Column(name = "distributor", length = 32)
    private String distributor;

    @Column(name = "country", length = 32)
    private String country;

    @Column(name = "release_year")
    private Integer releaseYear;

    @OneToMany(mappedBy = "movieEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MovieHasTagEntity> links = new HashSet<>();

    public void addLink(MovieHasTagEntity movieHasTag) {
        this.links.add(movieHasTag);
        movieHasTag.setMovieEntity(this);
    }

    public void removeLink(MovieHasTagEntity movieHasTag) {
        this.links.remove(movieHasTag);
        movieHasTag.setMovieEntity(null);
    }
}
