package pl.wilenskid.jamorganizer.action.user;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/user/edit")
public class UserEditAction extends BaseAction {
    private final UserService userService;

    public User user;

    @Inject
    public UserEditAction(UserService userService) {
        super("user/user-edit");
        this.userService = userService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {
        long userId = Long.parseLong(pathParams.get("userId"));
        user = userService.getById(userId).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
