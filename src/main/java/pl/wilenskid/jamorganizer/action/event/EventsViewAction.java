package pl.wilenskid.jamorganizer.action.event;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.EventBean;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.EventService;
import pl.wilenskid.jamorganizer.service.ParameterParseService;
import pl.wilenskid.jamorganizer.service.SecurityService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
@RequestMapping("/event/view-all")
public class EventsViewAction extends BaseAction {

    private static final Integer PAGE_SIZE = 10;

    private final EventService eventService;
    private final SecurityService securityService;
    private final ParameterParseService parameterParseService;

    public List<EventBean> events;
    public List<EventBean> myEvents;
    public List<Integer> eventsPageIndexes;
    public List<Integer> myEventsPageIndexes;

    @Inject
    public EventsViewAction(EventService eventService,
                            SecurityService securityService,
                            ParameterParseService parameterParseService) {
        super("event/event-view-all");
        this.eventService = eventService;
        this.securityService = securityService;
        this.parameterParseService = parameterParseService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        if (pathParams == null) {
            return;
        }

        User loggedInUser = securityService.getLoggedInUser();

        int myEventsPageIndex = parameterParseService.getInt("myEventsPageIndex", pathParams);

        if (myEventsPageIndex == 0) {
            pathParams.put("myEventsPageIndex", "0");
        }

        int eventsPageIndex = parameterParseService.getInt("eventsPageIndex", pathParams);

        if (eventsPageIndex == 0) {
            pathParams.put("eventsPageIndex", "0");
        }

        myEvents = eventService
            .getAll()
            .stream()
            .filter(event -> event.getMembers().stream().anyMatch(member -> Objects.equals(member.getUser().getId(), loggedInUser.getId()) && member.getApplicationUserEventRole() == ApplicationUserEventRole.ORGANIZER))
            .map(eventService::toBean)
            .skip((long) myEventsPageIndex * PAGE_SIZE)
            .limit(PAGE_SIZE)
            .collect(Collectors.toList());

        events = eventService
            .getAll()
            .stream()
            .skip((long) eventsPageIndex * PAGE_SIZE)
            .limit(PAGE_SIZE)
            .map(eventService::toBean)
            .collect(Collectors.toList());

        int eventsCount = eventService
            .getAll()
            .size();

        eventsPageIndexes = new ArrayList<>();

        for (int i = 0; i < Math.ceil(eventsCount / (double) PAGE_SIZE); i++) {
            eventsPageIndexes.add(i);
        }

        long myEventsCount = eventService
            .getAll()
            .stream()
            .filter(event -> event.getMembers().stream().anyMatch(member -> Objects.equals(member.getUser().getId(), loggedInUser.getId()) && member.getApplicationUserEventRole() == ApplicationUserEventRole.ORGANIZER))
            .count();

        myEventsPageIndexes = new ArrayList<>();

        for (int i = 0; i < Math.ceil(myEventsCount / (double) PAGE_SIZE); i++) {
            myEventsPageIndexes.add(i);
        }
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }

}
