package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import pl.wilenskid.jamorganizer.entity.bean.CriterionBean;
import pl.wilenskid.jamorganizer.entity.bean.CriterionCreateBean;
import pl.wilenskid.jamorganizer.entity.Criterion;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.CriterionService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/criterion")
public class CriterionRest implements CrudRest<CriterionCreateBean, CriterionBean> {
    private final CriterionService criterionService;

    @Inject
    public CriterionRest(CriterionService criterionService) {
        this.criterionService = criterionService;
    }

    @Override
    public String create(CriterionCreateBean criterionCreateBean, HttpServletResponse response) throws HttpStatusCodeException {
        criterionService.create(criterionCreateBean);
        return "redirect:/event/view?eventId=" + criterionCreateBean.getEventId() + "&focusCriterionLabel=true";
    }

    @Override
    public String update(CriterionBean criterionBean, HttpServletResponse response) throws HttpStatusCodeException {
        return null;
    }

    @Override
    public String delete(Long id, HttpServletResponse response) throws HttpStatusCodeException {
        Criterion criterion = criterionService.getById(id).orElseThrow(NotFoundRestException::new);
        criterionService.delete(criterion.getId());
        Event event = criterion.getEvents().stream().findFirst().orElseThrow(IllegalStateException::new);
        return "redirect:/event/view?eventId=" + event.getId() + "&focusCriterionLabel=true";
    }
}
