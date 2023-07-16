package edu.my.data.repository;

import edu.my.data.entity.ScreeningEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScreeningRepo implements PanacheRepository<ScreeningEntity> {
}
