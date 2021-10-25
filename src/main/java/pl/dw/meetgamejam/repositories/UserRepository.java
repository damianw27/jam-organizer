package pl.dw.meetgamejam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dw.meetgamejam.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
