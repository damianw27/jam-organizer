package pl.dw.meetgamejam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dw.meetgamejam.entities.OrganizationEntity;

@Repository
public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {
}
