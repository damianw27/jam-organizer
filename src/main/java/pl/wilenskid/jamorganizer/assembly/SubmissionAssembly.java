package pl.wilenskid.jamorganizer.assembly;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.SubmissionBean;
import pl.wilenskid.jamorganizer.entity.Submission;
import pl.wilenskid.jamorganizer.service.SubmissionService;

@Named
public class SubmissionAssembly implements Assembly<Submission, SubmissionBean> {

    private final SubmissionService submissionService;
    
    @Inject
    public SubmissionAssembly(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }
    
    @Override
    public SubmissionBean toBean(Submission model) {
        List<Long> grades = StreamSupport.stream(model.getGrades().spliterator(), false)
                .map(grade -> grade.getId())
                .collect(Collectors.toList());
        
        return SubmissionBean
                .builder()
                .id(model.getId())
                .event(model.getId())
                .project(model.getId())
                .grades(grades)
                .build();
    }

    @Override
    public Submission toModel(SubmissionBean bean) {
        return submissionService.getById(bean.getId()).orElse(null);
    }

}
