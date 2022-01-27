package pl.wilenskid.jamorganizer.action.comment;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.CommentBean;
import pl.wilenskid.jamorganizer.entity.bean.ReportReasonBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.CommentService;
import pl.wilenskid.jamorganizer.service.ParameterParseService;
import pl.wilenskid.jamorganizer.service.ReportReasonService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/comment/report")
public class CommentReportAction extends BaseAction {

    private final CommentService commentService;
    private final ReportReasonService reportReasonService;
    private final ParameterParseService parameterParseService;

    public CommentBean comment;
    public List<ReportReasonBean> reportReasons;

    @Inject
    protected CommentReportAction(CommentService commentService,
                                  ReportReasonService reportReasonService,
                                  ParameterParseService parameterParseService) {
        super("comment/comment-report");
        this.commentService = commentService;
        this.reportReasonService = reportReasonService;
        this.parameterParseService = parameterParseService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        long commentId = parameterParseService.getLong("commentId", pathParams, -1);

        comment = commentService
            .getComment(commentId)
            .map(commentService::toBean)
            .orElseThrow(NotFoundRestException::new);

        reportReasons = reportReasonService
            .getAllReportReasons()
            .map(reportReasonService::toBean)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }

}
