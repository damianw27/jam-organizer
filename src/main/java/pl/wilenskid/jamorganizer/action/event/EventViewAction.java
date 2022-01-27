package pl.wilenskid.jamorganizer.action.event;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.*;
import pl.wilenskid.jamorganizer.entity.*;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/event/view")
public class EventViewAction extends BaseAction {

    private final EventService eventService;
    private final UserService userService;
    private final CriterionService criterionService;
    private final ParameterParseService parameterParseService;
    private final ProjectService projectService;
    private final SubmissionService submissionService;

    public EventBean event;
    public List<UserBean> organizers;
    public List<UserBean> judges;
    public List<ProjectBean> projects;
    public List<ProjectBean> winners;
    public List<SubmissionBean> submissions;
    public List<UserBean> allUsers;
    public List<CriterionBean> criteria;
    public boolean isLoggedUserOrganizer;

    @Inject
    public EventViewAction(EventService eventService,
                           UserService userService,
                           CriterionService criterionService,
                           ParameterParseService parameterParseService,
                           ProjectService projectService,
                           SubmissionService submissionService) {
        super("event/event-view.html");
        this.eventService = eventService;
        this.userService = userService;
        this.criterionService = criterionService;
        this.parameterParseService = parameterParseService;
        this.projectService = projectService;
        this.submissionService = submissionService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        long eventId = parameterParseService.getLong("eventId", pathParams, -1);

        Event eventModel = eventService
            .getById(eventId)
            .orElseThrow(IllegalArgumentException::new);

        event = eventService.toBean(eventModel);

        if (event == null) {
            throw new IllegalStateException();
        }

        organizers = eventModel.getMembers()
            .stream()
            .filter(member -> member.getApplicationUserEventRole() == ApplicationUserEventRole.ORGANIZER)
            .map(Member::getUser)
            .map(userService::toBean)
            .collect(Collectors.toList());

        judges = eventModel.getMembers()
            .stream()
            .filter(member -> member.getApplicationUserEventRole() == ApplicationUserEventRole.JUDGE)
            .map(Member::getUser)
            .map(userService::toBean)
            .collect(Collectors.toList());

        projects = eventModel.getSubmissions()
            .stream()
            .map(Submission::getProject)
            .map(projectService::toBean)
            .collect(Collectors.toList());

        allUsers = userService
            .getAll()
            .stream()
            .map(userService::toBean)
            .collect(Collectors.toList());

        isLoggedUserOrganizer = eventService
            .hasLoggedInUserRoleInEvent(eventModel, ApplicationUserEventRole.ORGANIZER);

        criteria = criterionService
            .getCriteriaForEvent(event.getId())
            .stream()
            .map(criterionService::toBean)
            .collect(Collectors.toList());

        submissions = eventModel
            .getSubmissions()
            .stream()
            .map(submissionService::toBean)
            .collect(Collectors.toList());

        winners = eventModel
            .getWinners()
            .stream()
            .map(projectService::toBean)
            .collect(Collectors.toList());

        if (winners == null) {
            winners = new ArrayList<>();
        }
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}