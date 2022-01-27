package pl.wilenskid.jamorganizer.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.ReportReason;

@Repository
public interface ReportReasonRepository extends CrudRepository<ReportReason, Long> {
}
