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

    @Column(name = "name", length = 64)
    public String name;

    @Column(name = "description", length = 1024)
    public String description;

    @Column(name = "reasons_to_view", length = 3072)
    public String reasonsToView;

    @Column(name = "facts", length = 3072)
    public String facts;

    @Column(name = "duration")
    public Integer durationInSeconds;

    @Column(name = "distributor", length = 32)
    public String distributor;

    @Column(name = "country", length = 32)
    public String country;

    @Column(name = "release_year")
    public Integer releaseYear;

    @OneToMany(mappedBy = "movieEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<MovieHasTagEntity> links = new HashSet<>();

    public void addLink(MovieHasTagEntity movieHasTag) {
        this.links.add(movieHasTag);
        movieHasTag.movieEntity = this;
    }

    public void removeLink(MovieHasTagEntity movieHasTag) {
        this.links.remove(movieHasTag);
        movieHasTag.movieEntity = null;
    }
}
