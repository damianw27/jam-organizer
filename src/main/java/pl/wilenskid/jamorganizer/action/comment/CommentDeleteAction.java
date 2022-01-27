package pl.wilenskid.jamorganizer.action.comment;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.CommentBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.CommentService;
import pl.wilenskid.jamorganizer.service.ParameterParseService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/comment/delete")
public class CommentDeleteAction extends BaseAction {

    private final CommentService commentService;
    private final ParameterParseService parameterParseService;

    public CommentBean comment;

    @Inject
    protected CommentDeleteAction(CommentService commentService, ParameterParseService parameterParseService) {
        super("comment/comment-delete");
        this.commentService = commentService;
        this.parameterParseService = parameterParseService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        long commentId = parameterParseService.getLong("commentId", pathParams, -1);
        comment = commentService
            .getComment(commentId)
            .map(commentService::toBean)
            .orElseThrow(NotFoundRestException::new);
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
