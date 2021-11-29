package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
