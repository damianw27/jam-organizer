package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.jamorganizer.enums.ReferenceType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Calendar;

@Getter
@Setter
@Entity(name = "COMMENT")
public class Comment extends AbstractPersistable<Long> {
    private Long referenceId;
    private ReferenceType referenceType;
    private Long commentFileId;
    private Calendar created;

    @ManyToOne
    private User author;
}
