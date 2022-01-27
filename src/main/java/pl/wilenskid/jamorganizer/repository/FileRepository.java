package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.File;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {
}
