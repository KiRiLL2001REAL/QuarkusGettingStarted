package edu.my.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "movie_has_tag")
public class MovieHasTagEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_movie")
    @JsonIgnore
    public MovieEntity movieEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tag")
    @JsonIgnore
    public TagEntity tagEntity;
}
