package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.bean.ApplicationUserCreateBean;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.exception.NotModifiedRestException;
import pl.wilenskid.jamorganizer.service.ApplicationUserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/app-user")
public class ApplicationUserRest implements CrudRest<ApplicationUserCreateBean, Object> {

    private final ApplicationUserService applicationUserService;

    @Inject
    public ApplicationUserRest(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    public String create(ApplicationUserCreateBean createApplicationUserBean, HttpServletResponse response) {
        ApplicationUser applicationUser = applicationUserService.create(createApplicationUserBean);

        if (applicationUser == null) {
            throw new NotModifiedRestException();
        }

        return "redirect:/app-user/view?appUserId=" + applicationUser.getId();
    }

    @Override
    public String update(Object o, HttpServletResponse response) {
        return "redirect:/home";
    }

    @Override
    public String delete(Long id, HttpServletResponse response) {
        Optional<ApplicationUser> applicationUser = applicationUserService.delete(id);

        if (applicationUser.isEmpty()) {
            throw new NotModifiedRestException();
        }

        return "redirect:/app-user/view-all";
    }

}
