package pl.wilenskid.jamorganizer.action.project;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.enums.UserRole;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/project/create")
public class ProjectCreateAction extends BaseAction {
    protected ProjectCreateAction() {
        super("project/project-create");
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {

    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
