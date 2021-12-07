package pl.wilenskid.jamorganizer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.CriterionBean;
import pl.wilenskid.jamorganizer.entity.Criterion;
import pl.wilenskid.jamorganizer.repository.CriterionRepository;

@Named
public class CriterionService implements CrudService<Criterion, CriterionBean, Object, Long> {

    private final CriterionRepository criterionRepository;
    
    @Inject
    public CriterionService(CriterionRepository criterionRepository) {
        super();
        this.criterionRepository = criterionRepository;
    }

    @Override
    public List<Criterion> getAll() {
        return StreamSupport.stream(criterionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Criterion> getById(Long id) {
        return criterionRepository.findById(id);
    }

    @Override
    public Criterion create(CriterionBean createBean) {
        Criterion criterion = new Criterion();
        criterion.setLabel(createBean.getLabel());
        criterion.setPriority(createBean.getPriority());
        criterion.setRequired(createBean.getRequired());
        criterion.setGrades(new HashSet<>());
        criterion.setEvents(new HashSet<>());
        criterionRepository.save(criterion);
        return criterion;
    }

    @Override
    public Criterion update(Object updateBean) {
        return null;
    }

    @Override
    public Optional<Criterion> delete(Long id) {
        Optional<Criterion> criterion = criterionRepository.findById(id);
        criterion.ifPresent(criterionRepository::delete);
        return criterion;
    }
    
    
    
}
