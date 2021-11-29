package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "CRITERION")
public class Criterion extends AbstractPersistable<Long> {
    private String label;
    private Boolean required;
    private Byte priority;

    @ManyToMany(mappedBy = "criteria")
    private Set<Event> events;

    @OneToMany(mappedBy = "criterion")
    private Set<Grade> grades;
}

