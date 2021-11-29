package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "SUBMISSION")
public class Submission extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(name="EVENT_ID", nullable=false)
    private Event event;

    @ManyToOne
    @JoinColumn(name="PROJECT_ID", nullable=false)
    private Project project;

    @OneToMany(mappedBy = "submission")
    private Set<Grade> grades;
}
