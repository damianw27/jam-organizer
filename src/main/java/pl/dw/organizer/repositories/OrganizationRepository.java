package pl.dw.organizer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dw.organizer.entities.OrganizationEntity;

@Repository
public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {
}
