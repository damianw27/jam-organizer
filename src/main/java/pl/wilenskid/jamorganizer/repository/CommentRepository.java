package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.jamorganizer.entity.Comment;
import pl.wilenskid.jamorganizer.enums.ReferenceType;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByReferenceIdAndReferenceType(long referenceId, ReferenceType referenceType);

}
