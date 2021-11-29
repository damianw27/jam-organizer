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
import java.util.Set;

@Getter
@Setter
@Entity(name = "PROJECT")
public class Project extends AbstractPersistable<Long> {
    private String title;
    private String descriptionLink;
    private String logoLink;
    private String videoLink;
    private String downloadLink;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "PROJECT_APP_USER",
        joinColumns = { @JoinColumn(name = "PROJECT_ID") },
        inverseJoinColumns = { @JoinColumn(name = "USER_ID") }
    )
    private Set<ApplicationUser> members;

    @OneToMany(mappedBy = "project")
    private Set<Submission> submissions;
}
