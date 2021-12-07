package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.wilenskid.jamorganizer.entity.Criterion;

@Repository
public interface CriterionRepository extends CrudRepository<Criterion, Long> {
}
