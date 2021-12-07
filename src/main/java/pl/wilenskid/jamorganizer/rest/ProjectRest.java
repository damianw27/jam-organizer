package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import pl.wilenskid.jamorganizer.bean.ProjectCreateBean;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.exception.NotModifiedRestException;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectRest implements CrudRest<ProjectCreateBean, Object> {
    private final ProjectService projectService;

    @Inject
    public ProjectRest(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String create(ProjectCreateBean createProjectBean, HttpServletResponse response) throws HttpStatusCodeException {
        Project project = projectService.create(createProjectBean);
        return "redirect:/project/view?projectId=" + project.getId();
    }

    @Override
    public String update(Object o, HttpServletResponse response) throws HttpStatusCodeException {
        return "redirect:/";
    }

    @Override
    public String delete(Long id, HttpServletResponse response) throws HttpStatusCodeException {
        Optional<Project> project = projectService.delete(id);

        if (project.isEmpty()) {
            throw new NotModifiedRestException();
        }

        return "redirect:/project/view-all";
    }
}
