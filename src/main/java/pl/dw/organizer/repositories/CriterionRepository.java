package pl.dw.organizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dw.organizer.entities.CriterionEntity;

@Repository
public interface CriterionRepository extends JpaRepository<CriterionEntity, Long> {
}
