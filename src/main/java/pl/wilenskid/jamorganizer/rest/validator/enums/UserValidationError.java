package pl.wilenskid.jamorganizer.rest.validator.enums;

import lombok.Getter;

@Getter
public enum UserValidationError {

    NO_ERROR(-1),
    USERNAME_TAKEN(101),
    USERNAME_INVALID(102),
    PASSWORDS_NOT_SAME(103),
    PASSWORD_TO_WEAK(104);

    private final Integer errorCode;

    UserValidationError(Integer errorCode) {
        this.errorCode = errorCode;
    }

}
