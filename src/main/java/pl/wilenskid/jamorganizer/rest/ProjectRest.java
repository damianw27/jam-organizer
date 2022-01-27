package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import pl.wilenskid.jamorganizer.entity.bean.AddAuthorBean;
import pl.wilenskid.jamorganizer.entity.bean.ProjectCreateBean;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.bean.ProjectUpdateBean;
import pl.wilenskid.jamorganizer.exception.NotModifiedRestException;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectRest implements CrudRest<ProjectCreateBean, ProjectUpdateBean> {

    private final ProjectService projectService;

    @Inject
    public ProjectRest(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public String create(ProjectCreateBean createProjectBean,
                         HttpServletResponse response) throws HttpStatusCodeException {
        Project project = projectService.create(createProjectBean);
        return "redirect:/project/view?projectId=" + project.getId();
    }

    @Override
    public String update(ProjectUpdateBean projectUpdateBean,
                         HttpServletResponse response) throws HttpStatusCodeException {
        projectService.update(projectUpdateBean);
        return "redirect:/project/view?projectId=" + projectUpdateBean.getProjectId();
    }

    @Override
    public String delete(Long id, HttpServletResponse response) throws HttpStatusCodeException {
        Optional<Project> project = projectService.delete(id);

        if (project.isEmpty()) {
            throw new NotModifiedRestException();
        }

        return "redirect:/project/view-all";
    }

    @PostMapping("/author/{projectId}")
    public String addAuthor(@PathVariable("projectId") Long projectId, AddAuthorBean addAuthorBean) {
        projectService.addAuthorToProject(projectId, addAuthorBean.getAuthorId());
        return "redirect:/project/view?projectId=" + projectId + "&focusAuthorId=true";
    }

    @DeleteMapping("/author/remove/{projectId}/{userId}")
    public String removeAuthor(@PathVariable("projectId") Long projectId,
                               @PathVariable("userId") Long userId) {
        projectService.removeAuthorFromProject(projectId, userId);
        return "redirect:/project/view?projectId=" + projectId + "&focusAuthorId=true";
    }

}
