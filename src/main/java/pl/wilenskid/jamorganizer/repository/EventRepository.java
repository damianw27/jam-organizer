package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}
