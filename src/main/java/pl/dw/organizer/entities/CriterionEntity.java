package pl.dw.organizer.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "CRITERIA")
public class CriterionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String label;

    private Boolean required;

    private Byte priority;

    @ManyToMany(mappedBy = "criteria")
    private Set<EventEntity> events;

    @OneToMany
    private Set<GradeEntity> grades;
}

