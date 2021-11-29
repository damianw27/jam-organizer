package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "APP_USER")
public class ApplicationUser extends AbstractPersistable<Long> {
    private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String password;
    private ApplicationUserRole applicationUserRole;
    private Calendar jobStart;
    private Calendar created;
    private Calendar suspendExpiration;

    @OneToMany(mappedBy = "event")
    private Set<Member> membership;

    @ManyToMany(mappedBy = "applicationUsers")
    private Set<Organization> organizations;

    @ManyToMany(mappedBy = "members")
    private Set<Project> projects;
}
