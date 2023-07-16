package edu.my.data.repository;

import edu.my.data.entity.TicketEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<TicketEntity> {
}
