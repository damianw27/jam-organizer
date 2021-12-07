package pl.wilenskid.jamorganizer.action;

import org.springframework.web.bind.annotation.RequestMapping;

import pl.wilenskid.jamorganizer.assembly.ApplicationUserAssembly;
import pl.wilenskid.jamorganizer.bean.ApplicationUserBean;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.exception.NotFoundRestException;
import pl.wilenskid.jamorganizer.service.ApplicationUserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Named
@RequestMapping("/app-user/view")
public class ViewApplicationUserAction extends BaseAction {
    private final ApplicationUserService applicationUserService;
    private final ApplicationUserAssembly applicationUserAssembly;

    public ApplicationUserBean applicationUser;
    public String created;
    public String jobStart;

    @Inject
    protected ViewApplicationUserAction(ApplicationUserService applicationUserService,
                                        ApplicationUserAssembly applicationUserAssembly) {
        super("view-app-user.html");
        this.applicationUserService = applicationUserService;
        this.applicationUserAssembly = applicationUserAssembly;
    }

    @Override
    public void onLoad(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParams) throws NotFoundRestException {
        Long appUserId = Long.valueOf(pathParams.get("appUserId"));
        ApplicationUser appUser = applicationUserService.getById(appUserId).orElseThrow(NotFoundRestException::new);
        applicationUser = applicationUserAssembly.toBean(appUser);
    }

    @Override
    public List<ApplicationUserRole> getAllowedRoles() {
        return null;
    }
}
