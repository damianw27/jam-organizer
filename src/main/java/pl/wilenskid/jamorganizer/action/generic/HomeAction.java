package pl.wilenskid.jamorganizer.action.generic;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.EventBean;
import pl.wilenskid.jamorganizer.entity.bean.ProjectBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.EventService;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/")
public class HomeAction extends BaseAction {

    private final EventService eventService;
    private final ProjectService projectService;

    public List<EventBean> events;
    public List<ProjectBean> projects;

    @Inject
    public HomeAction(EventService eventService,
                      ProjectService projectService) {
        super("generic/home-page");
        this.eventService = eventService;
        this.projectService = projectService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        Calendar now = Calendar.getInstance();

        events = eventService
            .getAll()
            .stream()
            .filter(event -> event.getSubmissionsStart().before(now))
            .sorted(this::getEventComparator)
            .map(eventService::toBean)
            .limit(10)
            .collect(Collectors.toList());

        projects = projectService
            .getAll()
            .stream()
            .sorted((o1, o2) -> o2.getEndorsements().size() - o1.getEndorsements().size())
            .limit(10)
            .map(projectService::toBean)
            .collect(Collectors.toList());
    }

    private int getEventComparator(Event current, Event next) {
        if (current.getSubmissionsStart().before(next.getSubmissionsStart())) {
            return -1;
        } else if (current.getSubmissionsStart().after(next.getSubmissionsStart())) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }

}
