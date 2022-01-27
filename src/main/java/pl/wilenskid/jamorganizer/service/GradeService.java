package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.entity.bean.GradeBean;
import pl.wilenskid.jamorganizer.entity.bean.GradeCreateBean;
import pl.wilenskid.jamorganizer.entity.*;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.repository.CriterionRepository;
import pl.wilenskid.jamorganizer.repository.GradeRepository;
import pl.wilenskid.jamorganizer.repository.SubmissionRepository;
import pl.wilenskid.jamorganizer.entity.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class GradeService {

    private final GradeRepository gradeRepository;
    private final MemberService memberService;
    private final SubmissionRepository submissionRepository;
    private final CriterionRepository criterionRepository;
    private final SecurityService securityService;

    @Inject
    public GradeService(GradeRepository gradeRepository,
                        MemberService memberService,
                        CriterionRepository criterionRepository,
                        SubmissionRepository submissionRepository,
                        SecurityService securityService) {
        this.gradeRepository = gradeRepository;
        this.memberService = memberService;
        this.submissionRepository = submissionRepository;
        this.criterionRepository = criterionRepository;
        this.securityService = securityService;
    }

    public List<Grade> getAll() {
        return StreamSupport.stream(gradeRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Grade> getById(Long id) {
        return gradeRepository.findById(id);
    }

    public Grade create(GradeCreateBean createBean) {
        User loggedInUser = securityService.getLoggedInUser();

        Member judge = memberService
            .getAll()
            .stream()
            .filter(member -> Objects.equals(member.getUser().getId(), loggedInUser.getId()))
            .findFirst()
            .orElseThrow(SecurityException::new);

        Submission submission = submissionRepository.findById(createBean.getSubmissionId()).orElseThrow(NotFoundRestException::new);
        Criterion criterion = criterionRepository.findById(createBean.getCriterionId()).orElseThrow(NotFoundRestException::new);

        Grade grade = new Grade();
        grade.setSubmission(submission);
        grade.setCriterion(criterion);
        grade.setJudge(judge);
        grade.setGrade(createBean.getGrade());
        gradeRepository.save(grade);

        Set<Grade> grades = submission.getGrades();
        grades.add(grade);
        submission.setGrades(grades);
        submissionRepository.save(submission);

        return null;
    }

    public Grade update(Object updateBean) {
        return null;
    }

    public Optional<Grade> delete(Long id) {
        Optional<Grade> grade = gradeRepository.findById(id);
        grade.ifPresent(gradeRepository::delete);
        return grade;
    }

    public GradeBean toBean(Grade model) {
        return GradeBean
            .builder()
            .id(model.getId())
            .criterion(model.getCriterion().getId())
            .submission(model.getSubmission().getId())
            .judge(model.getJudge().getId())
            .grade(model.getGrade())
            .build();
    }

}
