package pl.dw.organizer.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "GRADES")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Byte grade;

    @ManyToOne
    private MemberEntity memberEntity;

    @ManyToOne
    private SubmissionEntity submission;

    @ManyToOne
    private CriterionEntity criterion;
}
