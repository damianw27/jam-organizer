package pl.wilenskid.jamorganizer.action;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.service.EventService;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/submission/create")
public class CreateSubmissionView extends BaseAction {
    private final ProjectService projectService;
    private final EventService eventService;

    public List<Project> projects;
    public List<Event> events;

    @Inject
    public CreateSubmissionView(ProjectService projectService, EventService eventService) {
        super("create-submission");
        this.projectService = projectService;
        this.eventService = eventService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        projects = projectService.getAll();
        events = eventService.getAll();
    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        return null;
    }
}
