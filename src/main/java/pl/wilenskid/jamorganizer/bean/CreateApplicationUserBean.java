package pl.wilenskid.jamorganizer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.wilenskid.jamorganizer.entity.Organization;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateApplicationUserBean extends AbstractOperationBean {
    private String name;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String password;
    private ApplicationUserRole applicationUserRole;
    private String jobStart;
    private Set<Organization> organizations;
}

