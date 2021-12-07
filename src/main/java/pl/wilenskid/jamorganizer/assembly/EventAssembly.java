package pl.wilenskid.jamorganizer.assembly;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.EventBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.service.EventService;
import pl.wilenskid.jamorganizer.service.TimeService;

@Named
public class EventAssembly implements Assembly<Event, EventBean> {

    private final EventService eventService;
    private final TimeService timeService;
    
    @Inject
    public EventAssembly(EventService eventService, TimeService timeService) {
        this.eventService = eventService;
        this.timeService = timeService;
    }

    @Override
    public EventBean toBean(Event model) {
        List<Long> members = StreamSupport.stream(model.getMembers().spliterator(), false)
                .map(memeber -> memeber.getId())
                .collect(Collectors.toList());
        
        List<Long> criteria = StreamSupport.stream(model.getCriteria().spliterator(), false)
                .map(criterion -> criterion.getId())
                .collect(Collectors.toList());
        
        List<Long> submissions = StreamSupport.stream(model.getSubmissions().spliterator(), false)
                .map(submission -> submission.getId())
                .collect(Collectors.toList());
        
        return EventBean
            .builder()
            .id(model.getId())
            .name(model.getName())
            .descriptionLink(model.getDescriptionLink())
            .submissionsStart(timeService.dateToString(model.getSubmissionsStart(), false).orElse("n/a"))
            .submissionsEnd(timeService.dateToString(model.getSubmissionsEnd(), false).orElse("n/a"))
            .judgementsStart(timeService.dateToString(model.getJudgementsStart(), false).orElse("n/a"))
            .judgementsEnd(timeService.dateToString(model.getJudgementsEnd(), false).orElse("n/a"))
            .resultsDate(timeService.dateToString(model.getResultsDate(), false).orElse("n/a"))
            .members(members)
            .criteria(criteria)
            .submissions(submissions)
            .build();
    }

    @Override
    public Event toModel(EventBean bean) {
        return eventService.getById(bean.getId()).orElse(null);
    }

}
