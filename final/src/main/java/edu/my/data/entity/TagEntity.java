package edu.my.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class TagEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy = "tagEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<MovieHasTagEntity> movieHasTagEntities = new HashSet<>();

    public void addMovieHasTagEntities(MovieHasTagEntity movieHasTag) {
        movieHasTag.tagEntity = this;
        this.movieHasTagEntities.add(movieHasTag);
    }
}
