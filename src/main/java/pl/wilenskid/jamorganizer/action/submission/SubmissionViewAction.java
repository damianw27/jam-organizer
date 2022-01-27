package pl.wilenskid.jamorganizer.action.submission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.entity.Criterion;
import pl.wilenskid.jamorganizer.entity.Grade;
import pl.wilenskid.jamorganizer.entity.Submission;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.EventService;
import pl.wilenskid.jamorganizer.service.SecurityService;
import pl.wilenskid.jamorganizer.service.SubmissionService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/submission/view")
public class SubmissionViewAction extends BaseAction {
    private final SubmissionService submissionService;
    private final SecurityService securityService;
    private final EventService eventService;

    public Submission submission;
    public List<Criterion> criteria;
    public List<Grade> grades;
    public boolean isLoggedUserJudge;

    @Inject
    public SubmissionViewAction(SubmissionService submissionService,
                                SecurityService securityService,
                                EventService eventService) {
        super("submission/submission-view");
        this.submissionService = submissionService;
        this.securityService = securityService;
        this.eventService = eventService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        User loggedInUser = securityService.getLoggedInUser();

        submission = submissionService
            .getById(Long.valueOf(pathParams.get("submissionId")))
            .orElseThrow(NotFoundRestException::new);

        boolean isLoggedUserAuthor = submission
            .getProject()
            .getMembers()
            .stream()
            .anyMatch(applicationUser -> Objects.equals(applicationUser.getId(), loggedInUser.getId()));

        isLoggedUserJudge = eventService
            .hasLoggedInUserRoleInEvent(submission.getEvent(), ApplicationUserEventRole.JUDGE);

        boolean isLoggedUserOrganizer = eventService
            .hasLoggedInUserRoleInEvent(submission.getEvent(), ApplicationUserEventRole.ORGANIZER);

        if (!isLoggedUserAuthor && !isLoggedUserJudge && !isLoggedUserOrganizer) {
            response.sendRedirect("/event/view?eventId=" + submission.getEvent().getId());
        }

        criteria = new ArrayList<>(submission.getEvent().getCriteria());
        grades = new ArrayList<>(submission.getGrades());
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
