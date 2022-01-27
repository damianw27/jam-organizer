package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import pl.wilenskid.jamorganizer.entity.bean.GradeCreateBean;
import pl.wilenskid.jamorganizer.entity.*;
import pl.wilenskid.jamorganizer.service.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/grade")
public class GradeRest implements CrudRest<GradeCreateBean, Object> {

    private final GradeService gradeService;

    @Inject
    public GradeRest(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @Override
    public String create(GradeCreateBean gradeCreateBean, HttpServletResponse response) throws HttpStatusCodeException {
        gradeService.create(gradeCreateBean);
        return "redirect:/submission/view?submissionId=" + gradeCreateBean.getSubmissionId();
    }

    @Override
    public String update(Object o, HttpServletResponse response) throws HttpStatusCodeException {
        return null;
    }

    @Override
    public String delete(Long id, HttpServletResponse response) throws HttpStatusCodeException {
        Optional<Grade> delete = gradeService.delete(id);
        return "redirect:/";
    }

}
