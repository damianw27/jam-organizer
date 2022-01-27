package pl.wilenskid.jamorganizer.service;

import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.jamorganizer.entity.bean.EventBean;
import pl.wilenskid.jamorganizer.entity.bean.ProjectBean;
import pl.wilenskid.jamorganizer.entity.bean.SubmissionBean;
import pl.wilenskid.jamorganizer.entity.bean.SubmissionCreateBean;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.Submission;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.repository.SubmissionRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ProjectService projectService;
    private final EventService eventService;

    @Inject
    public SubmissionService(SubmissionRepository submissionRepository,
                             ProjectService projectService,
                             EventService eventService) {
        this.submissionRepository = submissionRepository;
        this.projectService = projectService;
        this.eventService = eventService;
    }

    public List<Submission> getAll() {
        return StreamSupport
            .stream(submissionRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<Submission> getAllByEvent(Event event) {
        return new ArrayList<>(submissionRepository.findAllByEvent(event));
    }

    public Optional<Submission> getById(Long id) {
        return submissionRepository.findById(id);
    }

    public Submission create(SubmissionCreateBean createSubmissionBean) {
        Event event = eventService.getById(createSubmissionBean.getEventId()).orElseThrow(NotFoundRestException::new);
        Project project = projectService.getById(createSubmissionBean.getProjectId()).orElseThrow(NotFoundRestException::new);

        Submission submission = new Submission();
        submission.setEvent(event);
        submission.setProject(project);
        submission.setGrades(new HashSet<>());

        project
            .getMembers()
            .forEach(applicationUser ->
                eventService
                    .addMemberToEvent(event.getId(), applicationUser.getId(), ApplicationUserEventRole.PARTICIPANT));

        submissionRepository.save(submission);
        return submission;
    }

    public Submission update(Object o) {
        return null;
    }

    public Optional<Submission> delete(Long id) {
        Optional<Submission> entity = submissionRepository.findById(id);

        entity.ifPresent(submission -> {
            submission
                .getProject()
                .getMembers()
                .forEach(applicationUser -> eventService.removeMemberFromEvent(submission.getEvent().getId(), applicationUser.getId()));

            submissionRepository.delete(submission);
        });

        return entity;
    }

    public SubmissionBean toBean(Submission model) {
        EventBean event = eventService
            .getById(model.getEvent().getId())
            .map(eventService::toBean)
            .orElseThrow(IllegalStateException::new);

        ProjectBean project = projectService
            .getById(model.getProject().getId())
            .map(projectService::toBean)
            .orElseThrow(IllegalStateException::new);

        List<Long> grades = model
            .getGrades()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        return SubmissionBean
            .builder()
            .id(model.getId())
            .event(event)
            .project(project)
            .grades(grades)
            .build();
    }

}
