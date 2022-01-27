package pl.wilenskid.jamorganizer.rest.validator;

import pl.wilenskid.jamorganizer.entity.bean.UserCreateBean;
import pl.wilenskid.jamorganizer.rest.validator.enums.UserValidationError;
import pl.wilenskid.jamorganizer.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
public class UserRestValidator extends CrudRestValidator<UserCreateBean, Object> {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,}$";

    private final UserService userService;

    @Inject
    public UserRestValidator(UserService userService) {
        this.userService = userService;
    }

    public List<Integer> validate(UserCreateBean userCreateBean) {
        return Stream.of(
                usernameValid(userCreateBean),
                usernameTaken(userCreateBean),
                passwordToWeak(userCreateBean),
                passwordsNotSame(userCreateBean)
            )
            .filter(integer -> integer != -1)
            .collect(Collectors.toList());
    }

    private Integer usernameTaken(UserCreateBean userCreateBean) {
        return userService
            .getByName(userCreateBean.getName())
            .map((applicationUser) -> UserValidationError.USERNAME_TAKEN)
            .orElse(UserValidationError.NO_ERROR)
            .getErrorCode();
    }

    private Integer usernameValid(UserCreateBean userCreateBean) {
        UserValidationError userValidationError = userCreateBean.getName().matches(USERNAME_PATTERN)
            ? UserValidationError.NO_ERROR
            : UserValidationError.USERNAME_INVALID;

        return userValidationError.getErrorCode();
    }

    private Integer passwordsNotSame(UserCreateBean userCreateBean) {
        String password = userCreateBean.getPassword();
        String rePassword = userCreateBean.getRePassword();

        UserValidationError error = !password.equals(rePassword)
            ? UserValidationError.PASSWORDS_NOT_SAME
            : UserValidationError.NO_ERROR;

        return error.getErrorCode();
    }

    private Integer passwordToWeak(UserCreateBean userCreateBean) {
        UserValidationError userValidationError = userCreateBean.getPassword().matches(PASSWORD_PATTERN)
            ? UserValidationError.NO_ERROR
            : UserValidationError.PASSWORD_TO_WEAK;

        return userValidationError.getErrorCode();
    }
}
