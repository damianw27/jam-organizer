package pl.wilenskid.jamorganizer.action;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.service.EventService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/home")
public class HomeAction extends BaseAction {
    private final EventService eventService;

    public List<Event> events;

    @Inject
    public HomeAction(EventService eventService) {
        super("home.html");
        this.eventService = eventService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        events = eventService.getAll();
    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        return null;
    }
}
