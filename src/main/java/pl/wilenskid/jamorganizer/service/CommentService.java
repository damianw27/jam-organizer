package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.entity.bean.CommentBean;
import pl.wilenskid.jamorganizer.entity.bean.CommentCreateBean;
import pl.wilenskid.jamorganizer.entity.bean.CommentUpdateBean;
import pl.wilenskid.jamorganizer.entity.Comment;
import pl.wilenskid.jamorganizer.entity.File;
import pl.wilenskid.jamorganizer.enums.ReferenceType;
import pl.wilenskid.jamorganizer.repository.CommentRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Stream;

@Named
public class CommentService {

    private final CommentRepository commentRepository;
    private final FileService fileService;
    private final SecurityService securityService;
    private final TimeService timeService;
    private final UserService userService;

    @Inject
    public CommentService(CommentRepository commentRepository,
                          FileService fileService,
                          SecurityService securityService,
                          TimeService timeService,
                          UserService userService) {
        this.commentRepository = commentRepository;
        this.fileService = fileService;
        this.securityService = securityService;
        this.timeService = timeService;
        this.userService = userService;
    }

    public Optional<Comment> getComment(long id) {
        return commentRepository.findById(id);
    }

    public Stream<CommentBean> getCommentsForProject(long projectId) {
        return commentRepository
            .findAllByReferenceIdAndReferenceType(projectId, ReferenceType.PROJECT)
            .stream()
            .sorted((o1, o2) -> Math.toIntExact(o2.getCreated().getTimeInMillis() - o1.getCreated().getTimeInMillis()))
            .map(this::toBean);
    }

    public Stream<CommentBean> getCommentsForEvent(long eventId) {
        return commentRepository
            .findAllByReferenceIdAndReferenceType(eventId, ReferenceType.EVENT)
            .stream()
            .map(this::toBean);
    }

    public Comment create(CommentCreateBean commentCreateBean) {
        if (commentCreateBean.getCommentContent().isEmpty()) {
            return null;
        }

        Calendar created = Calendar.getInstance();
        String referenceName = commentCreateBean.getReferenceType().name();
        Long referenceId = commentCreateBean.getReferenceId();
        String filename = String.format("COMMENT-%s-%s", referenceName, referenceId);

        File file = fileService
            .uploadFile(filename, commentCreateBean.getCommentContent())
            .orElseThrow(IllegalStateException::new);

        Comment comment = new Comment();
        comment.setReferenceId(commentCreateBean.getReferenceId());
        comment.setReferenceType(commentCreateBean.getReferenceType());
        comment.setCreated(created);
        comment.setCommentFileId(file.getId());
        comment.setAuthor(securityService.getLoggedInUser());

        commentRepository.save(comment);

        return comment;
    }

    public Comment update(CommentUpdateBean commentUpdateBean) {
        Comment comment = commentRepository
            .findById(commentUpdateBean.getCommentId())
            .orElseThrow(IllegalArgumentException::new);

        fileService.updateFileContent(comment.getCommentFileId(), commentUpdateBean.getCommentContent());

        return comment;
    }

    public Optional<Comment> delete(long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
        return comment;
    }

    public CommentBean toBean(Comment comment) {
        File file = fileService
            .findFileById(comment.getCommentFileId())
            .orElseThrow(IllegalStateException::new);

        String createdDateTimeString = timeService
            .dateToString(comment.getCreated(), true)
            .orElseThrow(IllegalStateException::new);

        return CommentBean
            .builder()
            .id(comment.getId())
            .referenceId(comment.getReferenceId())
            .referenceType(comment.getReferenceType())
            .commentContent(fileService.toBeanWithContent(file))
            .created(createdDateTimeString)
            .author(userService.toBean(comment.getAuthor()))
            .build();
    }

}
