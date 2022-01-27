package pl.wilenskid.jamorganizer.service;

import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.jamorganizer.entity.*;
import pl.wilenskid.jamorganizer.entity.bean.*;
import pl.wilenskid.jamorganizer.enums.ApplicationUserEventRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.repository.EventRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class EventService {

    private final EventRepository eventRepository;
    private final TimeService timeService;
    private final FileService fileService;
    private final MemberService memberService;
    private final SecurityService securityService;

    @Inject
    public EventService(EventRepository eventRepository,
                        TimeService timeService,
                        FileService fileService,
                        MemberService memberService,
                        SecurityService securityService) {
        this.eventRepository = eventRepository;
        this.timeService = timeService;
        this.fileService = fileService;
        this.memberService = memberService;
        this.securityService = securityService;
    }

    public Event create(EventCreateBean eventBean) {
        String parsedFileName = eventBean.getName().toLowerCase(Locale.ROOT).replace(' ', '-');
        String descFileName = parsedFileName + "-desc";
        String logoFileName = parsedFileName + "-avatar";

        Event event = new Event();
        event.setName(eventBean.getName());

        fileService
            .uploadFile(descFileName, eventBean.getDescription())
            .ifPresent(file -> event.setDescriptionFileId(file.getId()));

        fileService
            .uploadFile(logoFileName, eventBean.getLogo())
            .ifPresent(file -> event.setLogoFileId(file.getId()));

        timeService.dateFromString(eventBean.getSubmissionsStart()).ifPresent(event::setSubmissionsStart);
        timeService.dateFromString(eventBean.getSubmissionsEnd()).ifPresent(event::setSubmissionsEnd);
        timeService.dateFromString(eventBean.getJudgementsStart()).ifPresent(event::setJudgementsStart);
        timeService.dateFromString(eventBean.getJudgementsEnd()).ifPresent(event::setJudgementsEnd);
        timeService.dateFromString(eventBean.getResultsDate()).ifPresent(event::setResultsDate);
        event.setSubmissions(new HashSet<>());
        event.setMembers(new HashSet<>());
        event.setCriteria(new HashSet<>());
        eventRepository.save(event);

        User loggedInUser = securityService.getLoggedInUser();

        MemberCreateBean memberCreateBean = MemberCreateBean
            .builder()
            .eventId(event.getId())
            .applicationUserId(loggedInUser.getId())
            .applicationUserEventRole(ApplicationUserEventRole.ORGANIZER)
            .build();

        memberService.create(memberCreateBean);

        return event;
    }

    public Event update(EventUpdateBean eventUpdateBean) {
        Event event = eventRepository
            .findById(eventUpdateBean.getEventId())
            .orElseThrow(NotFoundRestException::new);

        event.setName(eventUpdateBean.getName());
        fileService.updateFileContent(event.getDescriptionFileId(), eventUpdateBean.getDescription());
        timeService.dateFromString(eventUpdateBean.getSubmissionsStart()).ifPresent(event::setSubmissionsStart);
        timeService.dateFromString(eventUpdateBean.getSubmissionsEnd()).ifPresent(event::setSubmissionsEnd);
        timeService.dateFromString(eventUpdateBean.getJudgementsStart()).ifPresent(event::setJudgementsStart);
        timeService.dateFromString(eventUpdateBean.getJudgementsEnd()).ifPresent(event::setJudgementsEnd);
        timeService.dateFromString(eventUpdateBean.getResultsDate()).ifPresent(event::setResultsDate);

        eventRepository.save(event);

        return null;
    }

    public Optional<Event> delete(Long id) {
        Optional<Event> entity = eventRepository.findById(id);
        entity.ifPresent(eventRepository::delete);
        return entity;
    }

    public List<Event> getAll() {
        return StreamSupport
            .stream(eventRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public Optional<Event> getById(Long id) {
        return eventRepository.findById(id);
    }

    public void addMemberToEvent(Long eventId, Long userId, ApplicationUserEventRole eventRole) {
        Boolean isMemberAddedAlready = getById(eventId)
            .map(event ->
                event
                    .getMembers()
                    .stream()
                    .filter(member -> member.getApplicationUserEventRole() == eventRole && Objects.equals(member.getUser().getId(), userId))
                    .count()
            )
            .map(count -> count > 0)
            .orElseThrow(NotFoundRestException::new);

        if (isMemberAddedAlready) {
            return;
        }

        MemberCreateBean memberCreateBean = MemberCreateBean
            .builder()
            .eventId(eventId)
            .applicationUserId(userId)
            .applicationUserEventRole(eventRole)
            .build();

        memberService.create(memberCreateBean);
    }

    public void removeMemberFromEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundRestException::new);

        long organizersCount = event
            .getMembers()
            .stream()
            .filter(member -> member.getApplicationUserEventRole() == ApplicationUserEventRole.ORGANIZER)
            .count();

        event
            .getMembers()
            .stream()
            .filter(member -> Objects.equals(member.getUser().getId(), userId))
            .findFirst()
            .ifPresent(member -> {
                if (member.getApplicationUserEventRole() == ApplicationUserEventRole.ORGANIZER && organizersCount == 1) {
                    return;
                }

                memberService.delete(member.getId());
            });
    }

    public void findWinner(Event event) {
        Set<Project> winners = event
            .getSubmissions()
            .stream()
            .map(this::getSubmissionAndGradeBean)
            .sorted((o1, o2) -> (int) (o2.getMeanGrade() - o1.getMeanGrade()))
            .map(SubmissionWithMeanGradeBean::getSubmission)
            .map(Submission::getProject)
            .limit(1)
            .collect(Collectors.toSet());

        event.setWinners(winners);

        eventRepository.save(event);
    }

    public boolean hasLoggedInUserRoleInEvent(Long eventId,
                                              ApplicationUserEventRole eventRole) {
        return hasUserRoleInEvent(eventId, eventRole, securityService.getLoggedInUser());
    }

    public boolean hasLoggedInUserRoleInEvent(Event event,
                                              ApplicationUserEventRole eventRole) {
        return hasUserRoleInEvent(event, eventRole, securityService.getLoggedInUser());
    }

    public boolean hasUserRoleInEvent(Long eventId,
                                      ApplicationUserEventRole eventRole,
                                      User user) {
        return getById(eventId)
            .map(event -> hasUserRoleInEvent(event, eventRole, user))
            .orElse(false);
    }

    public boolean hasUserRoleInEvent(Event event,
                                      ApplicationUserEventRole eventRole,
                                      User user) {
        return event
            .getMembers()
            .stream()
            .filter(member -> member.getApplicationUserEventRole() == eventRole)
            .map(Member::getUser)
            .filter(currentUser -> Objects.equals(currentUser.getId(), user.getId()))
            .count() == 1;
    }

    public EventRepository getEventRepository() {
        return eventRepository;
    }

    public EventBean toBean(Event model) {
        if (model == null) {
            return null;
        }

        List<Long> members = model
            .getMembers()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        List<Long> criteria = model
            .getCriteria()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        List<Long> submissions = model
            .getSubmissions()
            .stream()
            .map(AbstractPersistable::getId)
            .collect(Collectors.toList());

        String descriptionContent = fileService
            .findFileById(model.getDescriptionFileId())
            .map(File::getFilename)
            .map(fileService::loadFileAsString)
            .orElse("");

        String logoUrl = model.getLogoFileId() == null ? "" : null;

        if (logoUrl == null) {
            logoUrl = fileService
                .findFileById(model.getLogoFileId())
                .map(File::getFileUrl)
                .orElse("");
        }

        return EventBean
            .builder()
            .id(model.getId())
            .name(model.getName())
            .descriptionContent(descriptionContent)
            .logoLink(logoUrl)
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

    private SubmissionWithMeanGradeBean getSubmissionAndGradeBean(Submission submission) {
        int value = 0;

        List<Integer> values = submission
            .getGrades()
            .stream()
            .map(grade -> (int) grade.getGrade() * ((int) grade.getCriterion().getPriority() / 5))
            .collect(Collectors.toList());

        for (int currentValue : values) {
            value += currentValue;
        }

        double mean = value / (double) values.size();

        return SubmissionWithMeanGradeBean
            .builder()
            .submission(submission)
            .meanGrade(mean)
            .build();
    }

}
