package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Criterion;

@Repository
public interface CriterionRepository extends JpaRepository<Criterion, Long> {
}
