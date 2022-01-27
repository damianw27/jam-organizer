package pl.wilenskid.jamorganizer.action.user;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.enums.UserRole;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/user/login")
public class LoginUserAction extends BaseAction {
    @Inject
    public LoginUserAction() {
        super("user/user-login");
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {

    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
