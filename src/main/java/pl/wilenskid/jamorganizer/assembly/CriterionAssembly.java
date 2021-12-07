package pl.wilenskid.jamorganizer.assembly;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.CriterionBean;
import pl.wilenskid.jamorganizer.entity.Criterion;
import pl.wilenskid.jamorganizer.service.CriterionService;

@Named
public class CriterionAssembly implements Assembly<Criterion, CriterionBean> {

    private final CriterionService criterionService;
    
    @Inject
    public CriterionAssembly(CriterionService criterionService) {
        super();
        this.criterionService = criterionService;
    }

    @Override
    public CriterionBean toBean(Criterion model) {
        List<Long> events = StreamSupport.stream(model.getEvents().spliterator(), false)
                .map(event -> event.getId())
                .collect(Collectors.toList());
        
        List<Long> grades = StreamSupport.stream(model.getGrades().spliterator(), false)
                .map(grade -> grade.getId())
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

    @Override
    public Criterion toModel(CriterionBean bean) {
        return criterionService.getById(bean.getId()).orElse(null);
    }

}
