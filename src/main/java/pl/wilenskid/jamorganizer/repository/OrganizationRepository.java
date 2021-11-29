package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
}
