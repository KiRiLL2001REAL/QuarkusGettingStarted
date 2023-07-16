package edu.my.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class TicketEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_screening")
    @JsonIgnore
    public ScreeningEntity screeningEntity;

    @Column(name = "row")
    public Integer row;

    @Column(name = "seat")
    public Integer seat;

    @Column(name = "cost")
    public Float cost;
}
