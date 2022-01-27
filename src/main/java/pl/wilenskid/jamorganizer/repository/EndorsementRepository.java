package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Endorsement;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.User;

import java.util.Optional;

@Repository
public interface EndorsementRepository extends CrudRepository<Endorsement, Long> {
    Optional<Endorsement> findByProjectAndUser(Project project, User user);
}
