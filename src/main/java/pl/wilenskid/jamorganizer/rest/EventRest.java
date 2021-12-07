package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import pl.wilenskid.jamorganizer.bean.EventCreateBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.exception.NotModifiedRestException;
import pl.wilenskid.jamorganizer.service.EventService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/event")
public class EventRest implements CrudRest<EventCreateBean, Object> {

    public final EventService eventService;

    @Inject
    public EventRest(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String create(EventCreateBean eventBean, HttpServletResponse response) throws HttpStatusCodeException {
        Event event = eventService.create(eventBean);
        return "redirect:/event/view?eventId=" + event.getId();
    }

    @Override
    public String update(Object o, HttpServletResponse response) throws HttpStatusCodeException {
        return "redirect:/home";
    }

    @Override
    public String delete(Long id, HttpServletResponse response) throws NotModifiedRestException {
        Optional<Event> event = eventService.delete(id);

        if (event.isEmpty()) {
            throw new NotModifiedRestException();
        }

        return "redirect:/home";
    }

}

