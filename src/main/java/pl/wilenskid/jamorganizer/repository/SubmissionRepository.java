package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Submission;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Long> {
}
