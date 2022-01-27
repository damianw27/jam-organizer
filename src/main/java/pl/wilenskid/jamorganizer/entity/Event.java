package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "EVENT")
public class Event extends AbstractPersistable<Long> {
    private String name;
    private Long descriptionFileId;
    private Long logoFileId;
    private Calendar submissionsStart;
    private Calendar submissionsEnd;
    private Calendar judgementsStart;
    private Calendar judgementsEnd;
    private Calendar resultsDate;

    @OneToMany(mappedBy = "event")
    private Set<Member> members;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "EVENT_CRITERION",
        joinColumns = { @JoinColumn(name = "EVENT_ID") },
        inverseJoinColumns = { @JoinColumn(name = "CRITERION_ID") }
    )
    private Set<Criterion> criteria;

    @OneToMany(mappedBy = "event")
    private Set<Submission> submissions;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "EVENT_PROJECT",
        joinColumns = { @JoinColumn(name = "EVENT_ID") },
        inverseJoinColumns = { @JoinColumn(name = "PROJECT_ID") }
    )
    private Set<Project> winners;
}
