package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
