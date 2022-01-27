package pl.wilenskid.jamorganizer.action.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.enums.UserRole;

@Named
@RequestMapping("/event/create")
public class EventCreateAction extends BaseAction {
    @Inject
    public EventCreateAction() {
        super("event/event-create");
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {

    }

    @Override
    public List<UserRole> getAllowedRoles() {
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.NORMAL);
        return roles;
    }
}
