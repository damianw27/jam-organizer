package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.builder.RedirectionPath;
import pl.wilenskid.jamorganizer.entity.bean.ModeratorAddBean;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.SecurityService;
import pl.wilenskid.jamorganizer.service.UserService;

import javax.inject.Inject;
import javax.naming.NoPermissionException;

@Controller
@RequestMapping("/admin")
public class AdminRest {

    private final UserService userService;

    @Inject
    public AdminRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/moderator")
    private String addModerator(ModeratorAddBean moderatorAddBean) throws NoPermissionException {
        if (moderatorAddBean.getUserId() != null) {
            userService.setUserRole(moderatorAddBean.getUserId(), UserRole.MODERATOR);
        }

        return RedirectionPath
            .builder()
            .basePath("/admin/panel")
            .withParam("focusAddModerator", "true")
            .build();
    }

    @DeleteMapping("/moderator/{userId}")
    public String removeModerator(@PathVariable Long userId) throws NoPermissionException {
        if (userId != null) {
            userService.setUserRole(userId, UserRole.NORMAL);
        }

        return RedirectionPath
            .builder()
            .basePath("/admin/panel")
            .build();
    }
}
