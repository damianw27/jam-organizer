package pl.wilenskid.jamorganizer.service;

import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.jamorganizer.entity.bean.CriterionBean;
import pl.wilenskid.jamorganizer.entity.bean.CriterionCreateBean;
import pl.wilenskid.jamorganizer.entity.Criterion;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.repository.CriterionRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class CriterionService {

    private final CriterionRepository criterionRepository;
    private final EventService eventService;

    @Inject
    public CriterionService(CriterionRepository criterionRepository,
                            EventService eventService) {
        super();
        this.criterionRepository = criterionRepository;
        this.eventService = eventService;
    }

    public List<Criterion> getAll() {
        return StreamSupport.stream(criterionRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public Optional<Criterion> getById(Long id) {
        return criterionRepository.findById(id);
    }

    public List<Criterion> getCriteriaForEvent(Long id) {
        return StreamSupport
            .stream(criterionRepository.findAll().spliterator(), false)
            .filter(criterion -> criterion.getEvents().stream().anyMatch(event -> Objects.equals(event.getId(), id)))
            .collect(Collectors.toList());
    }

    public Criterion create(CriterionCreateBean createBean) {
        Event event = eventService.getById(createBean.getEventId()).orElseThrow(NotFoundRestException::new);

        Criterion criterion = new Criterion();
        criterion.setLabel(createBean.getLabel());
        criterion.setPriority(createBean.getPriority());
        criterion.setRequired(false);
        criterion.setGrades(new HashSet<>());
        criterion.setEvents(new HashSet<>());
        criterionRepository.save(criterion);

        Set<Criterion> criteria = event.getCriteria();
        criteria.add(criterion);
        event.setCriteria(criteria);
        eventService.getEventRepository().save(event);

        return criterion;
    }

    public Criterion update(Object updateBean) {
        return null;
    }

    public Optional<Criterion> delete(Long id) {
        Optional<Criterion> criterion = criterionRepository.findById(id);
        criterion.ifPresent(criterionRepository::delete);
        return criterion;
    }

    public CriterionBean toBean(Criterion model) {
        List<Long> events = model
            .getEvents()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        List<Long> grades = model
            .getGrades()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        return CriterionBean
            .builder()
            .id(model.getId())
            .label(model.getLabel())
            .priority(model.getPriority())
            .required(model.getRequired())
            .events(events)
            .grades(grades)
            .build();
    }

}
