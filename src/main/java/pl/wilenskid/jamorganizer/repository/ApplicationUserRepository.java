package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.User;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends CrudRepository<User, Long> {
    Optional<User> getByName(String name);
}
