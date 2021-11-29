package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.bean.CreateEventBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.repository.EventRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class EventService {
    private final EventRepository eventRepository;
    private final TimeService timeService;

    @Inject
    public EventService(EventRepository eventRepository, TimeService timeService) {
        this.eventRepository = eventRepository;
        this.timeService = timeService;
    }

    public Event createEvent(CreateEventBean eventBean) {
        Event event = new Event();
        event.setName(eventBean.getName());
        event.setDescriptionLink(eventBean.getDescriptionLink());

        timeService
            .fromString(eventBean.getSubmissionsStart())
            .ifPresent(event::setSubmissionsStart);

        timeService
            .fromString(eventBean.getSubmissionsEnd())
            .ifPresent(event::setSubmissionsEnd);

        timeService
            .fromString(eventBean.getJudgementsStart())
            .ifPresent(event::setJudgementsStart);

        timeService
            .fromString(eventBean.getJudgementsEnd())
            .ifPresent(event::setJudgementsEnd);

        timeService
            .fromString(eventBean.getResultsDate())
            .ifPresent(event::setResultsDate);

        event.setSubmissions(new HashSet<>());
        event.setMembers(new HashSet<>());
        event.setCriteria(new HashSet<>());
        eventRepository.save(event);
        return event;
    }

    public List<Event> getAllEvents() {
        return StreamSupport
            .stream(eventRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
