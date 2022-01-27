package pl.wilenskid.jamorganizer.action.user;

import org.springframework.web.bind.annotation.RequestMapping;

import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.UserBean;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.ParameterParseService;
import pl.wilenskid.jamorganizer.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/user/view")
public class UserViewAction extends BaseAction {

    private final UserService userService;
    private final ParameterParseService parameterParseService;

    public UserBean user;

    @Inject
    protected UserViewAction(UserService userService,
                             ParameterParseService parameterParseService) {
        super("user/user-view");
        this.userService = userService;
        this.parameterParseService = parameterParseService;
    }

    @Override
    public void onLoad(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, String> pathParams) throws NotFoundRestException {
        Long userId = parameterParseService.getLong("userId", pathParams);
        User appUser = userService.getById(userId).orElseThrow(NotFoundRestException::new);
        user = userService.toBean(appUser);

        if (user == null) {
            throw new IllegalStateException("Found user can't be null!");
        }
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }

}
