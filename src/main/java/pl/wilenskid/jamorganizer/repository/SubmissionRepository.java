package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.Submission;

import java.util.List;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Long> {
    List<Submission> findAllByEvent(Event event);
}
