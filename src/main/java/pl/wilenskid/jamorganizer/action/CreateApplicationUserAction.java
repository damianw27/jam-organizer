package pl.wilenskid.jamorganizer.action;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/app-user/create")
public class CreateApplicationUserAction extends BaseAction {
    protected CreateApplicationUserAction() {
        super("create-app-user");
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {

    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        return null;
    }
}
