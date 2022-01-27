package pl.wilenskid.jamorganizer.entity.bean;

import lombok.Data;
import pl.wilenskid.jamorganizer.enums.UserRole;

@Data
public class UserCreateBean {
    private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String password;
    private String rePassword;
    private UserRole userRole;
    private String jobStart;
}

