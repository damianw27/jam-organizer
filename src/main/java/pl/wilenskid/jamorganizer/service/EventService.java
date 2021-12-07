package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.bean.EventCreateBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.repository.EventRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class EventService implements CrudService<Event, EventCreateBean, Object, Long> {

    private final EventRepository eventRepository;
    private final TimeService timeService;

    @Inject
    public EventService(EventRepository eventRepository, TimeService timeService) {
        this.eventRepository = eventRepository;
        this.timeService = timeService;
    }

    @Override
    public Event create(EventCreateBean eventBean) {
        Event event = new Event();
        event.setName(eventBean.getName());
        event.setDescriptionLink(eventBean.getDescriptionLink());
        timeService.dateFromString(eventBean.getSubmissionsStart()).ifPresent(event::setSubmissionsStart);
        timeService.dateFromString(eventBean.getSubmissionsEnd()).ifPresent(event::setSubmissionsEnd);
        timeService.dateFromString(eventBean.getJudgementsStart()).ifPresent(event::setJudgementsStart);
        timeService.dateFromString(eventBean.getJudgementsEnd()).ifPresent(event::setJudgementsEnd);
        timeService.dateFromString(eventBean.getResultsDate()).ifPresent(event::setResultsDate);
        event.setSubmissions(new HashSet<>());
        event.setMembers(new HashSet<>());
        event.setCriteria(new HashSet<>());
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event update(Object o) {
        return null;
    }

    @Override
    public Optional<Event> delete(Long id) {
        Optional<Event> entity = eventRepository.findById(id);
        entity.ifPresent(eventRepository::delete);
        return entity;
    }

    @Override
    public List<Event> getAll() {
        return StreamSupport
            .stream(eventRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Event> getById(Long id) {
        return eventRepository.findById(id);
    }

}
