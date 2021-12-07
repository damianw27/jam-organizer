package pl.wilenskid.jamorganizer.action;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.repository.ProjectRepository;
import pl.wilenskid.jamorganizer.service.ProjectService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/project/view")
public class ViewProjectAction extends BaseAction {
    private final ProjectService projectService;

    public Project project;
    public List<ApplicationUser> authors;
    public List<Object> comments;

    @Inject
    public ViewProjectAction(ProjectService projectService) {
        super("view-project.html");
        this.projectService = projectService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        String projectId = pathParams.get("projectId");
        project = projectService.getById(Long.valueOf(projectId)).orElseThrow(ChangeSetPersister.NotFoundException::new);
        authors = new ArrayList<>(project.getMembers());
        comments = new ArrayList<>();
    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        return null;
    }
}
