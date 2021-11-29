package pl.wilenskid.jamorganizer.action;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.repository.ProjectRepository;

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
    private final ProjectRepository projectRepository;

    public Project project;
    public List<ApplicationUser> authors;
    public List<Object> comments;

    @Inject
    public ViewProjectAction(ProjectRepository projectRepository) {
        super("view-project.html");
        this.projectRepository = projectRepository;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        String projectId = pathParams.get("projectId");
        project = projectRepository.getById(Long.valueOf(projectId));
        authors = new ArrayList<>(project.getMembers());
        comments = new ArrayList<>();
    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        return null;
    }
}
