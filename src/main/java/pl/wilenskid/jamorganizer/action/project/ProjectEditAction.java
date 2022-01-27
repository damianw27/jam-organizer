package pl.wilenskid.jamorganizer.action.project;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.entity.bean.ProjectBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.ParameterParseService;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/project/edit")
public class ProjectEditAction extends BaseAction {
    private final ProjectService projectService;
    private final ParameterParseService parameterParseService;

    public ProjectBean project;

    @Inject
    public ProjectEditAction(ProjectService projectService, ParameterParseService parameterParseService) {
        super("project/project-edit");
        this.projectService = projectService;
        this.parameterParseService = parameterParseService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        long projectId = parameterParseService.getLong("projectId", pathParams, -1);
        Project projectModel = projectService.getById(projectId).orElseThrow(NotFoundRestException::new);
        project = projectService.toBean(projectModel);
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
