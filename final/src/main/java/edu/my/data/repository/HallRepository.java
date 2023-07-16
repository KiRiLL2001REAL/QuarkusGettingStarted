package edu.my.data.repository;

import edu.my.data.entity.HallEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HallRepository implements PanacheRepository<HallEntity> {
}
