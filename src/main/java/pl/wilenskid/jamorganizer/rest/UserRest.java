package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.builder.RedirectionPath;
import pl.wilenskid.jamorganizer.entity.bean.UserCreateBean;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.exception.NotModifiedRestException;
import pl.wilenskid.jamorganizer.rest.validator.UserRestValidator;
import pl.wilenskid.jamorganizer.service.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserRest {

    private final UserService userService;
    private final UserRestValidator userRestValidator;

    @Inject
    public UserRest(UserService userService, UserRestValidator userRestValidator) {
        this.userService = userService;
        this.userRestValidator = userRestValidator;
    }

    @PostMapping("/register")
    public String create(UserCreateBean createApplicationUserBean, HttpServletResponse response) {
        List<Integer> validate = userRestValidator.validate(createApplicationUserBean);

        if (validate.size() != 0) {
            return RedirectionPath
                .builder()
                .basePath("/user/register")
                .withParams("validationErrors", validate)
                .build();
        }

        User user = userService.create(createApplicationUserBean);

        if (user == null) {
            throw new NotModifiedRestException();
        }

        return "redirect:/user/view?userId=" + user.getId();
    }

    @PatchMapping
    public String update(Object o, HttpServletResponse response) {
        return "redirect:/home";
    }

    @DeleteMapping
    public String delete(Long id, HttpServletResponse response) {
        Optional<User> applicationUser = userService.delete(id);

        if (applicationUser.isEmpty()) {
            throw new NotModifiedRestException();
        }

        return "redirect:/user/view-all";
    }

}
