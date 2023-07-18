package edu.my.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class TagEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 64)
    private String name;

    @OneToMany(mappedBy = "tagEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MovieHasTagEntity> links = new HashSet<>();

    public void addLink(MovieHasTagEntity movieHasTag) {
        this.links.add(movieHasTag);
        movieHasTag.setTagEntity(this);
    }

    public void removeLink(MovieHasTagEntity movieHasTag) {
        this.links.remove(movieHasTag);
        movieHasTag.setTagEntity(null);
    }
}
