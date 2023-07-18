package edu.my.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "movie_has_tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieHasTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_movie")
    @JsonIgnore
    private MovieEntity movieEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tag")
    @JsonIgnore
    private TagEntity tagEntity;

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        MovieHasTagEntity toCompare = (MovieHasTagEntity) obj;
        return Objects.equals(id, toCompare.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
