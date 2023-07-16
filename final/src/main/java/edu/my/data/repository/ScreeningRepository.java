package edu.my.data.repository;

import edu.my.data.entity.ScreeningEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScreeningRepository implements PanacheRepository<ScreeningEntity> {
}
