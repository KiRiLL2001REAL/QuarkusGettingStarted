package edu.my.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "screening")
public class ScreeningEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_hall")
    @JsonIgnore
    public HallEntity hallEntity;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_movie")
    @JsonIgnore
    public MovieEntity movieEntity;

    @Column(name = "showing_time")
    public LocalTime showingTime;
}
