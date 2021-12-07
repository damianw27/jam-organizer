package pl.wilenskid.jamorganizer.assembly;

import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.GradeBean;
import pl.wilenskid.jamorganizer.entity.Grade;
import pl.wilenskid.jamorganizer.repository.GradeRepository;

@Named
public class GradeAssembly implements Assembly<Grade, GradeBean> {

    private final GradeRepository gradeRepository;
    
    public GradeAssembly(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }
    
    @Override
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

    @Override
    public Grade toModel(GradeBean bean) {
        return gradeRepository.findById(bean.getId()).orElse(null);
    }

}
