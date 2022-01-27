package pl.wilenskid.jamorganizer.action.project;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.ProjectBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.ParameterParseService;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/project/view-all")
public class ProjectsViewAction extends BaseAction {

    private static final Integer PAGE_SIZE = 15;

    private final ProjectService projectService;
    private final ParameterParseService parameterParseService;

    public List<ProjectBean> projects;
    public List<Integer> myProjectsPageIndexes;
    public List<Integer> projectsPageIndexes;

    @Inject
    public ProjectsViewAction(ProjectService projectService, ParameterParseService parameterParseService) {
        super("project/project-view-all");
        this.projectService = projectService;
        this.parameterParseService = parameterParseService;
    }


    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        int myProjectsPageIndex = parameterParseService.getInt("myProjectsPageIndex", pathParams);

        if (myProjectsPageIndex == 0) {
            pathParams.put("myProjectsPageIndex", "0");
        }

        int projectsPageIndex = parameterParseService.getInt("projectsPageIndex", pathParams);

        if (projectsPageIndex == 0) {
            pathParams.put("projectsPageIndex", "0");
        }

        projects = projectService
            .getAll()
            .stream()
            .skip((long) projectsPageIndex * PAGE_SIZE)
            .limit(PAGE_SIZE)
            .map(projectService::toBean)
            .collect(Collectors.toList());

        int projectsCount = projectService
            .getAll()
            .size();

        projectsPageIndexes = new ArrayList<>();

        for (int i = 0; i < Math.ceil(projectsCount / (double) PAGE_SIZE); i++) {
            projectsPageIndexes.add(i);
        }
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
