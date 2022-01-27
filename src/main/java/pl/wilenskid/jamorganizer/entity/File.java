package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "FILE")
public class File extends AbstractPersistable<Long> {
    private String filename;
    private String fileUrl;

    @ManyToOne
    private User author;

    @ManyToOne
    private FilesGroup filesGroup;
}
