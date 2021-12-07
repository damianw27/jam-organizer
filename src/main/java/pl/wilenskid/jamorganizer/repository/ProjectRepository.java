package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
