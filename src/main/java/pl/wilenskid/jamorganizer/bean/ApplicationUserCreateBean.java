package pl.wilenskid.jamorganizer.bean;

import lombok.Data;
import pl.wilenskid.jamorganizer.entity.Organization;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;

import java.util.Set;

@Data
public class ApplicationUserCreateBean {
    private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String password;
    private ApplicationUserRole applicationUserRole;
    private String jobStart;
    private Set<Organization> organizations;
}

