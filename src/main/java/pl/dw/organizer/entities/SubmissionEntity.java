package pl.dw.organizer.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "SUBMISSIONS")
public class SubmissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ProjectEntity project;

    @OneToMany
    private Set<GradeEntity> grades;

    @ManyToMany(mappedBy = "submissions")
    private Set<EventEntity> events;
}
