package pl.wilenskid.jamorganizer.action.submission;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.EventService;
import pl.wilenskid.jamorganizer.service.ProjectService;
import pl.wilenskid.jamorganizer.service.SecurityService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
@RequestMapping("/submission/create")
public class SubmissionCreateView extends BaseAction {
    private final ProjectService projectService;
    private final EventService eventService;
    private final SecurityService securityService;

    public List<Project> projects;
    public List<Event> events;

    @Inject
    public SubmissionCreateView(ProjectService projectService,
                                EventService eventService,
                                SecurityService securityService) {
        super("submission/submission-create");
        this.projectService = projectService;
        this.eventService = eventService;
        this.securityService = securityService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        User loggedInUser = securityService.getLoggedInUser();

        projects = projectService
            .getAll()
            .stream()
            .filter(project ->
                project
                    .getMembers()
                    .stream()
                    .anyMatch(applicationUser -> Objects.equals(applicationUser.getId(), loggedInUser.getId()))
            )
            .collect(Collectors.toList());

        events = eventService.getAll();
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
