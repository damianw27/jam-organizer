package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
}
