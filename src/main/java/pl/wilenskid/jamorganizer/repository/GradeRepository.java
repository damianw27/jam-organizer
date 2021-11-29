package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Grade;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long> {
}
