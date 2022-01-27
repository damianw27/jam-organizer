package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wilenskid.jamorganizer.entity.bean.CommentCreateBean;
import pl.wilenskid.jamorganizer.entity.bean.CommentUpdateBean;
import pl.wilenskid.jamorganizer.builder.RedirectionPath;
import pl.wilenskid.jamorganizer.entity.Comment;
import pl.wilenskid.jamorganizer.enums.ReferenceType;
import pl.wilenskid.jamorganizer.service.CommentService;

import javax.inject.Inject;
import java.util.Optional;

@Controller
@RequestMapping("/comment")
public class CommentRest {

    private final CommentService commentService;

    @Inject
    public CommentRest(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public String createComment(CommentCreateBean commentCreateBean) {
        String redirectionBasePath = getRedirectionBasePath(commentCreateBean.getReferenceType());
        String idParamName = getIdParamName(commentCreateBean.getReferenceType());

        Comment comment = commentService.create(commentCreateBean);

        if (comment == null) {
            return RedirectionPath
                .builder()
                .basePath(redirectionBasePath)
                .withParam(idParamName, String.valueOf(commentCreateBean.getReferenceId()))
                .build();
        }

        return RedirectionPath
            .builder()
            .basePath(redirectionBasePath)
            .withParam(idParamName, String.valueOf(comment.getReferenceId()))
            .withParam("selectedCommentId", String.valueOf(comment.getId()))
            .build();
    }

    @PutMapping
    public String updateComment(CommentUpdateBean commentUpdateBean) {
        Comment updatedComment = commentService.update(commentUpdateBean);
        String redirectionBasePath = getRedirectionBasePath(updatedComment.getReferenceType());
        String idParamName = getIdParamName(updatedComment.getReferenceType());

        return RedirectionPath
            .builder()
            .basePath(redirectionBasePath)
            .withParam(idParamName, String.valueOf(updatedComment.getReferenceId()))
            .withParam("selectedCommentId", String.valueOf(updatedComment.getId()))
            .build();
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        Optional<Comment> comment = commentService.delete(commentId);

        if (comment.isEmpty()) {
            return RedirectionPath
                .builder()
                .basePath("/project/view-all")
                .build();
        }

        Comment comment1 = comment.get();
        String redirectionBasePath = getRedirectionBasePath(comment1.getReferenceType());
        String idParamName = getIdParamName(comment1.getReferenceType());

        return RedirectionPath
            .builder()
            .basePath(redirectionBasePath)
            .withParam(idParamName, String.valueOf(comment1.getReferenceId()))
            .withParam("selectedCommentId", String.valueOf(comment1.getId()))
            .build();

    }

    private String getRedirectionBasePath(ReferenceType referenceType) {
        if (referenceType == ReferenceType.PROJECT) {
            return "/project/view";
        }

        if (referenceType == ReferenceType.EVENT) {
            return "/project/event";
        }

        return "/";
    }

    private String getIdParamName(ReferenceType referenceType) {
        if (referenceType == ReferenceType.PROJECT) {
            return "projectId";
        }

        if (referenceType == ReferenceType.EVENT) {
            return "eventId";
        }

        return "/";
    }

}
