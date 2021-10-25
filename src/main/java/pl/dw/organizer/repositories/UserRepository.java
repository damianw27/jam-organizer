package pl.dw.organizer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dw.organizer.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
