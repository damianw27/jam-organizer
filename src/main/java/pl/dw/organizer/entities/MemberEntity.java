package pl.dw.organizer.entities;

import lombok.Data;
import pl.dw.organizer.enums.EventRole;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "MEMBERS")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private EventRole eventRole;

    @ManyToOne
    private EventEntity event;

    @ManyToOne
    private UserEntity user;

    @OneToMany
    private Set<GradeEntity> grades;

    @ManyToMany
    @JoinTable(
        name = "MEMBERS_PROJECTS",
        joinColumns = @JoinColumn(name = "MEMBER_ID"),
        inverseJoinColumns = @JoinColumn(name = "PROJECT_ID")
    )
    private Set<ProjectEntity> projects;
}
