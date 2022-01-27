package pl.wilenskid.jamorganizer.action.user;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.enums.UserRole;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/user/forget-pass")
public class ForgetPasswordAction extends BaseAction {

    public ForgetPasswordAction() {
        super("user/user-forget-pass");
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws Exception {

    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
