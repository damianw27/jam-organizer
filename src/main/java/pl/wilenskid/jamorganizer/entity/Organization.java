package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "ORGANIZATION")
public class Organization extends AbstractPersistable<Long> {
    private String name;
    private String country;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "ORGANIZATION_USER",
        joinColumns = { @JoinColumn(name = "ORGANIZATION_ID") },
        inverseJoinColumns = { @JoinColumn(name = "USER_ID") }
    )
    private Set<ApplicationUser> applicationUsers;
}
