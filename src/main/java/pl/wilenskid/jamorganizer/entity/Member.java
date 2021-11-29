package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "MEMBER")
public class Member extends AbstractPersistable<Long> {
    private ApplicationUserEventRole applicationUserEventRole;

    @ManyToOne
    @JoinColumn(name="EVENT_ID", nullable=false)
    private Event event;

    @ManyToOne
    @JoinColumn(name="APPLICATION_USER_ID", nullable=false)
    private ApplicationUser applicationUser;
}
