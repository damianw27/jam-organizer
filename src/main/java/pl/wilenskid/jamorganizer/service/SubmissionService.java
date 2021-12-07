package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.bean.SubmissionCreateBean;
import pl.wilenskid.jamorganizer.entity.Submission;
import pl.wilenskid.jamorganizer.repository.SubmissionRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class SubmissionService implements CrudService<Submission, SubmissionCreateBean, Object, Long> {

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

    @Override
    public List<Submission> getAll() {
        return StreamSupport
            .stream(submissionRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Submission> getById(Long id) {
        return submissionRepository.findById(id);
    }

    @Override
    public Submission create(SubmissionCreateBean createSubmissionBean) {
        Submission submission = new Submission();
        eventService.getById(createSubmissionBean.getEventId()).ifPresent(submission::setEvent);
        projectService.getById(createSubmissionBean.getProjectId()).ifPresent(submission::setProject);
        submission.setGrades(new HashSet<>());

        if (submission.getProject() == null || submission.getEvent() == null) {
            return null;
        }

        submissionRepository.save(submission);
        return submission;
    }

    @Override
    public Submission update(Object o) {
        return null;
    }

    @Override
    public Optional<Submission> delete(Long id) {
        Optional<Submission> entity = submissionRepository.findById(id);
        entity.ifPresent(submissionRepository::delete);
        return entity;
    }

}
