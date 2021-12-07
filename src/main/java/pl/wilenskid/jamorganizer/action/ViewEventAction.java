package pl.wilenskid.jamorganizer.action;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.Member;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.Submission;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.repository.EventRepository;
import pl.wilenskid.jamorganizer.service.TimeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/event/view")
public class ViewEventAction extends BaseAction {
    private final EventRepository eventRepository;
    private final TimeService timeService;

    public Event event;
    public List<ApplicationUser> organizers;
    public List<ApplicationUser> judges;
    public List<Project> projects;

    @Inject
    public ViewEventAction(EventRepository eventRepository, TimeService timeService) {
        super("view-event.html");
        this.eventRepository = eventRepository;
        this.timeService = timeService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        String eventId = pathParams.get("eventId");
        event = eventRepository.findById(Long.valueOf(eventId))
            .orElseThrow(IllegalArgumentException::new);

        organizers = event.getMembers()
            .stream()
            .filter(member -> member.getApplicationUserEventRole() == ApplicationUserEventRole.ORGANIZER)
            .map(Member::getApplicationUser)
            .collect(Collectors.toList());

        judges = event.getMembers()
            .stream()
            .filter(member -> member.getApplicationUserEventRole() == ApplicationUserEventRole.JUDGE)
            .map(Member::getApplicationUser)
            .collect(Collectors.toList());

        projects = event.getSubmissions()
            .stream()
            .map(Submission::getProject)
            .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        return null;
    }
}