package pl.dw.organizer.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = ProjectEntity.NAME)
public class ProjectEntity {
    public static final String NAME = "PROJECTS";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String descriptionLink;

    private String logoLink;

    private String videoLink;

    private String downloadLink;

    @ManyToMany(mappedBy = "projects")
    private Set<MemberEntity> coworkers;
}
