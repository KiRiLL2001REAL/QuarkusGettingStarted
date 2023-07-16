package edu.my.data.repository;

import edu.my.data.entity.TagEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TagRepo implements PanacheRepository<TagEntity> {
}
