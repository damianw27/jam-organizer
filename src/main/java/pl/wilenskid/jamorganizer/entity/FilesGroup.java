package pl.wilenskid.jamorganizer.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "FILES_GROUP")
public class FilesGroup extends AbstractPersistable<Long> {
    private String baseFilename;

    @OneToMany
    private Set<File> files;
}
