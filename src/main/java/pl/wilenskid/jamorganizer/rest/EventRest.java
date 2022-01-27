package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import pl.wilenskid.jamorganizer.builder.RedirectionPath;
import pl.wilenskid.jamorganizer.entity.bean.AddJudgeBean;
import pl.wilenskid.jamorganizer.entity.bean.AddOrganizerBean;
import pl.wilenskid.jamorganizer.entity.bean.EventCreateBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.bean.EventUpdateBean;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.exception.NotModifiedRestException;
import pl.wilenskid.jamorganizer.service.EventService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/event")
public class EventRest implements CrudRest<EventCreateBean, EventUpdateBean> {

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
    public String update(EventUpdateBean eventUpdateBean, HttpServletResponse response) throws HttpStatusCodeException {
        eventService.update(eventUpdateBean);
        return "redirect:/event/view?eventId=" + eventUpdateBean.getEventId();
    }

    @Override
    public String delete(Long id, HttpServletResponse response) throws NotModifiedRestException {
        Optional<Event> event = eventService.delete(id);

        if (event.isEmpty()) {
            throw new NotModifiedRestException();
        }

        return "redirect:/home";
    }

    @PostMapping("/organizer/{eventId}")
    public String addOrganizer(@PathVariable("eventId") Long eventId, AddOrganizerBean addAuthorBean) {
        eventService.addMemberToEvent(eventId, addAuthorBean.getOrganizerId(), ApplicationUserEventRole.ORGANIZER);
        return "redirect:/event/view?eventId=" + eventId + "&focusOrganizerId=true";
    }

    @DeleteMapping("/organizer/remove/{eventId}/{userId}")
    public String removeAuthor(@PathVariable("eventId") Long eventId,
                               @PathVariable("userId") Long userId) {
        eventService.removeMemberFromEvent(eventId, userId);
        return "redirect:/event/view?eventId=" + eventId + "&focusOrganizerId=true";
    }

    @PostMapping("/judge/{eventId}")
    public String addJudge(@PathVariable("eventId") Long eventId, AddJudgeBean addAuthorBean) {
        eventService.addMemberToEvent(eventId, addAuthorBean.getJudgeId(), ApplicationUserEventRole.JUDGE);
        return "redirect:/event/view?eventId=" + eventId + "&focusJudgeId=true";
    }

    @DeleteMapping("/judge/remove/{eventId}/{userId}")
    public String removeJudge(@PathVariable("eventId") Long eventId,
                              @PathVariable("userId") Long userId) {
        eventService.removeMemberFromEvent(eventId, userId);
        return "redirect:/event/view?eventId=" + eventId + "&focusJudgeId=true";
    }

    @PutMapping("/find-winners/{eventId}")
    public String findWinners(@PathVariable("eventId") Long eventId) {
        Event event = eventService
            .getById(eventId)
            .orElseThrow(NotFoundRestException::new);

        if (eventService.hasLoggedInUserRoleInEvent(event, ApplicationUserEventRole.ORGANIZER)) {
            eventService.findWinner(event);
        }

        return RedirectionPath
            .builder()
            .basePath("/event/view")
            .withParam("eventId", String.valueOf(event.getId()))
            .build();
    }

}

