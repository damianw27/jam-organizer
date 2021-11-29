package pl.wilenskid.jamorganizer.action;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.service.EventService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/event/create")
public class CreateEventView extends BaseAction {
    @Inject
    public CreateEventView() {
        super("create-event");
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {

    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        List<ApplicationUserRole> roles = new ArrayList<>();
        roles.add(ApplicationUserRole.NORMAL);
        return roles;
    }
}
