package pl.wilenskid.jamorganizer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.GradeCreateBean;
import pl.wilenskid.jamorganizer.entity.Grade;
import pl.wilenskid.jamorganizer.repository.CriterionRepository;
import pl.wilenskid.jamorganizer.repository.GradeRepository;
import pl.wilenskid.jamorganizer.repository.MemberRepository;
import pl.wilenskid.jamorganizer.repository.SubmissionRepository;

@Named
public class GradeService implements CrudService<Grade, GradeCreateBean, Object, Long> {

    private final GradeRepository gradeRepository;
    private final MemberRepository memberRepository;
    private final SubmissionRepository submissionRepository;
    private final CriterionRepository criterionRepository;

    @Inject
    public GradeService(GradeRepository gradeRepository,
                        CriterionRepository criterionRepository,
                        SubmissionRepository submissionRepository,
                        MemberRepository memberRepository) {
        this.gradeRepository = gradeRepository;
        this.memberRepository = memberRepository;
        this.submissionRepository = submissionRepository;
        this.criterionRepository = criterionRepository;
    }

    @Override
    public List<Grade> getAll() {
        return StreamSupport.stream(gradeRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Grade> getById(Long id) {
        return gradeRepository.findById(id);
    }

    @Override
    public Grade create(GradeCreateBean createBean) {
        Grade grade = new Grade();
        submissionRepository.findById(createBean.getSubmission()).ifPresent(grade::setSubmission);
        memberRepository.findById(createBean.getJudge()).ifPresent(grade::setJudge);
        criterionRepository.findById(createBean.getCriterion()).ifPresent(grade::setCriterion);
        grade.setGrade(createBean.getGrade());

        if (grade.getSubmission() == null || grade.getJudge() == null || grade.getCriterion() == null) {
            return null;
        }

        gradeRepository.save(grade);
        return null;
    }

    @Override
    public Grade update(Object updateBean) {
        return null;
    }

    @Override
    public Optional<Grade> delete(Long id) {
        Optional<Grade> grade = gradeRepository.findById(id);
        grade.ifPresent(gradeRepository::delete);
        return grade;
    }

}
