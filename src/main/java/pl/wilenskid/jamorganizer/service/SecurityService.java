package pl.wilenskid.jamorganizer.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.enums.UserRole;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SecurityService {

    public final UserService userService;

    @Inject
    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public User getLoggedInUser() {
        Object principal = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal)
                .getUsername();
        } else {
            username = principal
                .toString();
        }

        return userService
            .getByName(username)
            .orElseThrow(IllegalStateException::new);
    }

    public boolean hasLoggedInUserRole(UserRole userRole) {
        return getLoggedInUser()
            .getUserRole() == userRole;
    }

}
