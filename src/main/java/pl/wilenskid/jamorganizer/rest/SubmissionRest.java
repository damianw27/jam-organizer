package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import pl.wilenskid.jamorganizer.entity.bean.SubmissionCreateBean;
import pl.wilenskid.jamorganizer.entity.Submission;
import pl.wilenskid.jamorganizer.exception.NotModifiedRestException;
import pl.wilenskid.jamorganizer.service.SubmissionService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/submission")
public class SubmissionRest implements CrudRest<SubmissionCreateBean, Object> {
    private final SubmissionService submissionService;

    @Inject
    public SubmissionRest(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Override
    public String create(SubmissionCreateBean createSubmissionBean, HttpServletResponse response) throws HttpStatusCodeException {
        Submission submission = submissionService.create(createSubmissionBean);

        if (submission == null) {
            throw new NotModifiedRestException();
        }

        return "redirect:/submission/view?submissionId=" + submission.getId();
    }

    @Override
    public String update(Object o, HttpServletResponse response) throws HttpStatusCodeException {
        return "redirect:/";
    }

    @Override
    public String delete(Long id, HttpServletResponse response) throws HttpStatusCodeException {
        Optional<Submission> submission = submissionService.delete(id);

        if (submission.isEmpty()) {
            throw new NotModifiedRestException();
        }

        return "redirect:/submission/view-all";
    }
}
