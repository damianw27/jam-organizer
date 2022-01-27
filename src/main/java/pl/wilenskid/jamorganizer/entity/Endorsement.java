package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity(name = "ENDORSEMENT")
public class Endorsement extends AbstractPersistable<Long> {
    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;
}
