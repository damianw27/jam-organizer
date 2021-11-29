package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "GRADE")
public class Grade extends AbstractPersistable<Long> {
    private Byte grade;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member judge;

    @ManyToOne
    @JoinColumn(name = "SUBMISSION_ID", nullable = false)
    private Submission submission;

    @ManyToOne
    @JoinColumn(name = "CRITERION_ID", nullable = false)
    private Criterion criterion;
}
