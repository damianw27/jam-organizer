package pl.dw.organizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dw.organizer.entities.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
