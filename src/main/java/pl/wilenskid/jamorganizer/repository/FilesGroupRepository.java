package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.FilesGroup;

@Repository
public interface FilesGroupRepository extends CrudRepository<FilesGroup, Long> {
}
