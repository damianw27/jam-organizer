package pl.wilenskid.jamorganizer.action.user;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.ParameterParseService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/user/register")
public class RegisterUserAction extends BaseAction {

    private final ParameterParseService parameterParseService;

    public List<Integer> validationErrors;

    @Inject
    protected RegisterUserAction(ParameterParseService parameterParseService) {
        super("user/user-create");
        this.parameterParseService = parameterParseService;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) {
        validationErrors = parameterParseService.getValidationErrors(pathParams);
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
