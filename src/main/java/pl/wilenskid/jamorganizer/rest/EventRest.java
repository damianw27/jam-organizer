package pl.wilenskid.jamorganizer.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wilenskid.jamorganizer.bean.CreateEventBean;
import pl.wilenskid.jamorganizer.service.EventService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@RestController
public class EventRest extends BaseRest {
    public final EventService eventService;

    @Inject
    public EventRest(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create-event")
    public void createEvent(CreateEventBean eventBean, HttpServletResponse response) {
        eventService.createEvent(eventBean);
        redirectTo(response, "/home");
    }
}
