package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "REPORT_REASON")
public class ReportReason extends AbstractPersistable<Long> {
    private String label;
    private String description;
}
