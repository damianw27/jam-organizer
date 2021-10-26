package pl.dw.organizer.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Data
@Entity(name = "EVENTS")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String descriptionLink;

    private Calendar submissionsStart;

    private Calendar submissionsEnd;

    private Calendar judgementsStart;

    private Calendar judgementsEnd;

    private Calendar resultsDate;

    @OneToMany
    private Set<MemberEntity> members;

    @ManyToMany
    @JoinTable(
        name = "EVENTS_SUBMISSIONS",
        joinColumns = @JoinColumn(name = "EVENTS_ID"),
        inverseJoinColumns = @JoinColumn(name = "SUBMISSIONS_ID")
    )
    private Set<SubmissionEntity> submissions;

    @ManyToMany
    @JoinTable(
        name = "EVENTS_CRITERIA",
        joinColumns = @JoinColumn(name = "EVENTS_ID"),
        inverseJoinColumns = @JoinColumn(name = "CRITERIA_ID")
    )
    private Set<CriterionEntity> criteria;
}
